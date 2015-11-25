package ast;

/**
 * Abstract base class for expression AST nodes.
 */
public abstract class Expr extends ASTNode {
	/**
	 * Constructs a new expression node. Used by subclasses.
	 * 
	 * @param line  Line number of expression.
	 */
	protected Expr(int line) {
		super(line);
	}
}