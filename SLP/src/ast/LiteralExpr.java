package ast;

import ic.LiteralType;

/**
 * An AST node for a literal value.
 */
public class LiteralExpr extends Expr {
	private LiteralType type;
	private Object value;

	/**
	 * Constructs a new literal node.
	 * 
	 * @param line  Line number of the literal.
	 * @param type  Literal type.
	 */
	public LiteralExpr(int line, LiteralType type) {
		super(line);
		this.type = type;
		value = type.getValue();
	}

	/**
	 * Constructs a new literal node, with a value.
	 * 
	 * @param line   Line number of the literal.
	 * @param type   Literal type.
	 * @param value  Value of literal.
	 */
	public LiteralExpr(int line, LiteralType type, Object value) {
		this(line, type);
		this.value = value;
	}

	public LiteralType getType() {
		return type;
	}

	public Object getValue() {
		return value;
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
