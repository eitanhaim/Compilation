package ast;

import java.util.List;

/**
 * Root AST node for an IC program.
 */
public class Program extends ASTNode {
	private List<ICClass> classes; 

	/**
	 * Constructs a new program node.
	 * 
	 * @param classes  List of all classes declared in the program.
	 */
	public Program(List<ICClass> classes) {
		super(0);
		this.classes = classes;
	}

	public List<ICClass> getClasses() {
		return classes;
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
