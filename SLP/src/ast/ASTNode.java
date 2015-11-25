package ast;

/**
 * Abstract base class for AST node base.
 */
public abstract class ASTNode {
	private int line;
	
	/**
	 * Constructs an AST node corresponding to a line number in the original code. Used by subclasses.
	 * 
	 * @param line	The line number.
	 */
	protected ASTNode(int line) {
		this.line = line;
	}
	
	public int getLine() {
		return line;
	}
	
	/** 
	 * Accepts a visitor object as part of the visitor pattern.
	 * 
	 * @param visitor  A visitor.
	 */
	public abstract void accept(Visitor visitor);
	
	/** 
	 * Accepts a propagating visitor parameterized by two types.
	 * 
	 * @param <DownType>  The type of the object holding the context.
	 * @param <UpType>    The type of the result object.
	 * @param visitor 	  A propagating visitor.
	 * @param context 	  An object holding context information.
	 * @return 			  The result of visiting this node.
	 */
	public abstract <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context);
}
