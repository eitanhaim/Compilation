package ast;

/**
 * AST node a method call statement.
 */
public class CallStmt extends Stmt {
	private CallExpr call;

	/**
	 * Constructs a new method call statement node.
	 * 
	 * @param call  Method call expression.
	 */
	public CallStmt(CallExpr call) {
		super(call.getLine());
		this.call = call;
	}

	public CallExpr getCall() {
		return call;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		return visitor.visit(this, context);
	}
}
