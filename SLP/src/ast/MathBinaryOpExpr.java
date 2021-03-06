package ast;

import ic.BinaryOperator;

/**
 * An AST node for a mathematical binary operation.
 */
public class MathBinaryOpExpr extends BinaryOpExpr {
	/**
	 * Constructs a new mathematical binary operation node.
	 * 
	 * @param firstOperand   The first operand.
	 * @param secondOperand  The second operand.
	 * @param operator       The operator.
	 */
	public MathBinaryOpExpr(Expr firstOperand, Expr secondOperand, 
			BinaryOperator operator) {
		super(firstOperand, secondOperand, operator);
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
