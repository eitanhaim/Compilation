package ast;

import ic.UnaryOperator;

/**
 * An AST node for a logical unary operation.
 */
public class LogicalUnaryOpExpr extends UnaryOpExpr {
	/**
	 * Constructs a new logical unary operation node.
	 * 
	 * @param operator  The operator.
	 * @param operand   The operand.
	 */
	public LogicalUnaryOpExpr(UnaryOperator operator, Expr operand) {
		super(operator, operand);
	}
	
	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
