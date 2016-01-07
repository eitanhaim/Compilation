package ast;

import ic.BinaryOps;

/**
 * Logical binary operation AST node.
 */
public class LogicalBinaryOpExpr extends BinaryOpExpr {

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new logical binary operation node.
	 * 
	 * @param operand1
	 *            The first operand.
	 * @param operator
	 *            The operator.
	 * @param operand2
	 *            The second operand.
	 */
	public LogicalBinaryOpExpr(Expr operand1, Expr operand2, 
			BinaryOps operator) {
		super(operand1, operand2, operator);
	}

}
