package ast;

/**
 * Assignment statement AST node.
 * 
 */
public class AssignStmt extends Stmt {

	private LocationExpr variable;

	private Expr assignment;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new assignment statement node.
	 * 
	 * @param variable
	 *            Variable to assign a value to.
	 * @param assignment
	 *            Value to assign.
	 */
	public AssignStmt(LocationExpr variable, Expr assignment) {
		super(variable.getLine());
		this.variable = variable;
		this.assignment = assignment;
	}

	public LocationExpr getVariable() {
		return variable;
	}

	public Expr getAssignment() {
		return assignment;
	}

}
