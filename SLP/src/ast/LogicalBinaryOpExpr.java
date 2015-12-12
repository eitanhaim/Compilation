package ast;

import ic.BinaryOperator;

/**
 * An AST node for Logical binary operation AST node.
 */
public class LogicalBinaryOpExpr extends BinaryOpExpr {
	/**
	 * Constructs a new logical binary operation node.
	 * 
	 * @param firstOperand   The first operand.
	 * @param secondOperand  The second operand.
	 * @param operator       The operator.
	 */
	public LogicalBinaryOpExpr(Expr firstOperand, Expr secondOperand, 
			BinaryOperator operator) {
		super(firstOperand, secondOperand, operator);
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) throws Exception {
		return visitor.visit(this, context);
	}
}
