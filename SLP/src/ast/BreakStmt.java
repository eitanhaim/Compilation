package ast;

/**
 * An AST node for a break statement.
 */
public class BreakStmt extends Stmt {
	/**
	 * Constructs a break statement node.
	 * 
	 * @param line  Line number of break statement.
	 */
	public BreakStmt(int line) {
		super(line);
	}
	
	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
