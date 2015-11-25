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
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		return visitor.visit(this, context);
	}
}
