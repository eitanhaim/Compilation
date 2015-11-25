package ast;

/**
 * An AST node for a method parameter.
 */
public class Formal extends ASTNode {
	private Type type;
	private String name;

	/**
	 * Constructs a new parameter node.
	 * 
	 * @param type  Data type of parameter.
	 * @param name  Name of parameter.
	 */
	public Formal(Type type, String name) {
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
