package ast;

import java.util.List;

/**
 * An AST node for a virtual method.
 */
public class VirtualMethod extends Method {
	/**
	 * Constructs a new virtual method node.
	 * 
	 * @param type		  Data type returned by method.
	 * @param name		  Name of method.
	 * @param formals	  List of method parameters.
	 * @param statements  List of method's statements.
	 */
	public VirtualMethod(Type type, String name, List<Formal> formals,
			List<Stmt> statements) {
		super(type, name, formals, statements);
	}
	
	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) throws Exception {
		return visitor.visit(this, context);
	}
}
