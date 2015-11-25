package ast;

import java.util.List;

/**
 * An AST node for a block of statements.
 */
public class StmtBlock extends Stmt {
	private List<Stmt> statements;
	
	/**
	 * Constructs a new statement block node.
	 * 
	 * @param line  	  Line number where block begins.
	 * @param statements  List of all statements in block.
	 */
	public StmtBlock(int line, List<Stmt> statements) {
		super(line);
		this.statements = statements;
	}

	public List<Stmt> getStatements() {
		return statements;
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
