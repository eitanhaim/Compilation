package ast;

/**
 * An AST node for an array length expression.
 */
public class LengthExpr extends Expr {
	private Expr array;

	/**
	 * Constructs a new array length expression node.
	 * 
	 * @param array  Expression representing an array.
	 */
	public LengthExpr(Expr array) {
		super(array.getLine());
		this.array = array;
	}

	public Expr getArray() {
		return array;
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
