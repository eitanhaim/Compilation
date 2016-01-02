package ast;

/**
 * Method call statement AST node.
 */
public class CallStmt extends Stmt {

	private CallExpr call;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new method call statement node.
	 * 
	 * @param call
	 *            Method call expression.
	 */
	public CallStmt(CallExpr call) {
		super(call.getLine());
		this.call = call;
	}

	public CallExpr getCall() {
		return call;
	}

}
