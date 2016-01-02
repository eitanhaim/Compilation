package ast;

/**
 * If statement AST node.
 */
public class IfStmt extends Stmt {

	private Expr condition;

	private Stmt operation;

	private Stmt elseOperation = null;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs an If statement node.
	 * 
	 * @param condition
	 *            Condition of the If statement.
	 * @param operation
	 *            Operation to perform if condition is true.
	 * @param elseOperation
	 *            Operation to perform if condition is false.
	 */
	public IfStmt(Expr condition, Stmt operation, Stmt elseOperation) {
		this(condition, operation);
		this.elseOperation = elseOperation;
	}

	/**
	 * Constructs an If statement node, without an Else operation.
	 * 
	 * @param condition
	 *            Condition of the If statement.
	 * @param operation
	 *            Operation to perform if condition is true.
	 */
	public IfStmt(Expr condition, Stmt operation) {
		super(condition.getLine());
		this.condition = condition;
		this.operation = operation;
	}

	public Expr getCondition() {
		return condition;
	}

	public Stmt getOperation() {
		return operation;
	}

	public boolean hasElse() {
		return (elseOperation != null);
	}

	public Stmt getElseOperation() {
		return elseOperation;
	}

}
