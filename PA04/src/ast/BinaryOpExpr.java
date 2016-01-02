package ast;

import ic.BinaryOps;

/**
 * Abstract base class for binary operation AST nodes.
 */
public abstract class BinaryOpExpr extends Expr {

	private Expr operand1;

	private BinaryOps operator;

	private Expr operand2;

	/**
	 * Constructs a new binary operation node. Used by subclasses.
	 * 
	 * @param operand1
	 *            The first operand.
	 * @param operator
	 *            The operator.
	 * @param operand2
	 *            The second operand.
	 */
	protected BinaryOpExpr(Expr operand1, Expr operand2, 
			BinaryOps operator) {
		super(operand1.getLine());
		this.operand1 = operand1;
		this.operator = operator;
		this.operand2 = operand2;
	}

	public BinaryOps getOperator() {
		return operator;
	}

	public Expr getFirstOperand() {
		return operand1;
	}

	public Expr getSecondOperand() {
		return operand2;
	}

}
