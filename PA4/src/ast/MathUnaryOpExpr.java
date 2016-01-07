package ast;

import ic.UnaryOps;

/**
 * Mathematical unary operation AST node.
 */
public class MathUnaryOpExpr extends UnaryOpExpr {

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new mathematical unary operation node.
	 * 
	 * @param operator
	 *            The operator.
	 * @param operand
	 *            The operand.
	 */
	public MathUnaryOpExpr(UnaryOps operator, Expr operand) {
		super(operator, operand);
	}

}
