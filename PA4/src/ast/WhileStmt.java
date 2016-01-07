package ast;

/**
 * While statement AST node.
 */
public class WhileStmt extends Stmt {

	private Expr condition;

	private Stmt operation;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a While statement node.
	 * 
	 * @param condition
	 *            Condition of the While statement.
	 * @param operation
	 *            Operation to perform while condition is true.
	 */
	public WhileStmt(Expr condition, Stmt operation) {
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

}
