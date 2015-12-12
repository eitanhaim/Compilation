package semantic_analysis;

public class SemanticError extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public SemanticError(String message) {
		super(message);
	}
}
