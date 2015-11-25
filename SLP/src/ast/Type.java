package ast;

/**
 * Abstract base class for data type AST nodes.
 */
public abstract class Type extends ASTNode {
	// The number of array dimensions in data type. 
	// E.g., int[][] -> dimension = 2 ; int -> dimension = 0.
	private int dimension = 0; 

	/**
	 * Constructs a new type node. Used by subclasses.
	 * 
	 * @param line  Line number of type declaration.
	 */
	protected Type(int line) {
		super(line);
	}

	public abstract String getName();

	public int getDimension() {
		return dimension;
	}

	public void incrementDimension() {
		dimension++;
	}
}