package symbol_table;

import type_table.Type;

/**
 * Data structure representing a symbol entry in a symbol table.
 */
public class SymbolEntry {
	private String id;
	private Type type;
	private SymbolKind kind;
	  
	/**
	 * Constructs a new symbol entry.
	 * 
	 * @param id 	Symbol name.
	 * @param type 	Symbol type.
	 * @param kind 	Symbol kind.
	 */
	public SymbolEntry(String id, Type type, SymbolKind kind) {
		this.id = id;
	    this.type = type;
	    this.kind = kind;
	}

	public String getId() {
		return id;
	}

	public Type getType() {
		return type;
	}
	
	public SymbolKind getKind() {
		return kind;
	}
	
	@Override
	public String toString() {
		if (kind == SymbolKind.CLASS)
			return kind.toString() + ": " + id;
		if (kind.isMethodKind())
			return kind.toString() + ": " + id + " {" + type.toString() + "}";
		
		return kind.toString() + ": " + type.toString() + " " + id;
	}
}
