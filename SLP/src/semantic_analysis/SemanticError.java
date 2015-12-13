package semantic_analysis;

import java.io.IOException;

public class SemanticError extends IOException {
	private static final long serialVersionUID = 1L;
	
	public SemanticError(int line, String msg) {
		super(msg + " at line " + line);
	}
}

