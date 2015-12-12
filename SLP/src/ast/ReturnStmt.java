package ast;

/**
 * An AST node for a return statement.
 */
public class ReturnStmt extends Stmt {
	private Expr value = null;

	/**
	 * Constructs a new return statement node, with no return value.
	 * 
	 * @param line  Line number of return statement.
	 */
	public ReturnStmt(int line) {
		super(line);
	}

	/**
	 * Constructs a new return statement node.
	 * 
	 * @param line   Line number of return statement.
	 * @param value  Return value.
	 */
	public ReturnStmt(int line, Expr value) {
		this(line);
		this.value = value;
	}

	public boolean hasValue() {
		return (value != null);
	}

	public Expr getValue() {
		return value;
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
