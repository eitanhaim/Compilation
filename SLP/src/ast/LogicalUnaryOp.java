package ast;

import ic.UnaryOperator;

/**
 * An AST node for a logical unary operation.
 */
public class LogicalUnaryOp extends UnaryOp {
	/**
	 * Constructs a new logical unary operation node.
	 * 
	 * @param operator  The operator.
	 * @param operand   The operand.
	 */
	public LogicalUnaryOp(UnaryOperator operator, Expr operand) {
		super(operator, operand);
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
