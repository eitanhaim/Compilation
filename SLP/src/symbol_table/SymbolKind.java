package symbol_table;

/**
 * An enumeration containing all of the symbol kind of ID symbols.
 */
public enum SymbolKind {
	CLASS("Class"),
	STATIC_METHOD("Static method"),
	VIRTUAL_METHOD("Virtual method"),
	VARIABLE("Local variable"),
	FORMAL("Parameter"),
	FIELD("Field");
	
	private final String description;       

	private SymbolKind(String description) {
			this.description = description;
	}
	
	public Boolean isMethodKind() {
		return ((this == STATIC_METHOD) || (this == VIRTUAL_METHOD));
	}
	
	public String toString(){
		    return description;
	}
}
	