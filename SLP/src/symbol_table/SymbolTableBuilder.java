package symbol_table;

import java.util.LinkedList;
import java.util.Queue;
import semantic_analysis.*;
import type_table.*;
import ast.*;

/**
 * Builder for a symbol table.
 */
public class SymbolTableBuilder implements Visitor {
	/** Node needed for BFS scanning **/
	private Queue<ASTNode> nodeHandlingQueue; 
	
	/** The global symbol table **/
	private SymbolTable rootSymbolTable;

	/** 
	 * Class symbol table needed for searching scope of variables which
	 * called from external class location .
	 */
	private SymbolTable currentClassSymbolTablePoint;
	
	/** 
	 * Method type needed for checking the return statement to have 
	 * the method type which it returns value to.
	 */
	private type_table.Type currentMethodType; 
	
	private TypeTable typeTable;
	private SemanticErrorThrower semanticErrorThrower; 
	
	/** A counter to insure giving unique identifiers to statements block symbol tables. **/
	int blockCounter; 
	
	/**
	 * Constructs a new symbol table builder.
	 * 
	 * @param typeTable The program type table.
	 * @param tableId   The name of the root of the symbol table tree to be built.
	 */
	public SymbolTableBuilder(TypeTable typeTable, String tableId) {
		this.nodeHandlingQueue = new LinkedList<ASTNode>();
		this.rootSymbolTable = new SymbolTable(tableId, SymbolTableType.GLOBAL);
		this.currentClassSymbolTablePoint = null;
		this.currentMethodType = null;
		this.typeTable = typeTable;
		
		this.semanticErrorThrower = null;
	}

	public SymbolTable getSymbolTable() {
		return rootSymbolTable;
	}
	
	/**
	 * Builds the program symbol table while checking the following semantic issues:
	 * - There are no calls to variables or methods which were not initialized in their scope.
	 * 	  # Note that calls to local variables will only be permitted if the variable was initialized before the call.
	 *	  # Note that there is a separation between a class virtual scope and static scope in this check but a class has only one symbol table.
	 * - There are no calls to classes which were not declared in the program
	 * - Variables weren't initialized in their scope more than once.
	 * - Fields weren't initialized in their scope or in one of their super classes more than once.
	 * - Methods were not declared more than once in their scope or in one of their
	 *	 super classes unless a method overrides a method with the same signature.
	 * - Set types to each of the class, fields, formals and local variables nodes.
	 * - Connects each node with its local symbol table.
	 * The AST is scanned in BFS down to the methods level and in DFS from there on.
	 * 
	 * @param root  The root of the AST.
	 */
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
							SymbolKind.CLASS))) {
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
			iccls.setSymbolTable(icclsParentSymbolTable);
			
			SymbolTable currentClassSymbolTable = new SymbolTable(iccls.getName(), SymbolTableType.CLASS);
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
			if (!addEntryAndCheckDuplication(currentClassSymbolTable, 
					new SymbolEntry(field.getName(), fieldType, SymbolKind.FIELD))) {
				this.semanticErrorThrower = new SemanticErrorThrower(
						field.getLine(), "field " + field.getName() + " is declared more than once");
				return false;
			}
			field.setEntryType(fieldType);
			field.setSymbolTable(currentClassSymbolTable);
		}
		
		for (Method method : icClass.getMethods()) {
			nodeHandlingQueue.add(method);
			type_table.Type methodType = typeTable.getMethodType(method);
			if (!addEntryAndCheckDuplication(currentClassSymbolTable, 
					new SymbolEntry(method.getName(), methodType, getMethodKind(method)))) {
				this.semanticErrorThrower = new SemanticErrorThrower(
						method.getLine(), "method " + method.getName() + " is declared more than once");
				return false;
			}
			method.setEntryType(methodType);
			method.setSymbolTable(currentClassSymbolTable);
			SymbolTable currentMethodSymbolTable = new SymbolTable(method.getName(), SymbolTableType.METHOD);
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
		if (!addEntryAndCheckDuplication(formal.getSymbolTable(), 
				new SymbolEntry(formal.getName(), formalType, SymbolKind.FORMAL))) {
			this.semanticErrorThrower = new SemanticErrorThrower(
					formal.getLine(), "formal " + formal.getName() + " is declared more than once");
			return false;
		}
		formal.setEntryType(formalType);
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
		assignment.getVariable().setSymbolTable(assignment.getSymbolTable());
		if (!(Boolean)assignment.getVariable().accept(this))
			return false;
		assignment.getAssignVal().setSymbolTable(assignment.getSymbolTable());
		if (!(Boolean)assignment.getAssignVal().accept(this))
			return false;
		
		return true;
	}

	@Override
	public Object visit(CallStmt callStatement) {
		callStatement.getCall().setSymbolTable(callStatement.getSymbolTable());
		if (!(Boolean)callStatement.getCall().accept(this))
			return false;
		
		return true;
	}

	@Override
	public Object visit(ReturnStmt returnStatement) {
		if (returnStatement.hasValue()) {
			returnStatement.getValue().setSymbolTable(returnStatement.getSymbolTable());
			if (!(Boolean)returnStatement.getValue().accept(this))
				return false;
		}
		
		returnStatement.setMethodType(this.currentMethodType);
		return true;
	}

	@Override
	public Object visit(IfStmt ifStatement) {
		ifStatement.getCond().setSymbolTable(ifStatement.getSymbolTable());
		if (!(Boolean)ifStatement.getCond().accept(this))
			return false;
		ifStatement.getStmt().setSymbolTable(ifStatement.getSymbolTable());
		if (!(Boolean)ifStatement.getStmt().accept(this))
			return false;
		if (ifStatement.hasElse()) {
			ifStatement.getElseStmt().setSymbolTable(ifStatement.getSymbolTable());
			if (!(Boolean)ifStatement.getElseStmt().accept(this))
				return false;
		}
		return true;
	}

	@Override
	public Object visit(WhileStmt whileStatement) {
		whileStatement.getCond().setSymbolTable(whileStatement.getSymbolTable());
		if (!(Boolean)whileStatement.getCond().accept(this))
			return false;
		whileStatement.getStmt().setSymbolTable(whileStatement.getSymbolTable());
		if (!(Boolean)whileStatement.getStmt().accept(this))
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
		SymbolTable blockStmntSymbolTable = new SymbolTable("block#" + blockCounter, SymbolTableType.STATEMENT_BLOCK);
		statementsBlock.getSymbolTable().addTableChild(
				blockStmntSymbolTable.getId(), blockStmntSymbolTable);
		blockStmntSymbolTable.setParentSymbolTable(statementsBlock.getSymbolTable());
		for (Stmt stmnt : statementsBlock.getStatements()) {
			stmnt.setSymbolTable(blockStmntSymbolTable);
			if (!(Boolean)stmnt.accept(this))
				return false;
		}
		
		return true;
	}

	@Override
	public Object visit(LocalVarStmt localVariable) {
		if (localVariable.hasInitValue()) {
			localVariable.getInitValue().setSymbolTable(localVariable.getSymbolTable());
			if (!(Boolean)localVariable.getInitValue().accept(this))
				return false;
		}
		
		type_table.Type localVarType = typeTable.getTypeFromASTTypeNode(localVariable.getType());
		if (!addEntryAndCheckDuplication(localVariable.getSymbolTable(), 
				new SymbolEntry(localVariable.getName(), localVarType, SymbolKind.VARIABLE))) {
			this.semanticErrorThrower = new SemanticErrorThrower(localVariable.getLine(),
					"variable " + localVariable.getName() + " is initialized more than once");
			
			return false;
		}
		
		localVariable.setEntryType(localVarType);
		return true;
	}

	@Override
	public Object visit(VarLocationExpr location) {
		SymbolEntry varEntry;
		if (location.isExternal()) {
			location.getLocation().setSymbolTable(location.getSymbolTable());
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
			varEntry = getVariableSymbolEntry(location.getName(),  location.getSymbolTable());
			if (varEntry == null) {
				this.semanticErrorThrower = new SemanticErrorThrower(location.getLine(),
						"variable " + location.getName() + " is not initialized");
				return false;
			}
			if (varEntry.getType().isClassType()) 
				this.currentClassSymbolTablePoint = this.rootSymbolTable.findChildSymbolTable(varEntry.getType().toString());	
		}
		location.setEntryType(varEntry.getType());;
		return true;
	}

	@Override
	public Object visit(ArrayLocationExpr location) {
		location.getArray().setSymbolTable(location.getSymbolTable());
		if (!(Boolean)location.getArray().accept(this))
			return false;

		location.getIndex().setSymbolTable(location.getSymbolTable());
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
		SymbolEntry methodEntry = getMethodSymbolEntryFromExternalCall(call.getName(), SymbolKind.STATIC_METHOD, clsSymbolTable);
		if(methodEntry == null) {
			this.semanticErrorThrower = new SemanticErrorThrower(call.getLine(),
					"the method " + call.getName() + " dosen't exist");
			return false;
		}
		call.setMethodType(methodEntry.getType());
		for (Expr arg : call.getArguments()) {
			arg.setSymbolTable(call.getSymbolTable());
			if (!(Boolean)arg.accept(this))
				return false;
		}
		
		return true;
	}

	@Override
	public Object visit(VirtualCallExpr call) {
		SymbolEntry methodEntry;
		if (call.isExternal()) {
			call.getLocation().setSymbolTable(call.getSymbolTable());
			if (!(Boolean)call.getLocation().accept(this))
				return false;
			methodEntry = getMethodSymbolEntryFromExternalCall(call.getName(), SymbolKind.VIRTUAL_METHOD, this.currentClassSymbolTablePoint);
		}
		else 
			methodEntry = getMethodSymbolEntryFromInternalCall(call.getName(), call.getSymbolTable());

		if(methodEntry == null) {
			this.semanticErrorThrower = new SemanticErrorThrower(call.getLine(),
					"the method " + call.getName() + " dosen't exist");
			return false;
		}
		call.setMethodType(methodEntry.getType());
		for (Expr arg : call.getArguments()) {
			arg.setSymbolTable(call.getSymbolTable());
			if (!(Boolean)arg.accept(this))
				return false;
		}
		
		return true;
	}

	@Override
	public Object visit(ThisExpr thisExpression) {
		SymbolTable bottomSymbolTable = thisExpression.getSymbolTable();
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
		newArray.getSize().setSymbolTable(newArray.getSymbolTable());
		if (!(Boolean)newArray.getSize().accept(this))
			return false;

		return true;
	}

	@Override
	public Object visit(LengthExpr length) {
		length.getArray().setSymbolTable(length.getSymbolTable());
		if (!(Boolean)length.getArray().accept(this))
			return false;
		
		return true;
	}

	@Override
	public Object visit(MathBinaryOpExpr binaryOp) {
		binaryOp.getFirstOperand().setSymbolTable(binaryOp.getSymbolTable());
		if(!(Boolean)binaryOp.getFirstOperand().accept(this))
			return false;
		binaryOp.getSecondOperand().setSymbolTable(binaryOp.getSymbolTable());
		if(!(Boolean)binaryOp.getSecondOperand().accept(this))
			return false;

		return true;
	}

	@Override
	public Object visit(LogicalBinaryOpExpr binaryOp) {
		binaryOp.getFirstOperand().setSymbolTable(binaryOp.getSymbolTable());
		if(!(Boolean)binaryOp.getFirstOperand().accept(this))
			return false;
		binaryOp.getSecondOperand().setSymbolTable(binaryOp.getSymbolTable());
		if(!(Boolean)binaryOp.getSecondOperand().accept(this))
			return false;
		
		return true;
	}

	@Override
	public Object visit(MathUnaryOpExpr unaryOp) {
		unaryOp.getOperand().setSymbolTable(unaryOp.getSymbolTable());
		if(!(Boolean)unaryOp.getOperand().accept(this))
			return false;
		
		return true;
	}

	@Override
	public Object visit(LogicalUnaryOpExpr unaryOp) {
		unaryOp.getOperand().setSymbolTable(unaryOp.getSymbolTable());
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
		expressionBlock.getExpr().setSymbolTable(expressionBlock.getSymbolTable());
		if (!(Boolean)expressionBlock.getExpr().accept(this))
			return false;

		return true;
	}
	
	/**
	 * Visits any kind of method.
	 * 
	 * @param method  Method to visit.
	 * @return 		  True if the visit passed semantic checks, otherwise returns false.
	 */
	private Object visitMethod(Method method) {
		SymbolTable currentMethodSymbolTable = method.getSymbolTable().findChildSymbolTable(
				method.getName());
		this.currentMethodType = method.getEntryType();
		for (Formal formal : method.getFormals()) {
			formal.setSymbolTable(currentMethodSymbolTable);
			if (!(Boolean)formal.accept(this))
				return false;
		}
		
		for (Stmt stmnt : method.getStatements()) {
			stmnt.setSymbolTable(currentMethodSymbolTable);
			if(!(Boolean)stmnt.accept(this))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Adds a new symbol entry to the symbol table while checking if there is no variable duplication. 
	 * 
	 * @param table  Symbol table.
	 * @param entry  Symbol entry to add to table.
	 * @return	     True if there is no variable duplication and entry was added successfully,
	 * 				 otherwise returns false.
	 */
	private Boolean addEntryAndCheckDuplication(SymbolTable table, SymbolEntry entry) {
		if (table.hasEntry(entry.getId()))
			return false;
		if (entry.getKind() == SymbolKind.FIELD) {
			SymbolTable scanningTable = table.getParentSymbolTable();
			while (scanningTable.getTableType() != SymbolTableType.GLOBAL) {
				if (scanningTable.hasEntry(entry.getId()))
					return false;
				scanningTable = scanningTable.getParentSymbolTable();
			}
		}
		if (entry.getKind().isMethodKind()) {
			SymbolTable scanningTable = table.getParentSymbolTable();
			while (scanningTable.getTableType() != SymbolTableType.GLOBAL) {
				if (scanningTable.hasEntry(entry.getId())) {
					if (!scanningTable.getEntry(entry.getId()).getType().equals(entry.getType()))
						return false;
					else
						break;
				}
			}
		}
		table.addEntry(entry.getId(), entry);
		
		return true;
	}
	
	/**
	 * Gets the symbol kind of the specified Method node.
	 * 
	 * @param method  A Method node.
	 * @return		  the symbol kind of the specified Method node.
	 */
	private SymbolKind getMethodKind(Method method) {
		if (method instanceof VirtualMethod)
			return SymbolKind.VIRTUAL_METHOD;
		
		return SymbolKind.STATIC_METHOD;
	}
	
	/**
	 * Searches for a symbol entry of a local variable. 
	 * The entry can be of kind Local Variable, Method Parameter or a Field.
	 * 
	 * @param name				 Name of symbol entry to look for.
	 * @param bottomSymbolTable  The bottom symbol table.
	 * @return					 The symbol entry of a local variable. 
	 */
	private SymbolEntry getVariableSymbolEntry(String name, SymbolTable bottomSymbolTable) {
		if ((bottomSymbolTable.getTableType() == SymbolTableType.METHOD) || 
				(bottomSymbolTable.getTableType() == SymbolTableType.STATEMENT_BLOCK)) {
			// climb the symbol table tree up to the symbol table of the method from which contains the variable.
			while (bottomSymbolTable.getTableType().equals(SymbolTableType.STATEMENT_BLOCK)) {
				if (bottomSymbolTable.hasEntry(name))
					return bottomSymbolTable.getEntry(name);
				bottomSymbolTable = bottomSymbolTable.getParentSymbolTable();
			}
			
			// check method table:
			if (bottomSymbolTable.hasEntry(name))
				return bottomSymbolTable.getEntry(name);
			
			String containigMethodName = bottomSymbolTable.getId();
			bottomSymbolTable = bottomSymbolTable.getParentSymbolTable();
			
			// If a variable was called from a static method, it can't be a filed.
			// There so, it must be initialized in one of the method parameters or inside the method.
			// In that case, the check is done and the entry was not found.
			if (bottomSymbolTable.getEntry(containigMethodName).getKind() == 
					SymbolKind.STATIC_METHOD)
				return null;
		}
		
		// check class tables for a suitable field:
		while (bottomSymbolTable.getTableType() != SymbolTableType.GLOBAL) {
			SymbolTable clsTable = bottomSymbolTable;
			if (clsTable.hasEntry(name))
				if (clsTable.getEntry(name).getKind() == SymbolKind.FIELD)
					return clsTable.getEntry(name);
			bottomSymbolTable = bottomSymbolTable.getParentSymbolTable();
		}

		return null;
	}
	
	/**
	 * Searches for a method symbol entry from a static call or from a virtual call with external scope.
	 * 
	 * @param name		  			  Name of symbol entry to look for.
	 * @param methodKind  			  The method kind.
	 * @param bottomClassSymbolTable  The methods assume it has a CLASS symbol table type.
	 * @return						  The method symbol.
	 */
	private SymbolEntry getMethodSymbolEntryFromExternalCall(
			String name, SymbolKind methodKind, SymbolTable bottomClassSymbolTable) {
		while (bottomClassSymbolTable != null) {
			if (bottomClassSymbolTable.hasEntry(name))
				if (bottomClassSymbolTable.getEntry(name).getKind() == methodKind) // An external method mast be consistent with the call type.
					return bottomClassSymbolTable.getEntry(name);
			bottomClassSymbolTable = bottomClassSymbolTable.getParentSymbolTable();
		}
		return null;
	}
	
	/**
	 * Searches for a method symbol entry from a virtual call without external scope.
	 * - If the call was executed from a virtual method then a method entry with the same name and with any method kind (virtual or static) is a legal entry.
	 * - If the call was executed from a static method then only a method entry of kind static method with the same name is a legal entry.
	 * 
	 * @param name				 Name of symbol entry to look for.
	 * @param bottomSymbolTable  The methods assume it has a METHOD or a STATEMENT_BLCOK symbol table type.
	 * @return					 The method symbol.
	 */
	private SymbolEntry getMethodSymbolEntryFromInternalCall(
			String name, SymbolTable bottomSymbolTable) {
		// climb the symbol table tree up to the symbol table of the method from which the call was executed.
		while (bottomSymbolTable.getTableType().equals(SymbolTableType.STATEMENT_BLOCK)) 
			bottomSymbolTable = bottomSymbolTable.getParentSymbolTable();
		
		// identify kind of the method from which the call was executed (static or virtual).
		SymbolKind scopeMethodKind = bottomSymbolTable.getParentSymbolTable().
				getEntry(bottomSymbolTable.getId()).getKind();
		SymbolTable bottomClassSymbolTable = bottomSymbolTable = bottomSymbolTable.getParentSymbolTable();
		while (bottomClassSymbolTable.getTableType() != SymbolTableType.GLOBAL) {
			if (bottomClassSymbolTable.hasEntry(name)) {
				if (bottomClassSymbolTable.getEntry(name).getKind().isMethodKind()) { //found a method with same name
					if (scopeMethodKind == SymbolKind.VIRTUAL_METHOD) // from a virtual method the call can be to both static and virtual methods.
						return bottomClassSymbolTable.getEntry(name);
					if (scopeMethodKind == SymbolKind.STATIC_METHOD) { // from a static method the call must be to a static method.
						if (bottomClassSymbolTable.getEntry(name).getKind() == SymbolKind.STATIC_METHOD)
							return bottomClassSymbolTable.getEntry(name);
						else
							return null; // call to a virtual method in a virtual call without external scope from a static call is illegal.
					}
				}
					
			}
			bottomClassSymbolTable = bottomClassSymbolTable.getParentSymbolTable();
		}
		return null;
	}
}
