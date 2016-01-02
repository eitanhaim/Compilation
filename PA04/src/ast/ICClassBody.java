package ast;

import java.util.ArrayList;
import java.util.List;

public class ICClassBody {
	public List<Method> methods;
	public List<Field> fields;
	
	public ICClassBody() 
	{
		this.methods = new ArrayList<Method>(); 
		this.fields = new ArrayList<Field>();
	}

}
