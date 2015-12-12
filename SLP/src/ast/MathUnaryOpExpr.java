package ast;

import ic.UnaryOperator;

/**
 * An AST node for a mathematical unary operation.
 */
public class MathUnaryOpExpr extends UnaryOpExpr {
	/**
	 * Constructs a new mathematical unary operation node.
	 * 
	 * @param operator  The operator.
	 * @param operand   The operand.
	 */
	public MathUnaryOpExpr(UnaryOperator operator, Expr operand) {
		super(operator, operand);
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
