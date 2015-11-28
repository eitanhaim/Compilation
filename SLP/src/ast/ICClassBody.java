package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * An utility class used to construct an icClass AST node.
 */
public class ICClassBody {
	private List<Field> fields;
	private List<Method> methods;
	
	/**
	 * Constructs a new class body.
	 * 
	 * @param fields   List of all fields in the class.
	 * @param methods  List of all methods in the class.
	 */
	public ICClassBody() {
		fields = new ArrayList<Field>();
		methods = new ArrayList<Method>();
	}
	
	public List<Field> getFields() {
		return fields;
	}

	public List<Method> getMethods() {
		return methods;
	}
	
	public void addFields(List<Field> newFields) {
		fields.addAll(newFields);
	}
	
	public void addMethod(Method method) {
		methods.add(method);
	}
}
