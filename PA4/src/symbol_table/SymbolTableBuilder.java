package symbol_table;

import java.util.LinkedList;
import java.util.Queue;

import ast.*;
import semantic_analysis.SemanticError;
import semantic_analysis.SemanticErrorThrower;
import type_table.*;

public class SymbolTableBuilder implements Visitor {
	
	private Queue<ASTNode> nodeHandlingQueue; // for BFS scanning
	private SymbolTable rootSymbolTable; /// a pointer to the GLOBAL symbol table

	private SymbolTable currentClassSymbolTablePoint; // for searching scope of variables which
	// called from external class location.
	/* for exaple:
	 * 	class A { int x; }
	 * 	class B {
	 * 		void foo() {
	 * 			A a = new A();
	 * 			int y = a.x; // we need to save A's symbol table as the currentClassSymbolTablePoint to know where to look for x.
	 * 		}
	 * }
	 */	
	private type_table.Type currentMethodType; // for the return statement to have the method type
	private IDSymbolKinds currentMethodKind; // virtual or static
	// which it returns value to.
	
	private TypeTable typeTable;
	
	private SemanticErrorThrower semanticErrorThrower; 
	
	int blockCounter; // for giving unique IDs to statements block symbol tables.
	
	public SymbolTableBuilder(TypeTable typeTable, String tableId) {
		this.nodeHandlingQueue = new LinkedList<ASTNode>();
		this.rootSymbolTable = new SymbolTable(tableId, SymbolTableTypes.GLOBAL);
		this.currentClassSymbolTablePoint = null;
		this.currentMethodType = null;
		this.typeTable = typeTable;
		
		this.semanticErrorThrower = null;
	}

	public SymbolTable getSymbolTable() {
		return rootSymbolTable;
	}
	
	// Builds the program symbol table and do the following things:
	// 1) Checks there are no calls to variables or methods which were not initialized in their scope.
	// 	  1.1) Calls to local variables will only be permitted if the variable was initialized before the call.
	//	  1.2) There is a separation between a class virtual scope and static scope in this check but a class has only one symbol table.
	// 2) Checks there are no calls to classes which were not declared in the program
	// 3) Checks variables weren't initialized in their scope more than once.
	// 4) Checks fields weren't initialized in their scope or in one of their super classes more than once.
	// 5) Checks methods were not declared more than once in their scope or in one of their
	//	  super classes unless a method overrides a method with the same signature.
	// 6) Set types to each of the class, fields, formals and local variables nodes.
	// 7) Connects each node with its local symbol table.
	// The AST is scanned in BFS down to the methods level and in DFS from there on.
	public void buildSymbolTables(Program root) throws SemanticError {
		nodeHandlingQueue.add(root);
		ASTNode currentNode;
		this.blockCounter = 0;
		while (!nodeHandlingQueue.isEmpty()) { 
			// BFS queue scan. The queue allows to scan all the classes, then all the fields and then all the methods.
			// The statements inside a method will be scanned "deeply" (DFS) and will not be added to the queue.
			currentNode = nodeHandlingQueue.poll();
			if (!(Boolean)currentNode.accept(this)) 
				semanticErrorThrower.execute();
		}
	}
	
	@Override
	public Object visit(Program program) {
		SymbolTable programSymbolTable = this.rootSymbolTable;
		for (ICClass iccls : program.getClasses()) {
			nodeHandlingQueue.add(iccls);
			if (!addEntryAndCheckDuplication(programSymbolTable, 
					new SymbolEntry(iccls.getName(), typeTable.getClassType(iccls.getName()), 
							IDSymbolKinds.CLASS))) {
				this.semanticErrorThrower = new SemanticErrorThrower(
						iccls.getLine(), "class " + iccls.getName() + " is declared more than once");
				return false;
			}
			iccls.setEntryType(typeTable.getClassType(iccls.getName()));
			SymbolTable icclsParentSymbolTable;
			if (iccls.hasSuperClass()) 
				icclsParentSymbolTable = programSymbolTable.findChildSymbolTable(iccls.getSuperClassName());
			else
				icclsParentSymbolTable = programSymbolTable;
			iccls.setSymbolsTable(icclsParentSymbolTable);
			
			SymbolTable currentClassSymbolTable = new SymbolTable(iccls.getName(), SymbolTableTypes.CLASS);
			currentClassSymbolTable.setParentSymbolTable(icclsParentSymbolTable);
			icclsParentSymbolTable.addTableChild(iccls.getName(), currentClassSymbolTable);
		}
		return true;
	}

	@Override
	public Object visit(ICClass icClass) {
		SymbolTable currentClassSymbolTable = this.rootSymbolTable.findChildSymbolTable(icClass.getName());
		for (Field field : icClass.getFields()) {
			nodeHandlingQueue.add(field);
			type_table.Type fieldType = typeTable.getTypeFromASTTypeNode(field.getType());
			SymbolEntry fieldSymbolEntry = new SymbolEntry(field.getName(), fieldType, IDSymbolKinds.FIELD);
			if (!addEntryAndCheckDuplication(currentClassSymbolTable, fieldSymbolEntry)) {
				this.semanticErrorThrower = new SemanticErrorThrower(
						field.getLine(), "field " + field.getName() + " is declared more than once");
				return false;
			}
			field.setSymbolEntry(fieldSymbolEntry);
			field.setSymbolsTable(currentClassSymbolTable);
		}
		
		for (Method method : icClass.getMethods()) {
			nodeHandlingQueue.add(method);
			type_table.Type methodType = typeTable.getMethodType(method);
			SymbolEntry methodSymbolEntry = new SymbolEntry(method.getName(), methodType, getMethodKind(method));
			if (!addEntryAndCheckDuplication(currentClassSymbolTable, methodSymbolEntry)) {
				this.semanticErrorThrower = new SemanticErrorThrower(
						method.getLine(), "method " + method.getName() + " is declared more than once");
				return false;
			}
			method.setSymbolEntry(methodSymbolEntry);
			method.setSymbolsTable(currentClassSymbolTable);
			SymbolTable currentMethodSymbolTable = new SymbolTable(method.getName(), SymbolTableTypes.METHOD);
			currentMethodSymbolTable.setParentSymbolTable(currentClassSymbolTable);
			currentClassSymbolTable.addTableChild(method.getName(), currentMethodSymbolTable);
		}
		
		return true;
	}

	@Override
	public Object visit(Field field) {
		return true;
	}

	@Override
	public Object visit(VirtualMethod method) {
		return visitMethod(method);
	}

	@Override
	public Object visit(StaticMethod method) {
		return visitMethod(method);
	}

	@Override
	public Object visit(LibraryMethod method) {
		return visitMethod(method);
	}

	@Override
	public Object visit(Formal formal) {
		type_table.Type formalType = typeTable.getTypeFromASTTypeNode(formal.getType());
		SymbolEntry formalSymbolEntry = new SymbolEntry(formal.getName(), formalType, IDSymbolKinds.FORMAL);
		if (!addEntryAndCheckDuplication(formal.getSymbolsTable(), formalSymbolEntry)) {
			this.semanticErrorThrower = new SemanticErrorThrower(
					formal.getLine(), "formal " + formal.getName() + " is declared more than once");
			return false;
		}
		formal.setSymbolEntry(formalSymbolEntry);
		return true;
	}

	@Override
	public Object visit(PrimitiveType type) {
		// not called
		return null;
	}

	@Override
	public Object visit(UserType type) {
		// not called
		return null;
	}

	@Override
	public Object visit(AssignStmt assignment) {
		assignment.getVariable().setSymbolsTable(assignment.getSymbolsTable());
		if (!(Boolean)assignment.getVariable().accept(this))
			return false;
		assignment.getAssignment().setSymbolsTable(assignment.getSymbolsTable());
		if (!(Boolean)assignment.getAssignment().accept(this))
			return false;
		
		return true;
	}

	@Override
	public Object visit(CallStmt callStatement) {
		callStatement.getCall().setSymbolsTable(callStatement.getSymbolsTable());
		if (!(Boolean)callStatement.getCall().accept(this))
			return false;
		
		return true;
	}

	@Override
	public Object visit(ReturnStmt returnStatement) {
		if (returnStatement.hasValue()) {
			returnStatement.getValue().setSymbolsTable(returnStatement.getSymbolsTable());
			if (!(Boolean)returnStatement.getValue().accept(this))
				return false;
		}
		
		returnStatement.setMethodType(this.currentMethodType);
		return true;
	}

	@Override
	public Object visit(IfStmt ifStatement) {
		ifStatement.getCondition().setSymbolsTable(ifStatement.getSymbolsTable());
		if (!(Boolean)ifStatement.getCondition().accept(this))
			return false;
		ifStatement.getOperation().setSymbolsTable(ifStatement.getSymbolsTable());
		if (!(Boolean)ifStatement.getOperation().accept(this))
			return false;
		if (ifStatement.hasElse()) {
			ifStatement.getElseOperation().setSymbolsTable(ifStatement.getSymbolsTable());
			if (!(Boolean)ifStatement.getElseOperation().accept(this))
				return false;
		}
		return true;
	}

	@Override
	public Object visit(WhileStmt whileStatement) {
		whileStatement.getCondition().setSymbolsTable(whileStatement.getSymbolsTable());
		if (!(Boolean)whileStatement.getCondition().accept(this))
			return false;
		whileStatement.getOperation().setSymbolsTable(whileStatement.getSymbolsTable());
		if (!(Boolean)whileStatement.getOperation().accept(this))
			return false;
		
		return true;
	}

	@Override
	public Object visit(BreakStmt breakStatement) {
		return true;
	}

	@Override
	public Object visit(ContinueStmt continueStatement) {
		return true;
	}

	@Override
	public Object visit(StmtBlock statementsBlock) {
		this.blockCounter++;
		SymbolTable blockStmntSymbolTable = new SymbolTable("block#" + blockCounter, SymbolTableTypes.STATEMENT_BLOCK);
		statementsBlock.getSymbolsTable().addTableChild(
				blockStmntSymbolTable.getId(), blockStmntSymbolTable);
		blockStmntSymbolTable.setParentSymbolTable(statementsBlock.getSymbolsTable());
		for (Stmt stmnt : statementsBlock.getStatements()) {
			stmnt.setSymbolsTable(blockStmntSymbolTable);
			if (!(Boolean)stmnt.accept(this))
				return false;
		}
		
		return true;
	}

	@Override
	public Object visit(LocalVarStmt localVariable) {
		if (localVariable.hasInitValue()) {
			localVariable.getInitValue().setSymbolsTable(localVariable.getSymbolsTable());
			if (!(Boolean)localVariable.getInitValue().accept(this))
				return false;
		}
		
		type_table.Type localVarType = typeTable.getTypeFromASTTypeNode(localVariable.getType());
		SymbolEntry localVarSymbolEntry = new SymbolEntry(localVariable.getName(), localVarType, IDSymbolKinds.VARIABLE);
		if (!addEntryAndCheckDuplication(localVariable.getSymbolsTable(), localVarSymbolEntry)) {
			this.semanticErrorThrower = new SemanticErrorThrower(localVariable.getLine(),
					"variable " + localVariable.getName() + " is initialized more than once");
			
			return false;
		}
		localVariable.setSymbolEntry(localVarSymbolEntry);
		return true;
	}

	@Override
	public Object visit(VarLocationExpr location) {
		SymbolEntry varEntry;
		if (location.isExternal()) {
			location.getLocation().setSymbolsTable(location.getSymbolsTable());
			if (!(Boolean)location.getLocation().accept(this))
				return false;
			varEntry = getVariableSymbolEntry(location.getName(), this.currentClassSymbolTablePoint);
			if (varEntry == null) {
				this.semanticErrorThrower = new SemanticErrorThrower(location.getLine(),
						"variable " + location.getName() + " is not initialized");
				return false;
			}
		}
		else {
			varEntry = getVariableSymbolEntry(location.getName(),  location.getSymbolsTable());
			if (varEntry == null) {
				this.semanticErrorThrower = new SemanticErrorThrower(location.getLine(),
						"variable " + location.getName() + " is not initialized");
				return false;
			}
		}
		location.setSymbolEntry(varEntry);
		if (varEntry.getType().isClassType()) 
			this.currentClassSymbolTablePoint = this.rootSymbolTable.findChildSymbolTable(varEntry.getType().toString());	
		return true;
	}

	@Override
	public Object visit(ArrayLocationExpr location) {
		location.getArray().setSymbolsTable(location.getSymbolsTable());
		if (!(Boolean)location.getArray().accept(this))
			return false;

		location.getIndex().setSymbolsTable(location.getSymbolsTable());
		if (!(Boolean)location.getIndex().accept(this))
			return false;

		return true;
	}

	@Override
	public Object visit(StaticCallExpr call) {
		SymbolTable clsSymbolTable = this.rootSymbolTable.findChildSymbolTable(call.getClassName());
		if (clsSymbolTable == null) {
			this.semanticErrorThrower = new SemanticErrorThrower(call.getLine(),
					"the class " + call.getClassName() + " dosen't exist");
			return false;
		}
		SymbolEntry methodEntry = getMethodSymbolEntry(call.getName(), IDSymbolKinds.STATIC_METHOD, clsSymbolTable, false);
		if(methodEntry == null) {
			this.semanticErrorThrower = new SemanticErrorThrower(call.getLine(),
					"the method " + call.getName() + " dosen't exist");
			return false;
		}
		call.setMethodType(methodEntry.getType());
		for (Expr arg : call.getArguments()) {
			arg.setSymbolsTable(call.getSymbolsTable());
			if (!(Boolean)arg.accept(this))
				return false;
		}
		
		return true;
	}

	@Override
	public Object visit(VirtualCallExpr call) {
		SymbolEntry methodEntry;
		if (call.isExternal()) {
			call.getLocation().setSymbolsTable(call.getSymbolsTable());
			if (!(Boolean)call.getLocation().accept(this))
				return false;
			methodEntry = getMethodSymbolEntry(call.getName(), IDSymbolKinds.VIRTUAL_METHOD, this.currentClassSymbolTablePoint, false);
		}
		else {
			if (currentMethodKind == IDSymbolKinds.STATIC_METHOD)
				methodEntry = getMethodSymbolEntry(call.getName(), currentMethodKind, call.getSymbolsTable(), false);
			else
				methodEntry = getMethodSymbolEntry(call.getName(), null, call.getSymbolsTable(), true);
		}
		if(methodEntry == null) {
			this.semanticErrorThrower = new SemanticErrorThrower(call.getLine(),
					"the method " + call.getName() + " dosen't exist");
			return false;
		}
		call.setMethodType(methodEntry.getType());
		for (Expr arg : call.getArguments()) {
			arg.setSymbolsTable(call.getSymbolsTable());
			if (!(Boolean)arg.accept(this))
				return false;
		}
		
		return true;
	}

	@Override
	public Object visit(ThisExpr thisExpression) {
		SymbolTable bottomSymbolTable = thisExpression.getSymbolsTable();
		while (bottomSymbolTable.getId().contains("block#")) 
			bottomSymbolTable = bottomSymbolTable.getParentSymbolTable();
		
		bottomSymbolTable = bottomSymbolTable.getParentSymbolTable();
		this.currentClassSymbolTablePoint = bottomSymbolTable;
		
		return true;
	}

	@Override
	public Object visit(NewClassExpr newClass) {
		SymbolTable clsSymbolTable = this.rootSymbolTable.findChildSymbolTable(newClass.getName());
		if (clsSymbolTable == null) {
			this.semanticErrorThrower = new SemanticErrorThrower(newClass.getLine(),
					"the class " + newClass.getName() + " dosen't exist");
			return false;
		}
		
		this.currentClassSymbolTablePoint = clsSymbolTable;
		return true;
	}

	@Override
	public Object visit(NewArrayExpr newArray) {
		newArray.getSize().setSymbolsTable(newArray.getSymbolsTable());
		if (!(Boolean)newArray.getSize().accept(this))
			return false;

		return true;
	}

	@Override
	public Object visit(LengthExpr length) {
		length.getArray().setSymbolsTable(length.getSymbolsTable());
		if (!(Boolean)length.getArray().accept(this))
			return false;
		
		return true;
	}

	@Override
	public Object visit(MathBinaryOpExpr binaryOp) {
		binaryOp.getFirstOperand().setSymbolsTable(binaryOp.getSymbolsTable());
		if(!(Boolean)binaryOp.getFirstOperand().accept(this))
			return false;
		binaryOp.getSecondOperand().setSymbolsTable(binaryOp.getSymbolsTable());
		if(!(Boolean)binaryOp.getSecondOperand().accept(this))
			return false;

		return true;
	}

	@Override
	public Object visit(LogicalBinaryOpExpr binaryOp) {
		binaryOp.getFirstOperand().setSymbolsTable(binaryOp.getSymbolsTable());
		if(!(Boolean)binaryOp.getFirstOperand().accept(this))
			return false;
		binaryOp.getSecondOperand().setSymbolsTable(binaryOp.getSymbolsTable());
		if(!(Boolean)binaryOp.getSecondOperand().accept(this))
			return false;
		
		return true;
	}

	@Override
	public Object visit(MathUnaryOpExpr unaryOp) {
		unaryOp.getOperand().setSymbolsTable(unaryOp.getSymbolsTable());
		if(!(Boolean)unaryOp.getOperand().accept(this))
			return false;
		
		return true;
	}

	@Override
	public Object visit(LogicalUnaryOpExpr unaryOp) {
		unaryOp.getOperand().setSymbolsTable(unaryOp.getSymbolsTable());
		if(!(Boolean)unaryOp.getOperand().accept(this))
			return false;
		
		return true;
	}

	@Override
	public Object visit(LiteralExpr literal) {
		return true;
	}

	@Override
	public Object visit(ExprBlock expressionBlock) {
		expressionBlock.getExpression().setSymbolsTable(expressionBlock.getSymbolsTable());
		if (!(Boolean)expressionBlock.getExpression().accept(this))
			return false;

		return true;
	}
	
	private Object visitMethod(Method method) {
		SymbolTable currentMethodSymbolTable = method.getSymbolsTable().findChildSymbolTable(
				method.getName());
		this.currentMethodType = method.getEntryType();
		this.currentMethodKind = method.getSymbolsTable().getEntry(method.getName()).getKind();
		for (Formal formal : method.getFormals()) {
			formal.setSymbolsTable(currentMethodSymbolTable);
			if (!(Boolean)formal.accept(this))
				return false;
		}
		
		
		for (Stmt stmnt : method.getStatements()) {
			stmnt.setSymbolsTable(currentMethodSymbolTable);
			if(!(Boolean)stmnt.accept(this))
				return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @return true if and only if there is no variable duplication 
	 * and the SymbolEntry was added successfully.
	 */
	private Boolean addEntryAndCheckDuplication(SymbolTable table, SymbolEntry entry) {
		if (table.hasEntry(entry.getId()))
			return false;
		if (entry.getKind() == IDSymbolKinds.FIELD) {
			SymbolTable scanningTable = table.getParentSymbolTable();
			while (scanningTable.getTableType() != SymbolTableTypes.GLOBAL) {
				if (scanningTable.hasEntry(entry.getId()))
					return false;
				scanningTable = scanningTable.getParentSymbolTable();
			}
		}
		if (entry.getKind().isMethodKind()) {
			SymbolTable scanningTable = table.getParentSymbolTable();
			while (scanningTable.getTableType() != SymbolTableTypes.GLOBAL) {
				if (scanningTable.hasEntry(entry.getId())) {
					if (!scanningTable.getEntry(entry.getId()).getType().equals(entry.getType()))
						return false;
					else
						break;
				}
				scanningTable = scanningTable.getParentSymbolTable();
			}
		}
		table.addEntry(entry.getId(), entry);
		
		return true;
	}
	
	private IDSymbolKinds getMethodKind(Method method) {
		if (method instanceof VirtualMethod)
			return IDSymbolKinds.VIRTUAL_METHOD;
		
		return IDSymbolKinds.STATIC_METHOD;
	}
	
	private SymbolEntry getVariableSymbolEntry(String name, SymbolTable bottomSymbolTable) {
		if ((bottomSymbolTable.getTableType() == SymbolTableTypes.METHOD) || 
				(bottomSymbolTable.getTableType() == SymbolTableTypes.STATEMENT_BLOCK)) {
			while (bottomSymbolTable.getId().contains("block#")) {
				if (bottomSymbolTable.hasEntry(name))
					return bottomSymbolTable.getEntry(name);
				bottomSymbolTable = bottomSymbolTable.getParentSymbolTable();
			}
			
			// Checking method table:
			if (bottomSymbolTable.hasEntry(name))
				return bottomSymbolTable.getEntry(name);
			
			String containigMethodName = bottomSymbolTable.getId();
			bottomSymbolTable = bottomSymbolTable.getParentSymbolTable();
			if (bottomSymbolTable.getEntry(containigMethodName).getKind() == 
					IDSymbolKinds.STATIC_METHOD)
				return null;
		}
		
		// Checking class tables:
		while (bottomSymbolTable.getTableType() != SymbolTableTypes.GLOBAL) {
			SymbolTable clsTable = bottomSymbolTable;
			if (clsTable.hasEntry(name))
				if (clsTable.getEntry(name).getKind() == IDSymbolKinds.FIELD)
					return clsTable.getEntry(name);
			bottomSymbolTable = bottomSymbolTable.getParentSymbolTable();
		}

		return null;
	}
	
	private SymbolEntry getMethodSymbolEntry(String name, 
			IDSymbolKinds methodKind, SymbolTable bottomClassSymbolTable, Boolean ignoreMethodKind) {
		while (bottomClassSymbolTable != null) {
			if (bottomClassSymbolTable.hasEntry(name)) {
				SymbolEntry candidateEntry = bottomClassSymbolTable.getEntry(name);
				Boolean existanceCondition = ignoreMethodKind ? candidateEntry.getKind().isMethodKind() :
					candidateEntry.getKind() == methodKind;
				if (existanceCondition)
					return bottomClassSymbolTable.getEntry(name);
			}
			bottomClassSymbolTable = bottomClassSymbolTable.getParentSymbolTable();
		}
		return null;
	}
}
