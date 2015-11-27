package ic;

/**
 * An enumeration containing all of the primitive data types in the IC language.
 */
public enum DataType {
	INT(0, "int"), 
	BOOLEAN(false, "boolean"), 
	STRING(null, "string"), 
	VOID(null, "void");
	
	private Object value;
	private String description;

	private DataType(Object value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Returns the default value of the data type.
	 * 
	 * @return The value.
	 */
	public Object getDefaultValue() {
		return value;
	}

	/**
	 * Returns a description of the data type.
	 * 
	 * @return The description.
	 */
	public String getDescription() {
		return description;
	}
}
