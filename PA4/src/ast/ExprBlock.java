package ast;

/**
 * AST node for expression in parentheses.
 */
public class ExprBlock extends Expr {

	private Expr expression;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new expression in parentheses node.
	 * 
	 * @param expression
	 *            The expression.
	 */
	public ExprBlock(Expr expression) {
		super(expression.getLine());
		this.expression = expression;
	}

	public Expr getExpression() {
		return expression;
	}

}
