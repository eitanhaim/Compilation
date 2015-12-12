package semantic_analysis;

import java.util.ArrayList;
import java.util.List;
import ast.*;
import ic.CompilerUtils;
import type_table.ClassType;
import type_table.MethodType;
import type_table.MethodType.VirtualOrStatic;
import type_table.Type;
import type_table.TypeTable;

/**
 * Table Constructor visitor - travels along the AST and build the scope symbol tables
 */
public class SymbolTableConstructor implements PropagatingVisitor<SymbolTable, Object> {

	private String ICFilePath;
	private ASTNode root;
	
	private StringBuffer errors;
	private boolean didIFoundMainMethod;

	public SymbolTableConstructor(String path, ASTNode root) {
		this.ICFilePath = path.substring(path.lastIndexOf("\\") + 1);
		this.root = root;
		this.didIFoundMainMethod = false;
		errors = new StringBuffer();
	}

	public ASTNode construct() {
		ASTNode resultNode = null;
		try {
			resultNode = (ASTNode) root.accept(this, null);

            if (!didIFoundMainMethod) {
                errors.append("Main method not found, please define the main method as: static void main(string[] args)");
            }

            if (errors.length() > 0) {
                System.err.println(errors.toString());
            }

		} catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

		return resultNode;
	}

	@Override
	public Object visit(Program program, SymbolTable scope) throws Exception {
		SymbolTable globalTable = new GlobalSymbolTable("Global", ICFilePath);

		globalTable.setParentSymbolTable(null);
		
		for (ICClass icClass : program.getClasses()) {
			globalTable.insert(icClass.getName(),
					(SymbolTableRow) icClass.accept(this, globalTable));
		}
		
		for (ICClass icClass : program.getClasses()) {
			if (icClass.hasSuperClass()) {
				for (ICClass icClassOther : program.getClasses()) {
					if (icClass.getSuperClassName().equals(icClassOther.getName())) {
						icClass.enclosingScope().setParentSymbolTable(icClassOther.enclosingScope());
						icClassOther.enclosingScope().addChild(icClass.enclosingScope());
						break;
					}
				}
			} else {
				globalTable.addChild(icClass.enclosingScope());
			}
		}
		
		program.setEnclosingScope(globalTable);
		
		globalTable.AddUniqueTypes();
		
		return null;
	}

	@Override
	public Object visit(ICClass icClass, SymbolTable scope) throws Exception {
		SymbolTableRow currentSymbol;

		SymbolTable classTable = new ClassSymbolTable(icClass.getName());
		ClassType classType = new ClassType(icClass);
		
		for (Field field : icClass.getFields()) {
			currentSymbol = (SymbolTableRow) field.accept(this, classTable);
			
			try {
				classTable.insert(field.getName(), currentSymbol);
			} catch (SemanticError err) {
				errors.append(err.getMessage() + " at line " + field.getLine() + "\n");
			}
		}
		
		for (Method method : icClass.getMethods()) {
			currentSymbol = (SymbolTableRow) method.accept(this, classTable);
			classTable.insert(method.getName(), currentSymbol);
			classTable.addChild(method.enclosingScope());
		}

		if (!icClass.hasSuperClass()) {
			classTable.setParentSymbolTable(scope);
		}

		icClass.setEnclosingScope(classTable);
		
		TypeTable.addClassType(classType);
		classTable.AddUniqueTypes();
		
		return new SymbolTableRow(icClass.getName(), classType, Kind.CLASS);
	}

	@Override
	public Object visit(Field field, SymbolTable scope) throws Exception {
		SymbolTable fieldTable = new VarSymbolTable(field.getName());

		Type fieldType = (Type)field.getType().accept(this, scope);

		fieldTable.setParentSymbolTable(scope);
		field.setEnclosingScope(fieldTable);

		return new SymbolTableRow(field.getName(), fieldType, Kind.FIELD);
	}

	@Override
	public Object visit(VirtualMethod method, SymbolTable scope) throws Exception {
		SymbolTableRow currentSymbol;
		SymbolTable methodTable = new MethodSymbolTable(method.getName());

		methodTable.setParentSymbolTable(scope);

		List<Type> paramTypes = new ArrayList<>();
		
		if (method.getName().equals("main")) {
			errors.append("Main method not found, please define the main method as: static void main(string[] args)");
		}
		
		for (Formal formal : method.getFormals()) {
			currentSymbol = (SymbolTableRow) formal.accept(this, methodTable);
			paramTypes.add(currentSymbol.getType());
		}
		
		if (method.getStatements().size() > 0) {
			for (Stmt stmt : method.getStatements()) {
				stmt.accept(this, methodTable);
			}
		}

		Type methodReturnType = (Type)method.getType().accept(this, scope);
		MethodType methodType = new MethodType(method.getName(),
				VirtualOrStatic.Virtual, paramTypes, methodReturnType);
		
		Type thisType = TypeTable.getClassType(scope.getId());
		methodTable.insert("$this", new SymbolTableRow("$this", thisType, Kind.THIS));
		methodTable.insert("$ret", new SymbolTableRow("$ret_type", methodReturnType, Kind.RET_VAR));
		
		method.setEnclosingScope(methodTable);

		return new SymbolTableRow(method.getName(), methodType, Kind.METHOD);
	}

	@Override
	public Object visit(StaticMethod method, SymbolTable scope) throws Exception {
		SymbolTableRow currentSymbol;
		SymbolTable methodTable = new MethodSymbolTable(method.getName());

		methodTable.setParentSymbolTable(scope);
		
		List<Type> paramTypes = new ArrayList<>();

		for (Formal formal : method.getFormals()) {
			currentSymbol = (SymbolTableRow) formal.accept(this, methodTable);
			paramTypes.add(currentSymbol.getType());
		}

		for (Stmt stmt : method.getStatements()) {
			stmt.accept(this, methodTable);
		}

		if (method.getName().equals("main")) {
			Formal mainFormal; 
			if (method.getFormals().size() == 1) {
				mainFormal = method.getFormals().get(0);
				boolean isOneDimenstion = mainFormal.getType().getDimension() == 1; 
				boolean isString = mainFormal.getType().getName().equals("string");
				
				if (isOneDimenstion && isString) {
					didIFoundMainMethod = true;
				}
			}
		}
		
		Type methodReturnType = (Type)method.getType().accept(this, scope);
		MethodType methodType = new MethodType(method.getName(),
				VirtualOrStatic.Static, paramTypes, methodReturnType);
		
		methodTable.insert("$ret", new SymbolTableRow("$ret_type", methodReturnType, Kind.RET_VAR));
		
		method.setEnclosingScope(methodTable);
		
		return new SymbolTableRow(method.getName(), methodType, Kind.METHOD);
	}

	@Override
	public Object visit(LibraryMethod method, SymbolTable scope) throws Exception {
		SymbolTableRow currentSymbol;
		SymbolTable libMethodTable = new MethodSymbolTable(method.getName());

		libMethodTable.setParentSymbolTable(scope);

		List<Type> paramTypes = new ArrayList<>();

		for (Formal formal : method.getFormals()) {
			currentSymbol = (SymbolTableRow) formal.accept(this, libMethodTable);
			paramTypes.add(currentSymbol.getType());
		}
		
		Type methodReturnType = (Type)method.getType().accept(this, scope);
		MethodType methodType = new MethodType(method.getName(),
				VirtualOrStatic.Static, paramTypes, methodReturnType);

		method.setEnclosingScope(libMethodTable);

		return new SymbolTableRow(method.getName(), methodType, Kind.METHOD);
	}

	@Override
	public Object visit(Formal formal, SymbolTable scope) throws Exception {
		Type formalType = (Type)formal.getType().accept(this, scope);
		
		SymbolTableRow formalSymbol = new SymbolTableRow(formal.getName(),
					formalType, Kind.PARAM);
		
		scope.insert(formal.getName(), formalSymbol);

		return formalSymbol;
	}

	@Override
	public Object visit(PrimitiveType type, SymbolTable scope) throws Exception {
		return CompilerUtils.primitiveTypeToMyType(type);
	}

	@Override
	public Object visit(UserType type, SymbolTable scope) throws Exception {
		return CompilerUtils.userTypeToMyType(type);
	}

	@Override
	public Object visit(IfStmt ifStatement, SymbolTable scope) throws Exception {
		// System.out.println("------------------------- if");

		ifStatement.getStmt().accept(this, scope);
		
		if (ifStatement.hasElse()) {
			// System.out.println("------------------------- else");
			ifStatement.getElseStmt().accept(this, scope);
		}
		// System.out.println("------------------------- end if");
		return null;
	}

	@Override
	public Object visit(WhileStmt whileStatement, SymbolTable scope) throws Exception {
		// System.out.println("------------------------- while");
		return whileStatement.getStmt().accept(this, scope);
	}

	@Override
	public Object visit(StmtBlock statementsBlock, SymbolTable scope) throws Exception {
		// System.out.println("------------------------- statemnts block");

		SymbolTable table = new StatementBlockSymbolTable("statement block in " 
				+ scope.getId());

		table.setParentSymbolTable(scope);
		
		for (Stmt stmt : statementsBlock.getStatements()) {
			if (stmt.accept(this, table) != null) {
				if (stmt.enclosingScope() != null) {
					table.addChild(stmt.enclosingScope());
				}
			}
		}

		scope.addChild(table);
		statementsBlock.setEnclosingScope(table);

		return null;
	}

	@Override
	public Object visit(LocalVarStmt localVariable, SymbolTable scope) throws Exception {
		Type varType = (Type)localVariable.getType().accept(this, scope);

		SymbolTableRow sym = new SymbolTableRow(localVariable.getName(),
				varType, Kind.VAR);
		scope.insert(sym.getId(), sym);

		return sym;
	}

	@Override
	public Object visit(AssignStmt assignment, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(CallStmt callStatement, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(ReturnStmt returnStatement, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(BreakStmt breakStatement, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(ContinueStmt continueStatement, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(VarLocationExpr location, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(ArrayLocationExpr location, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(StaticCallExpr call, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(VirtualCallExpr call, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(ThisExpr thisExpression, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(NewClassExpr newClass, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(NewArrayExpr newArray, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(LengthExpr length, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(MathBinaryOpExpr binaryOp, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(LogicalBinaryOpExpr binaryOp, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(MathUnaryOpExpr unaryOp, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(LogicalUnaryOpExpr unaryOp, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(LiteralExpr literal, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(ExprBlock expressionBlock, SymbolTable context) throws Exception {
		return null;
	}
}