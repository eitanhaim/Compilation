package ast;

/**
 * An AST node for an assignment statement.
 */
public class AssignStmt extends Stmt {
	private LocationExpr variable;;
	private Expr assignVal;

	/**
	 * Constructs a new assignment statement node.
	 * 
	 * @param variable   Variable to assign a value to.
	 * @param assignVal  Value to assign.
	 */
	public AssignStmt(LocationExpr variable, Expr assignVal) {
		super(variable.getLine());
		this.variable = variable;
		this.assignVal = assignVal;
	}

	public LocationExpr getVariable() {
		return variable;
	}

	public Expr getAssignVal() {
		return assignVal;
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
