package ast;

import ic.DataType;

/**
 * An AST node for a primitive data type.
 */
public class PrimitiveType extends Type {
	private DataType type;

	/**
	 * Constructs a new primitive data type node.
	 * 
	 * @param line  Line number of type declaration.
	 * @param type  Specific primitive data type.
	 */
	public PrimitiveType(int line, DataType type) {
		super(line);
		this.type = type;
	}

	public String getName() {
		return type.getDescription();
	}
	
	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
}