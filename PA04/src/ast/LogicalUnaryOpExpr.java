package ast;

import ic.UnaryOps;

/**
 * Logical unary operation AST node.
 */
public class LogicalUnaryOpExpr extends UnaryOpExpr {

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new logical unary operation node.
	 * 
	 * @param operator
	 *            The operator.
	 * @param operand
	 *            The operand.
	 */
	public LogicalUnaryOpExpr(UnaryOps operator, Expr operand) {
		super(operator, operand);
	}

}
