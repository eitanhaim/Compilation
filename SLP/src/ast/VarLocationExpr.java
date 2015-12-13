package ast;

/**
 * An AST node for a variable reference.
 */
public class VarLocationExpr extends LocationExpr {
	private Expr location = null;
	private String name;

	/**
	 * Constructs a new variable reference node.
	 * 
	 * @param line  Line number of reference.
	 * @param name  Name of variable.
	 */
	public VarLocationExpr(int line, String name) {
		super(line);
		this.name = name;
	}

	/**
	 * Constructs a new variable reference node, for an external location.
	 * 
	 * @param line  	Line number of reference.
	 * @param location  Location of variable.
	 * @param name		Name of variable.
	 */
	public VarLocationExpr(int line, Expr location, String name) {
		this(line, name);
		this.location = location;
	}

	public boolean isExternal() {
		return (location != null);
	}

	public Expr getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
