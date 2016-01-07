package ast;

/**
 * Abstract base class for statement AST nodes.
 */
public abstract class Stmt extends ASTNode {

	/**
	 * Constructs a new statement node. Used by subclasses.
	 * 
	 * @param line
	 *            Line number of statement.
	 */
	protected Stmt(int line) {
		super(line);
	}

}
