package ast;

/**
 * Abstract base class for variable reference AST nodes.
 */
public abstract class LocationExpr extends Expr {
	/**
	 * Constructs a new variable reference node. Used by subclasses.
	 * 
	 * @param line  Line number of reference.
	 */
	protected LocationExpr(int line) {
		super(line);
	}
}
