package ast;

/**
 * Break statement AST node.
 */
public class BreakStmt extends Stmt {

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a break statement node.
	 * 
	 * @param line
	 *            Line number of break statement.
	 */
	public BreakStmt(int line) {
		super(line);
	}

}
