package ast;

/**
 * Array reference AST node.
 * 
 */
public class ArrayLocationExpr extends LocationExpr {

	private Expr array;

	private Expr index;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new array reference node.
	 * 
	 * @param array
	 *            Expression representing an array.
	 * @param index
	 *            Expression representing a numeric index.
	 */
	public ArrayLocationExpr(Expr array, Expr index) {
		super(array.getLine());
		this.array = array;
		this.index = index;
	}

	public Expr getArray() {
		return array;
	}

	public Expr getIndex() {
		return index;
	}
}