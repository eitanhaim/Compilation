package ast;

import ic.BinaryOperator;

/**
 * An AST node for Logical binary operation AST node.
 */
public class LogicalBinaryOp extends BinaryOpExpr {
	/**
	 * Constructs a new logical binary operation node. 
	 * 
	 * @param lhs  The left operand.
	 * @param op   The operator.
	 * @param rhs  The right operand.
	 */
	public LogicalBinaryOp(Expr lhs, Expr rhs, BinaryOperator op) {
		super(lhs, rhs, op);
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
