package ast;

import java.util.List;

/**
 * An AST node for a static method call.
 */
public class StaticCallExpr extends CallExpr {
	private String className;

	/**
	 * Constructs a new static method call node.
	 * 
	 * @param line 		 Line number of call.
	 * @param className  Name of method's class.
	 * @param name		 Name of method.
	 * @param arguments  List of all method arguments.
	 */
	public StaticCallExpr(int line, String className, String name,
			List<Expr> arguments) {
		super(line, name, arguments);
		this.className = className;
	}

	public String getClassName() {
		return className;
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
