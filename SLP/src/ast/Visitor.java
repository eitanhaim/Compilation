package ast;

/**
 * AST visitor interface. 
 * Declares methods for visiting each type of AST node.
 */
public interface Visitor {
	public Object visit(Program program);
	public Object visit(ICClass icClass);
	public Object visit(Field field);
	public Object visit(VirtualMethod method);
	public Object visit(StaticMethod method);
	public Object visit(LibraryMethod method);
	public Object visit(Formal formal);
	public Object visit(PrimitiveType type);
	public Object visit(UserType type);
	public Object visit(AssigmStmt assignment);
	public Object visit(CallStmt callStatement);
	public Object visit(ReturnStmt returnStatement);
	public Object visit(IfStmt ifStatement);
	public Object visit(WhileStmt whileStatement);
	public Object visit(BreakStmt breakStatement);
	public Object visit(ContinueStmt continueStatement);
	public Object visit(StmtBlock statementsBlock);
	public Object visit(LocalVarStmt localVariable);
	public Object visit(VarLocationExpr location);
	public Object visit(ArrayLocationExpr location);
	public Object visit(StaticCallExpr call);
	public Object visit(VirtualCallExpr call);
	public Object visit(ThisExpr thisExpression);
	public Object visit(NewClassExpr newClass);
	public Object visit(NewArrayExpr newArray);
	public Object visit(LengthExpr length);
	public Object visit(MathBinaryOpExpr binaryOp);
	public Object visit(LogicalBinaryOpExpr binaryOp);
	public Object visit(MathUnaryOpExpr unaryOp);
	public Object visit(LogicalUnaryOpExpr unaryOp);
	public Object visit(LiteralExpr literal);
	public Object visit(ExprBlock expressionBlock);
}
