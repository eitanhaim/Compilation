package ast;

import java.util.List;

/**
 * An AST node for static method.
 */
public class StaticMethod extends Method {
	/**
	 * Constructs a new static method node.
	 * 
	 * @param type        Data type returned by method.
	 * @param name        Name of method.
	 * @param formals     List of method parameters.
	 * @param statements  List of method statements.
	 */
	public StaticMethod(Type type, String name, List<Formal> formals,
			List<Stmt> statements) {
		super(type, name, formals, statements);
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
