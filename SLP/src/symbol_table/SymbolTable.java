package symbol_table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data structure representing symbol table tree node
 */
public class SymbolTable {
	  private String id;
	  private SymbolTableType tableType;
	  
	  /** A map representing the entries in the symbol table. **/
	  private Map<String,SymbolEntry> entries; 
	  
	  /** Fields for accessing the parent or the children of the symbol table respectively. **/
	  private SymbolTable parentSymbolTable;
	  private Map<String, SymbolTable> children;
	  
	  /** Sorted version of entries and children used from printing the whole table. */
	  private List<SymbolEntry> sorted_entries; 
	  private List<SymbolTable> sorted_children; 
	  
	  /** Counts the entry kinds **/
	  private Map<SymbolKind, Integer> entryKindsCounter;
	  
	  /**
	   * Constructs a new symbol table.
	   * 
	   * @param id 		   Symbol table name.
	   * @param tableType  Symbol table type.
	   */
	  public SymbolTable(String id, SymbolTableType tableType) {
	    this.id = id;
	    this.tableType = tableType;
	    
	    entries = new HashMap<String,SymbolEntry>();
	    children = new HashMap<String, SymbolTable>();
	    sorted_entries = new ArrayList<SymbolEntry>();
	    sorted_children = new ArrayList<SymbolTable>();
	    entryKindsCounter = generateKindsCounter();
	    parentSymbolTable = null;
	  }
	  
	  private Map<SymbolKind, Integer> generateKindsCounter() {
		  Map<SymbolKind, Integer> entryKindsCounter = new HashMap<SymbolKind, Integer>();
		  entryKindsCounter.put(SymbolKind.CLASS, 0);
		  entryKindsCounter.put(SymbolKind.FIELD, 0);
		  entryKindsCounter.put(SymbolKind.FORMAL, 0);
		  entryKindsCounter.put(SymbolKind.STATIC_METHOD, 0);
		  entryKindsCounter.put(SymbolKind.VIRTUAL_METHOD, 0);
		  entryKindsCounter.put(SymbolKind.VARIABLE, 0);
		  return entryKindsCounter;
	  }

	  public String getId() {
		return id;
	  }
	  
	  public Map<String,SymbolEntry> getEntries()
	  {
		  return this.entries;
	  }
	  
	  public SymbolTableType getTableType() {
		return tableType;
	  }
	  
	  public SymbolTable getParentSymbolTable() {
		  return parentSymbolTable;
	  }

	  public void setParentSymbolTable(SymbolTable parentSymbolTable) {
		  this.parentSymbolTable = parentSymbolTable;
	  }
	  
	  
	  /**
	   * Adds the specified key and symbol table to this table children.
	   * 
	   * @param key 		 Symbol table name.
	   * @param symbolTable  Symbol table to add.
	   */
	  public void addTableChild(String key, SymbolTable symbolTable) {
		  this.children.put(key, symbolTable);
		  this.sorted_children.add(symbolTable);
	  }
	  
	  /**
	   * Adds the specified key and symbol entry to this table entries.
	   * 
	   * @param key    Symbol entry name.
	   * @param entry  Symbol entry to add.
	   */
	  public void addEntry(String key, SymbolEntry entry) {
		  this.entryKindsCounter.put(entry.getKind(), entryKindsCounter.get(entry.getKind()) + 1);
		  entry.setGlobalId(entry.getKind().getShortRepr() + 
				  entryKindsCounter.get(entry.getKind()).toString() + "_" + entry.getId());
		  this.entries.put(key, entry);
		  this.sorted_entries.add(entry);
	  }
	  
	  /**
	   * Checks if this table has the specified key is in its entries.
	   * 
	   * @param key  Symbol entry name to look for.
	   * @return 	 True if the table has the specified key is in its entries, otherwise returns false.
	   */
	  public Boolean hasEntry(String key) {
		  return this.entries.containsKey(key);
	  }
	  
	  /**
	   * Gets the entry associated with the specified key.
	   * 
	   * @param key  Symbol entry name.
	   * @return 	 Symbol entry associated with the specified key.
	   */
	  public SymbolEntry getEntry(String key) {
		  return this.entries.get(key);
	  }
	  
	  /**
	   * Searches in the tree of this table for the specified class name.
	   * 
	   * @param className  The name of the class to look for.
	   * @return 		   Symbol table of the class if it in this scope sub-tree, otherwise returns null.
	   */
	  public SymbolTable getClassScope(String className) {
			if (id.equals(className)) 
				return this;
			
			for (SymbolTable subTable : children.values()) {
				SymbolTable table = subTable.getClassScope(className);
				if (table != null)
					return table;
			}
			
			return null;
		}
	  
	  /**
	   * Searches in the tree of this table for a child (or this) table with the specified id.
	   * 
	   * @param id  Name of symbol table to look for.
	   * @return 	The child symbol table or null if not found.
	   */
	  public SymbolTable findChildSymbolTable(String id) {
		  return findChildSymbolTableRecursive(this, id);
	  }
	  
	  /**
	   * Searches recursively in the tree rooted by root for a child (or this) table with the specified id
	   * 
	   * @param root  Root of table tree to search in.
	   * @param id	  Name of symbol table to look for.
	   * @return	  The child symbol table or null if not found,
	   */
	  private SymbolTable findChildSymbolTableRecursive(SymbolTable root, String id) {
		   if (root.children.containsKey(id))
			  return root.children.get(id);

		  for (String tableID : root.children.keySet()) {
			  SymbolTable result = findChildSymbolTableRecursive(
						root.children.get(tableID), id);
				  if (result != null)
					  return result;
		  }

		  return null;
		  
	  }
	  
	  /**
	   * Prints the symbol table to System.out
	   */
	  public void printTable() {
		  if (this.tableType != SymbolTableType.STATEMENT_BLOCK)
			  System.out.println(tableType.toString() + " Symbol Table: " + id);
		  else
			  System.out.println(tableType.toString() + " Symbol Table ( located in " + 
					  this.parentSymbolTable.toString() + " )");
		  
		  for (int i = 0; i < sorted_entries.size(); i++) 
			  System.out.println("    " +  sorted_entries.get(i).toString());
		  
		  if (sorted_children.size() > 0) {
			  if (this.tableType == SymbolTableType.CLASS)
				  moveClassChildrenToEnd();
			  System.out.print("Children tables: ");
			  Boolean isFirstChild = true;
			  for (int i = 0; i < sorted_children.size(); i++)  {
				  if (!isFirstChild)
					 System.out.print(", ");
				  else
					  isFirstChild = false;
				  System.out.print(sorted_children.get(i).toString()); 
			  }
			  System.out.println();
		  }
		  
		  System.out.println();
		  for (int i = 0; i < sorted_children.size(); i++)
			  sorted_children.get(i).printTable();
	  }
	  
	  /**
	   * Moves the class children to the end of the list sorted_children.
	   */
	  private void moveClassChildrenToEnd() {
		  int classesCounter = 0;
		  for (int i = 0; i < sorted_children.size() - classesCounter; i++) {
			  if (sorted_children.get(i).tableType == SymbolTableType.CLASS) {
				  SymbolTable classSymbolTable = sorted_children.get(i);
				  sorted_children.remove(classSymbolTable);
				  sorted_children.add(classSymbolTable);
				  classesCounter++;
			  }
		  }
	  }
	  
	  @Override
	  public String toString() {
		  if (this.tableType != SymbolTableType.STATEMENT_BLOCK)
			  return id;
		  else
			  return "statement block in " + parentSymbolTable.toString();
	  }
}





