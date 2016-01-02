package symbol_table;

/**
 * An enumeration containing all of the symbol kind of ID symbols.
 */
public enum SymbolKind {
	CLASS("Class", "cls"),
	STATIC_METHOD("Static method", "static"),
	VIRTUAL_METHOD("Virtual method", "virtual"),
	VARIABLE("Local variable", "var"),
	FORMAL("Parameter", "param"),
	FIELD("Field", "field");
	
	private final String repr;       
	private final String shortRepr;       

	private SymbolKind(String repr, String shortRepr) {
		this.repr = repr;
		this.shortRepr = shortRepr;
	}
	
	public Boolean isMethodKind() {
		return ((this == STATIC_METHOD) || (this == VIRTUAL_METHOD));
	}
	
	public String getShortRepr() {
		return shortRepr;
	}
	
	public String toString(){
		    return repr;
	}
}
	