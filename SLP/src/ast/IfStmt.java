package ast;

/**
 * An AST node for an if statement.
 */
public class IfStmt extends Stmt {
	private Expr cond;
	private Stmt stmt;
	private Stmt elseStmt = null;

	/**
	 * Constructs an if statement node.
	 * 
	 * @param cond  	Condition of the if statement.
	 * @param stmt      Statement to be executed if cond is true.
	 * @param elseStmt  Statement to be executed if cond is false.
	 */
	public IfStmt(Expr cond, Stmt stmt, Stmt elseStmt) {
		this(cond, stmt);
		this.elseStmt = elseStmt;
	}

	/**
	 * Constructs an if statement node, without an else operation.
	 * 
	 * @param cond  Condition of the if statement.
	 * @param stmt  Statement to be executed if cond is true.
	 */
	public IfStmt(Expr cond, Stmt stmt) {
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

	public boolean hasElse() {
		return (elseStmt != null);
	}

	public Stmt getElseStmt() {
		return elseStmt;
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
