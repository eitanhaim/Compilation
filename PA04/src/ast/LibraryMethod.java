package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Library method declaration AST node.
 */
public class LibraryMethod extends Method {

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new library method declaration node.
	 * 
	 * @param type
	 *            Data type returned by method.
	 * @param name
	 *            Name of method.
	 * @param formals
	 *            List of method parameters.
	 */
	public LibraryMethod(Type type, String name, List<Formal> formals) {
		super(type, name, formals, new ArrayList<Stmt>());
	}
}