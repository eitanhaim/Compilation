package ast;

/**
 * AST node for an expression in parentheses.
 */
public class ExprBlock extends Expr {
	private Expr expr;

	/**
	 * Constructs a new expression in parentheses node.
	 * 
	 * @param expr  The expression.
	 */
	public ExprBlock(Expr expr) {
		super(expr.getLine());
		this.expr = expr;
	}

	public Expr getExpr() {
		return expr;
	}
	
	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) throws Exception {
		return visitor.visit(this, context);
	}
}
