package ast;

/**
 * An AST node for a while statement.
 */
public class WhileStmt extends Stmt {
	private Expr cond;
	private Stmt stmt;

	/**
	 * Constructs a While statement node.
	 * 
	 * @param cond  Condition of the while statement.
	 * @param stmt  Statement to be executed while condition is true.
	 */
	public WhileStmt(Expr cond, Stmt stmt) {
		super(cond.getLine());
		this.cond = cond;
		this.stmt = stmt;
	}
	
	public Expr getCond() {
		return cond;
	}

	public Stmt getStmt() {
		return stmt;
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
