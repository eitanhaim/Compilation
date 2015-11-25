package ast;

/**
 * An AST node for a class field.
 */
public class Field extends ASTNode {
	private Type type;
	private String name;

	/**
	 * Constructs a new field node.
	 * 
	 * @param type  Data type of field.
	 * @param name  Name of field.
	 */
	public Field(Type type, String name) {
		super(type.getLine());
		this.type = type;
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
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
