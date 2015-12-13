package symbol_table;

/**
 * An enumeration containing all of the symbol table types in the IC language.
 */
public enum SymbolTableType {
	GLOBAL("Global"),
	CLASS("Class"),
	METHOD("Method"),
	STATEMENT_BLOCK("Statement Block");
	
	private final String description;       

	private SymbolTableType(String description) {
			this.description = description;
	}
	
	public String toString(){
	    return description;
	}
}
