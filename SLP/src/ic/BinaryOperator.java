package ic;

/**
 * An enumeration containing all of the binary operation types in the IC language.
 */
public enum BinaryOperator {

	PLUS("+", "addition"),
	MINUS("-", "subtraction"),
	MULTIPLY("*", "multiplication"),
	DIVIDE("/", "division"),
	MOD("%", "modulo"),
	LAND("&&", "logical and"),
	LOR("||", "logical or"),
	LT("<", "less than"),
	LTE("<=", "less than or equal to"),
	GT(">", "greater than"),
	GTE(">=", "greater than or equal to"),
	EQUAL("==", "equality"),
	NEQUAL("!=", "inequality");
	
	private String operator;
	private String description;

	private BinaryOperator(String operator, String description) {
		this.operator = operator;
		this.description = description;
	}

	/**
	 * Returns a string representation of the operator.
	 * 
	 * @return The string representation.
	 */
	public String getOperatorString() {
		return operator;
	}
	
	/**
	 * Returns a description of the operator.
	 * 
	 * @return The description.
	 */
	public String getDescription() {
		return description;
	}
}