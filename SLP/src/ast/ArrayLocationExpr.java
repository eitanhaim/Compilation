package ast;

/**
 * An AST node for an Array reference.
 */
public class ArrayLocationExpr extends LocationExpr {
	private Expr array;
	private Expr index;

	/**
	 * Constructs a new array reference node.
	 * 
	 * @param array  Expression representing an array.
	 * @param index  Expression representing a numeric index.
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