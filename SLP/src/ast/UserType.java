package ast;

/**
 * An AST node for an user-defined data type AST node.
 */
public class UserType extends Type {
	private String name;

	/**
	 * Constructs a new user-defined data type node.
	 * 
	 * @param line  Line number of type declaration.
	 * @param name  Name of data type.
	 */
	public UserType(int line, String name) {
		super(line);
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
