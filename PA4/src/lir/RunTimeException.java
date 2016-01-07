package lir;

public class RunTimeException extends RuntimeException {
	private static final long serialVersionUID = -7287486561773885176L;
	private int line;
	private int column;
	    
    public RunTimeException(String message, int line) {
    	super(message);
        this.line = line;
        this.column = -1;
    }
    
    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
