package ast;

import java.util.List;

/**
 * Statements block AST node.
 */
public class StmtBlock extends Stmt {

	private List<Stmt> statements;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new statements block node.
	 * 
	 * @param line
	 *            Line number where block begins.
	 * @param statements
	 *            List of all statements in block.
	 */
	public StmtBlock(int line, List<Stmt> statements) {
		super(line);
		this.statements = statements;
	}

	public List<Stmt> getStatements() {
		return statements;
	}

}
