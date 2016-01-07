package ast;

/**
 * 'This' expression AST node.
 */
public class ThisExpr extends Expr {

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a 'this' expression node.
	 * 
	 * @param line
	 *            Line number of 'this' expression.
	 */
	public ThisExpr(int line) {
		super(line);
	}

}
