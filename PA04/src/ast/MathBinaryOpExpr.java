package ast;

import ic.BinaryOps;

/**
 * Mathematical binary operation AST node.
 */
public class MathBinaryOpExpr extends BinaryOpExpr {

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new mathematical binary operation node.
	 * 
	 * @param operand1
	 *            The first operand.
	 * @param operator
	 *            The operator.
	 * @param operand2
	 *            The second operand.
	 */
	public MathBinaryOpExpr(Expr operand1, BinaryOps operator, Expr operand2) {
		super(operand1, operator, operand2);
	}

}
