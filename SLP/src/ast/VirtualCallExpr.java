package ast;

import java.util.List;

/**
 * An AST node for a virtual method call.
 */
public class VirtualCallExpr extends CallExpr {
	private Expr location = null;

	/**
	 * Constructs a new virtual method call node.
	 * 
	 * @param line  	 Line number of call.
	 * @param name  	 Name of method.
	 * @param arguments  List of all method arguments.
	 */
	public VirtualCallExpr(int line, String name, List<Expr> arguments) {
		super(line, name, arguments);
	}

	/**
	 * Constructs a new virtual method call node, for an external location.
	 * 
	 * @param lineLine   number of call.
	 * @param location	 Location of method.
	 * @param name  	 Name of method.
	 * @param arguments  List of all method arguments.
	 */
	public VirtualCallExpr(int line, Expr location, String name,
			List<Expr> arguments) {
		this(line, name, arguments);
		this.location = location;
	}

	public boolean isExternal() {
		return (location != null);
	}

	public Expr getLocation() {
		return location;
	}
	
	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
