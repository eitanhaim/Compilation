package ast;

import ic.BinaryOperator;

/**
 * Abstract base class for binary operation AST nodes.
 */
public abstract class BinaryOpExpr extends Expr {
	private Expr firstOperand;
	private Expr secondOperand;
	private BinaryOperator operator;

	/**
	 * Constructs a new binary operation node. Used by subclasses. 
	 * 
	 * @param firstOperand   The first operand.
	 * @param secondOperand  The second operand.
	 * @param operator       The operator.
	 */
	protected BinaryOpExpr(Expr firstOperand, Expr secondOperand, 
			BinaryOperator operator) {
		super(firstOperand.getLine());
		this.firstOperand = firstOperand;
		this.secondOperand = secondOperand;
		this.operator = operator;
	}

	public Expr getFirstOperand() {
		return firstOperand;
	}

	public Expr getSecondOperand() {
		return secondOperand;
	}
	
	public BinaryOperator getOperator() {
		return operator;
	}
}
