package semantic_analysis;

import java.util.Map.Entry;

import type_table.ArrayType;
import type_table.Type;
import type_table.TypeTable;

public class StatementBlockSymbolTable extends SymbolTable {

	public StatementBlockSymbolTable(String id) {
		super(id, Kind.BLOCK);
	}
	
	@Override
	public void AddUniqueTypes() {
		
		Type myType;
		
		if (entries.size() > 0) {
			for (Entry<String, SymbolTableRow> entry : entries.entrySet()) {
				if (entry.getValue().getKind() != Kind.RET_VAR
						&& entry.getValue().getKind() != Kind.THIS) {
					
					myType = entry.getValue().getType();
					
					if (myType instanceof ArrayType) {
						TypeTable.addArrayType((ArrayType)myType);
					}
				}
			}
		}
		
		if (getChildList().size() > 0) {
			for (SymbolTable child : getChildList()) {
				child.AddUniqueTypes();	
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		
		output.append("Statement Block Symbol Table ( located");
		output.append(" in " + getParentSymbolTable().getId() + " )\n");
		
		output.append(super.toString());
		
		if (getChildList().size() > 0) {
			output.append("\n");
			for (SymbolTable child : getChildList()) {
				output.append(child);	
			}
		}
		
		return output.toString();
	}

}
