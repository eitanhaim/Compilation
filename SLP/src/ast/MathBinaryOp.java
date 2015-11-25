package ast;

import ic.BinaryOperator;

/**
 * An AST node for a mathematical binary operation.
 */
public class MathBinaryOp extends BinaryOpExpr {
	/**
	 * Constructs a new mathematical binary operation node.
	 * 
	 * @param firstOperand   The first operand.
	 * @param secondOperand  The second operand.
	 * @param operator       The operator.
	 */
	public MathBinaryOp(Expr firstOperand, Expr secondOperand, 
			BinaryOperator operator) {
		super(firstOperand, secondOperand, operator);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		return visitor.visit(this, context);
	}
}
