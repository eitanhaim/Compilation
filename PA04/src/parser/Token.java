package parser;
import java_cup.runtime.Symbol;

public class Token extends Symbol {
	private int id, line, column;
	private Object value;
	
	public Token(int id, Object value, int line, int column) {
		super(id, line, column, value);
		this.id=id;
		this.value=value;
		this.line=line;
		this.column = column;
	}
	
	public int getId() {return id;};
	public Object getValue() {return value;};
	public int getLine() {return line;};
	public int getColumn() {return column;};

	public String getTag() {
		switch (sym) {
		
			case parser.sym.ID:
				return "ID";
			case parser.sym.CLASS_ID:
				return "CLASS_ID";
			case parser.sym.INTEGER_LITERAL:
				return "INTEGER";
			case parser.sym.STRING_LITERAL:
				return "STRING";
				
		}
		
		return value.toString();
	}
}