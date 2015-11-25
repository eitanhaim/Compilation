package ast;

/**
 * AST visitor interface. 
 * Declares methods for visiting each type of AST node.
 */
public interface Visitor {
	public void visit(Program program);
	public void visit(ICClass icClass);
	public void visit(Field field);
	public void visit(VirtualMethod method);
	public void visit(StaticMethod method);
	public void visit(LibraryMethod method);
	public void visit(Formal formal);
	public void visit(PrimitiveType type);
	public void visit(UserType type);
	public void visit(AssigmStmt assignment);
	public void visit(CallStmt callStatement);
	public void visit(ReturnStmt returnStatement);
	public void visit(IfStmt ifStatement);
	public void visit(WhileStmt whileStatement);
	public void visit(BreakStmt breakStatement);
	public void visit(ContinueStmt continueStatement);
	public void visit(StmtBlock statementsBlock);
	public void visit(LocalVarStmt localVariable);
	public void visit(VarLocationExpr location);
	public void visit(ArrayLocationExpr location);
	public void visit(StaticCallExpr call);
	public void visit(VirtualCallExpr call);
	public void visit(ThisExpr thisExpression);
	public void visit(NewClassExpr newClass);
	public void visit(NewArrayExpr newArray);
	public void visit(LengthExpr length);
	public void visit(MathBinaryOp binaryOp);
	public void visit(LogicalBinaryOp binaryOp);
	public void visit(MathUnaryOp unaryOp);
	public void visit(LogicalUnaryOp unaryOp);
	public void visit(LiteralExpr literal);
	public void visit(ExprBlock expressionBlock);
}
