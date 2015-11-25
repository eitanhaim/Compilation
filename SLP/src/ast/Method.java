package ast;

import java.util.List;

/**
 * Abstract base class for method AST nodes.
 */
public abstract class Method extends ASTNode {
	protected Type type;
	protected String name;
	protected List<Formal> formals;
	protected List<Stmt> statements;

	/**
	 * Constructs a new method node. Used by subclasses.
	 * 
	 * @param type 		  Data type returned by method.
	 * @param name        Name of method.
	 * @param formals     List of method parameters.
	 * @param statements  List of method's statements.
	 */
	protected Method(Type type, String name, List<Formal> formals, 
			List<Stmt> statements) {
		super(type.getLine());
		this.type = type;
		this.name = name;
		this.formals = formals;
		this.statements = statements;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public List<Formal> getFormals() {
		return formals;
	}

	public List<Stmt> getStatements() {
		return statements;
	}
}