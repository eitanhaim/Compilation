package ast;

/**
 * An AST node for an assignment statement.
 */
public class AssigmStmt extends Stmt {
	private LocationExpr variable;;
	private Expr rhs;

	/**
	 * Constructs a new assignment statement node.
	 * 
	 * @param variable  Variable to assign a value to.
	 * @param rhs       Value to assign.
	 */
	public AssigmStmt(LocationExpr variable, Expr rhs) {
		super(variable.getLine());
		this.variable = variable;
		this.rhs = rhs;
	}

	public LocationExpr getVariable() {
		return variable;
	}

	public Expr getRhs() {
		return rhs;
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
