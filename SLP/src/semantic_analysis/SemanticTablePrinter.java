package semantic_analysis;

import ast.ASTNode;

public class SemanticTablePrinter {
	
	private String ICFilePath;
	
	/**
	 * Constructs a new pretty printer visitor.
	 * 
	 * @param ICFilePath
	 *            The path + name of the IC file being compiled.
	 */
	public SemanticTablePrinter(String ICFilePath) {
		this.ICFilePath = ICFilePath;
	}
	
	public String print(ASTNode textRoot) {
		StringBuffer output = new StringBuffer();
		SymbolTable table = textRoot.enclosingScope();
		
		output.append(table);
		
		return output.toString();
	}
}
