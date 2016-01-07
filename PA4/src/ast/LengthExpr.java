package ast;

/**
 * Array length expression AST node.
 */
public class LengthExpr extends Expr {

	private Expr array;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new array length expression node.
	 * 
	 * @param array
	 *            Expression representing an array.
	 */
	public LengthExpr(Expr array) {
		super(array.getLine());
		this.array = array;
	}

	public Expr getArray() {
		return array;
	}

}
