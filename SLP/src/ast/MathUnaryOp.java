package ast;

import ic.UnaryOperator;

/**
 * An AST node for a mathematical unary operation.
 */
public class MathUnaryOp extends UnaryOp {
	/**
	 * Constructs a new mathematical unary operation node.
	 * 
	 * @param operator  The operator.
	 * @param operand   The operand.
	 */
	public MathUnaryOp(UnaryOperator operator, Expr operand) {
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
