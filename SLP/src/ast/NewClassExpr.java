package ast;

/**
 * An AST node for a class instance creation.
 */
public class NewClassExpr extends NewExpr {
	private String name;

	/**
	 * Constructs a new class instance creation expression node.
	 * 
	 * @param line  Line number of expression.
	 * @param name  Name of class.
	 */
	public NewClassExpr(int line, String name) {
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
