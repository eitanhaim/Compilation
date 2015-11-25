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
	public ExprBlock(Expr expression) {
		super(expression.getLine());
		this.expr = expression;
	}

	public Expr getExpr() {
		return expr;
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
