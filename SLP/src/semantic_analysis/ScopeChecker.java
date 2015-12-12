package semantic_analysis;

import java.util.Map.Entry;
import ast.*;
import type_table.ClassType;
import type_table.MethodType;
import type_table.TypeTable;

public class ScopeChecker implements PropagatingVisitor<SymbolTable, Object>, Tester {

	private StringBuffer errors;
	private int loopCounter;
	private ASTNode root;

	public ScopeChecker(ASTNode root) {
		this.root = root;
		errors = new StringBuffer();
	}

	@Override
	public boolean isAllGood() {
		return errors.length() == 0;
	}

	@Override
	public void test() throws Exception {
		loopCounter = 0;
		root.accept(this, null);
	}

    public String getErrors() {
        return errors.toString();
    }

	@Override
	public Object visit(ArrayLocationExpr location, SymbolTable context)
			throws Exception {
		return location.getArray().accept(this, context);
	}

	@Override
	public Object visit(AssignStmt assignment, SymbolTable context)
			throws Exception {
		assignment.getVariable().accept(this, context);
		assignment.getAssignVal().accept(this, context);
		return null;
	}

	@Override
	public Object visit(BreakStmt breakStatement, SymbolTable context)
			throws Exception {
		if (loopCounter == 0) {
			errors.append("SemanticError: 'break' must be inside a loop at line "
					+ breakStatement.getLine() + "\n");
		}

		return null;
	}

	@Override
	public Object visit(CallStmt callStatement, SymbolTable context)
			throws Exception {
		callStatement.getCall().accept(this, context);
		return null;
	}

	@Override
	public Object visit(ContinueStmt continueStatement, SymbolTable context)
			throws Exception {
		if (loopCounter == 0) {
			errors.append("SemanticError: 'continue' must be inside a loop at line "
					+ continueStatement.getLine() + "\n");
		}

		return null;
	}

	@Override
	public Object visit(ExprBlock expressionBlock, SymbolTable context)
			throws Exception {
		expressionBlock.getExpr().accept(this, context);
		return null;
	}

	@Override
	public Object visit(Field field, SymbolTable context) throws Exception {
		int counter = 0;

		for (Entry<String, SymbolTableRow> entry : context.getIterator()) {
			if (entry.getKey().equals(field.getName())) {
				counter++;
			}
		}

		if (counter > 1) {
			errors.append("Error");
		}

		return null;
	}

	@Override
	public Object visit(Formal formal, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(ICClass icClass, SymbolTable context) throws Exception {
		SymbolTable myTable = icClass.enclosingScope();
		SymbolTable parentTable = myTable.getParentSymbolTable();
		SymbolTable myParentParentTable;

		if (myTable.getId().equals(parentTable.getId())) {
			errors.append("SemanticError: cyclic inheritance detected! at line "
					+ icClass.getLine() + "\n");
		} else {
			while (parentTable.getParentSymbolTable() != null) {
				myParentParentTable = parentTable.getParentSymbolTable();

				if (myTable.getId().equals(parentTable.getId())) {
					errors.append("SemanticError: cyclic inheritance detected! at line "
							+ icClass.getLine() + "\n");
					break;
				} else {
					if (parentTable.getId().equals(myParentParentTable.getId())) {
						errors.append("SemanticError: cyclic inheritance detected! at line "
								+ icClass.getLine() + "\n");
						break;
					} else {
						parentTable = parentTable.getParentSymbolTable();
					}
				}
			}
		}

		for (Field field : icClass.getFields()) {
			field.accept(this, myTable);
		}

		for (Method method : icClass.getMethods()) {
			method.accept(this, myTable);
		}

		return null;
	}

	@Override
	public Object visit(IfStmt ifStatement, SymbolTable context) throws Exception {
		ifStatement.getCond().accept(this, context);
		ifStatement.getStmt().accept(this, context);

		if (ifStatement.hasElse()) {
			ifStatement.getElseStmt().accept(this, context);
		}

		return null;
	}

	@Override
	public Object visit(LengthExpr length, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(LibraryMethod method, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(LiteralExpr literal, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(LocalVarStmt localVariable, SymbolTable context)
			throws Exception {
		if (localVariable.hasInitValue()) {
			localVariable.getInitValue().accept(this, context);
		}

		if (context.lookup(localVariable.getName()) == null) {
			errors.append("SemanticError: undefined local variable '"
					+ localVariable.getName() + "' at line "
					+ localVariable.getLine() + "\n");
		}

		return localVariable.getName();
	}

	@Override
	public Object visit(LogicalBinaryOpExpr binaryOp, SymbolTable context)
			throws Exception {

		binaryOp.getFirstOperand().accept(this, context);
		binaryOp.getSecondOperand().accept(this, context);

		return null;
	}

	@Override
	public Object visit(LogicalUnaryOpExpr unaryOp, SymbolTable context)
			throws Exception {
		unaryOp.getOperand().accept(this, context);
		return null;
	}

	@Override
	public Object visit(MathBinaryOpExpr binaryOp, SymbolTable context)
			throws Exception {
		binaryOp.getFirstOperand().accept(this, context);
		binaryOp.getSecondOperand().accept(this, context);

		return null;
	}

	@Override
	public Object visit(MathUnaryOpExpr unaryOp, SymbolTable context)
			throws Exception {
		unaryOp.getOperand().accept(this, context);
		return null;
	}

	@Override
	public Object visit(NewArrayExpr newArray, SymbolTable context)
			throws Exception {
		return null;
	}

	@Override
	public Object visit(NewClassExpr newClass, SymbolTable context)
			throws Exception {
		return null;
	}

	@Override
	public Object visit(PrimitiveType type, SymbolTable context)
			throws Exception {
		return null;
	}

	@Override
	public Object visit(Program program, SymbolTable context) throws Exception {
		for (ICClass icClass : program.getClasses()) {
			icClass.accept(this, program.enclosingScope());
		}

		return null;
	}

	@Override
	public Object visit(ReturnStmt returnStatement, SymbolTable context)
			throws Exception {
		if (returnStatement.hasValue()) {
			returnStatement.getValue().accept(this, context);
		}

		return null;
	}

	@Override
	public Object visit(StmtBlock statementsBlock, SymbolTable context)
			throws Exception {
		// System.out.println("----- StatementsBlock");
		for (Stmt stmt : statementsBlock.getStatements()) {
			stmt.accept(this, statementsBlock.enclosingScope());
		}

		return null;
	}

	@Override
	public Object visit(StaticCallExpr call, SymbolTable context) throws Exception {
		for (Expr expr : call.getArguments()) {
			expr.accept(this, context);
		}

		return null;
	}

	@Override
	public Object visit(StaticMethod method, SymbolTable context)
			throws Exception {
		SymbolTable myScope = method.enclosingScope();

		for (Stmt stmts : method.getStatements()) {
			stmts.accept(this, myScope);
		}

		return null;
	}

	@Override
	public Object visit(ThisExpr thisExpression, SymbolTable context)
			throws Exception {
		SymbolTable thisScope = context;

		while (thisScope.getKind() == Kind.BLOCK) {
			thisScope = thisScope.getParentSymbolTable();
		}

		if (((MethodType) thisScope.getType()).isStatic()) {
			errors.append("SemanticError: 'this' must be only in virtual methods at line "
					+ thisExpression.getLine() + "\n");
		}

		return "$this";
	}

	@Override
	public Object visit(UserType type, SymbolTable context) throws Exception {
		return null;
	}

	@Override
	public Object visit(VarLocationExpr location, SymbolTable context)
			throws Exception {
		if (location.isExternal()) {
			String external = (String) location.getLocation().accept(this, context);

			ClassType classType = TypeTable.getClassType(context.lookup(external).getType().getName());
			
			if (classType.getClassAST().enclosingScope().lookup(location.getName()) == null) {
				errors.append("SemanticError: undefined variable '"
						+ location.getName() + "' at line " + location.getLine()
						+ "\n");
			}
		} else {
            if (context.lookup(location.getName()) == null) {
				errors.append("SemanticError: undefined variable '"
						+ location.getName() + "' at line " + location.getLine()
						+ "\n");
			}
		}

		return location.getName();
	}

	@Override
	public Object visit(VirtualCallExpr call, SymbolTable context) throws Exception {
		if (call.isExternal()) {
			call.getLocation().accept(this, context);
		} else {
			if (context.lookup(call.getName()) == null) {
				errors.append("SemanticError: undefined method '"
						+ call.getName() + "' at line " + call.getLine() + "\n");
			}
		}

		for (Expr expr : call.getArguments()) {
			expr.accept(this, context);
		}

		return null;
	}

	@Override
	public Object visit(VirtualMethod method, SymbolTable context)
			throws Exception {
		SymbolTable myScope = method.enclosingScope();

		for (Stmt stmts : method.getStatements()) {
			stmts.accept(this, myScope);
		}

		return null;
	}

	@Override
	public Object visit(WhileStmt whileStatement, SymbolTable context)
			throws Exception {
		whileStatement.getCond().accept(this, context);

		loopCounter += 1;
		whileStatement.getStmt().accept(this, context);
		loopCounter -= 1;

		return null;
	}
}