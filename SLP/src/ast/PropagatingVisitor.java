package ast;

/** 
 * AST propagating visitor interface. 
 * The visitor passes down UpTypes of type <code>DownType</code>
 * and propagates up UpTypes of type <code>UpType</code>.
 */
public interface PropagatingVisitor<DownType,UpType> {
	/*public UpType visit(StmtBlock stmts, DownType d);
	public UpType visit(Stmt stmt, DownType d);
	public UpType visit(PrintStmt stmt, DownType d);
	public UpType visit(AssignStmt stmt, DownType d);
	public UpType visit(Expr expr, DownType d);
	public UpType visit(ReadIExpr expr, DownType d);
	public UpType visit(VarExpr expr, DownType d);
	public UpType visit(NumberExpr expr, DownType d);
	public UpType visit(UnaryOpExpr expr, DownType d);
	public UpType visit(BinaryOpExpr expr, DownType d);
	public UpType visit(Formal formal, DownType d);*/
	
	public UpType visit(Program program, DownType d);
	public UpType visit(ICClass icClass, DownType d);
	public UpType visit(Field field, DownType d);
	public UpType visit(VirtualMethod method, DownType d);
	public UpType visit(StaticMethod method, DownType d);
	public UpType visit(LibraryMethod method, DownType d);
	public UpType visit(Formal formal, DownType d);
	public UpType visit(PrimitiveType type, DownType d);
	public UpType visit(UserType type, DownType d);
	public UpType visit(AssigmStmt assignment, DownType d);
	public UpType visit(CallStmt callStatement, DownType d);
	public UpType visit(ReturnStmt returnStatement, DownType d);
	public UpType visit(IfStmt ifStatement, DownType d);
	public UpType visit(WhileStmt whileStatement, DownType d);
	public UpType visit(BreakStmt breakStatement, DownType d);
	public UpType visit(ContinueStmt continueStatement, DownType d);
	public UpType visit(StmtBlock statementsBlock, DownType d);
	public UpType visit(LocalVarStmt localVariable, DownType d);
	public UpType visit(VarLocationExpr location, DownType d);
	public UpType visit(ArrayLocationExpr location, DownType d);
	public UpType visit(StaticCallExpr call, DownType d);
	public UpType visit(VirtualCallExpr call, DownType d);
	public UpType visit(ThisExpr thisExpression, DownType d);
	public UpType visit(NewClassExpr newClass, DownType d);
	public UpType visit(NewArrayExpr newArray, DownType d);
	public UpType visit(LengthExpr length, DownType d);
	public UpType visit(MathBinaryOp binaryOp, DownType d);
	public UpType visit(LogicalBinaryOp binaryOp, DownType d);
	public UpType visit(MathUnaryOp unaryOp, DownType d);
	public UpType visit(LogicalUnaryOp unaryOp, DownType d);
	public UpType visit(LiteralExpr literal, DownType d);
	public UpType visit(ExprBlock expressionBlock, DownType d);
}