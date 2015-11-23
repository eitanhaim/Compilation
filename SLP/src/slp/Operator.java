package slp;

/** An enumeration containing all the operation types in the SLP language.
 */
public enum Operator {
	MINUS, PLUS, MULTIPLY, DIVIDE, LT, GT, LTE, GTE, LAND, LOR;
	
	/** Prints the operator in the same way it appears in the program.
	 */
	public String toString() {
		switch (this) {
		case MINUS: return "-";
		case PLUS: return "+";
		case MULTIPLY: return "*";
		case DIVIDE: return "/";
		case LT: return "<";
		case GT: return ">";
		case LTE: return "<=";
		case GTE: return ">=";
		case LAND: return "&&";
		case LOR: return "||";
		default: throw new RuntimeException("Unexpted value: " + this.name());
		}
	}
}