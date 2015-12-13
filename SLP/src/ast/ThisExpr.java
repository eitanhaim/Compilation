package ast;

/**
 * An AST node for this expression AST node.
 */
public class ThisExpr extends Expr {
	/**
	 * Constructs a this expression node.
	 * 
	 * @param line  Line number of this expression.
	 */
	public ThisExpr(int line) {
		super(line);
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
