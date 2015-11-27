package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * An AST node for a library method declaration.
 */
public class LibraryMethod extends Method {
	/**
	 * Constructs a new library method declaration node.
	 * 
	 * @param type	   Data type returned by method.
	 * @param name     Name of method.
	 * @param formals  List of method parameters.
	 */
	public LibraryMethod(Type type, String name, List<Formal> formals) {
		super(type, name, formals, new ArrayList<Stmt>());
	}
	
	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
}