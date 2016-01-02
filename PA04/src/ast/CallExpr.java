package ast;

import java.util.List;

/**
 * Abstract base class for method call AST nodes.
 */
public abstract class CallExpr extends Expr {

	private String name;
	private List<Expr> arguments;
	private type_table.Type methodType;
	/**
	 * Constructs a new method call node. Used by subclasses.
	 * 
	 * @param line
	 *            Line number of call.
	 * @param name
	 *            Name of method.
	 * @param arguments
	 *            List of all method arguments.
	 */
	protected CallExpr(int line, String name, List<Expr> arguments) {
		super(line);
		this.name = name;
		this.arguments = arguments;
	}

	public String getName() {
		return name;
	}

	public List<Expr> getArguments() {
		return arguments;
	}

	public type_table.Type getMethodType() {
		return methodType;
	}

	public void setMethodType(type_table.Type methodTtype) {
		this.methodType = methodTtype;
	}

}
