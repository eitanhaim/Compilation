package symbol_table;

import type_table.Type;
public class SymbolEntry {
	
	private String id;
	private Type type;
	private IDSymbolKinds kind;
	private String globalId;
	
	public SymbolEntry(String id, Type type, IDSymbolKinds kind) {
		this.id =id;
	    this.type = type;
	    this.kind = kind;
	}
	
	public String getId() {
		return id;
	}

	public Type getType() {
		return type;
	}
	
	public IDSymbolKinds getKind() {
		return kind;
	}
	
	@Override
	public String toString() {
		if (kind == IDSymbolKinds.CLASS)
			return kind.toString() + ": " + id;
		if (kind.isMethodKind())
			return kind.toString() + ": " + id + " {" + type.toString() + "}";
		
		return kind.toString() + ": " + type.toString() + " " + id;
	}

	public String getGlobalId() {
		return globalId;
	}

	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}
	
	
}
