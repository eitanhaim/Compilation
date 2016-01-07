package ast;

import symbol_table.*;

/**
 * Abstract AST node base class.
 * 
 */
public abstract class ASTNode {

	private int line;
	private SymbolTable symbolsTable;
	
	private type_table.Type entryType;
	private symbol_table.SymbolEntry symbolEntry;
	
	/**
	 * Double dispatch method, to allow a visitor to visit a specific subclass.
	 * 
	 * @param visitor
	 *            The visitor.
	 * @return A value propagated by the visitor.
	 */
	public abstract Object accept(Visitor visitor);

	/**
	 * Constructs an AST node corresponding to a line number in the original
	 * code. Used by subclasses.
	 * 
	 * @param line
	 *            The line number.
	 */
	protected ASTNode(int line) {
		this.symbolsTable = null;
		this.line = line;
		this.symbolEntry = null;
	}

	public int getLine() {
		return line;
	}
	
	public SymbolTable getSymbolsTable() {
		return symbolsTable;
	}
	
	public void setSymbolsTable(SymbolTable table) {
		this.symbolsTable = table;
	}
	
	public type_table.Type getEntryType() {
		return (this.symbolEntry == null) ? entryType : this.symbolEntry.getType();
	}

	public void setEntryType(type_table.Type entryType) {
		this.entryType = entryType;
	}

	public symbol_table.SymbolEntry getSymbolEntry() {
		return symbolEntry;
	}

	public void setSymbolEntry(symbol_table.SymbolEntry symbolEntry) {
		this.symbolEntry = symbolEntry;
	}
	
	
}
