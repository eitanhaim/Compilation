package IC.AST;

import java.util.ArrayList;
import java.util.List;

public class ClassLines {
	public List<Method> methods;
	public List<Field> fields;
	
	public ClassLines() 
	{
		this.methods = new ArrayList<Method>(); 
		this.fields = new ArrayList<Field>();
	}

}
