package ast;

/**
 * An interface for a propagating AST visitor. 
 * The visitor passes down objects of type <code>DownType</code>
 * and propagates up objects of type <code>UpType</code>.
 */
public interface PropagatingVisitor<DownType, UpType> {
	UpType visit(Program program, DownType context) throws Exception;
	UpType visit(ICClass icClass, DownType context) throws Exception;
	UpType visit(Field field, DownType context) throws Exception;
    UpType visit(VirtualMethod method, DownType context) throws Exception;
	UpType visit(StaticMethod method, DownType context) throws Exception;
	UpType visit(LibraryMethod method, DownType context) throws Exception;
	UpType visit(Formal formal, DownType context) throws Exception;
	UpType visit(PrimitiveType type, DownType context) throws Exception;
	UpType visit(UserType type, DownType context) throws Exception;
	UpType visit(AssignStmt assignment, DownType context) throws Exception;
	UpType visit(CallStmt callStatement, DownType context) throws Exception;
	UpType visit(ReturnStmt returnStatement, DownType context) throws Exception;
	UpType visit(IfStmt ifStatement, DownType context) throws Exception;
	UpType visit(WhileStmt whileStatement, DownType context) throws Exception;
	UpType visit(BreakStmt breakStatement, DownType context) throws Exception;
	UpType visit(ContinueStmt continueStatement, DownType context) throws Exception;
	UpType visit(StmtBlock statementsBlock, DownType context) throws Exception;
	UpType visit(LocalVarStmt localVariable, DownType context) throws Exception;
	UpType visit(VarLocationExpr location, DownType context) throws Exception;
	UpType visit(ArrayLocationExpr location, DownType context) throws Exception;
	UpType visit(StaticCallExpr call, DownType context) throws Exception;
	UpType visit(VirtualCallExpr call, DownType context) throws Exception;
	UpType visit(ThisExpr thisExpression, DownType context) throws Exception;
	UpType visit(NewClassExpr newClass, DownType context) throws Exception;
	UpType visit(NewArrayExpr newArray, DownType context) throws Exception;
	UpType visit(LengthExpr length, DownType context) throws Exception;
	UpType visit(MathBinaryOpExpr binaryOp, DownType context) throws Exception;
	UpType visit(LogicalBinaryOpExpr binaryOp, DownType context) throws Exception;
	UpType visit(MathUnaryOpExpr unaryOp, DownType context) throws Exception;
	UpType visit(LogicalUnaryOpExpr unaryOp, DownType context) throws Exception;
	UpType visit(LiteralExpr literal, DownType context) throws Exception;
	UpType visit(ExprBlock expressionBlock, DownType context) throws Exception;
}