package ic;

/**
 * An enumeration containing all of the unary operation types in the IC language.
 */
public enum UnaryOperator {
	UMINUS("-", "unary subtraction"), 
	LNEG("!", "logical negation");

	private String operator;
	private String description;

	private UnaryOperator(String operator, String description) {
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