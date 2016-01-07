package ast;

/**
 * Continue statement AST node.
 */
public class ContinueStmt extends Stmt {

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a continue statement node.
	 * 
	 * @param line
	 *            Line number of continue statement.
	 */
	public ContinueStmt(int line) {
		super(line);
	}

}
