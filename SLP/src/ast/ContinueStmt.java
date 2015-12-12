package ast;

/**
 * An AST node for a continue statement.
 */
public class ContinueStmt extends Stmt {
	/**
	 * Constructs a continue statement node.
	 * 
	 * @param line  Line number of continue statement.
	 */
	public ContinueStmt(int line) {
		super(line);
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
