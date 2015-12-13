package type_table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ast.Formal;
import ast.ICClass;
import ast.Method;
import ast.PrimitiveType;
import ast.UserType;
import ic.DataType;
import ic.LiteralType;

/**
 * Data structure representing a Type Table
 */
public class TypeTable {
	private String id;
	private int idCounter;
	
	/** Maps types to identifiers **/
	private Map<Type, Integer> values;

	/** Primitive symbol types **/
	private Type intType;
	private Type boolType;
	private Type nullType;
	private Type stringType;
	private Type voidType;

	/** Maps element types to array types. **/
	private Map<Type,ArrayType> uniqueArrayTypes;

	/** Maps class names to class types. **/
	private Map<String,ClassType> uniqueClassTypes;

	/** Maps method names to method types. **/
	private Map<String,MethodType> uniqueMethodTypes;

	/**
	 * Constructs a new type type. 
	 * 
	 * @param tableId name of table
	 */
	public TypeTable(String tableId) {
		this.id = tableId;
		this.idCounter = 0;
		this.uniqueArrayTypes = new HashMap<Type, ArrayType>();
		this.uniqueClassTypes = new HashMap<String,ClassType>();
		this.uniqueMethodTypes = new HashMap<String,MethodType>();

		this.values = new HashMap<Type, Integer>();
	}

	/**
	 * Converts ast.Type to type_table.Type.
	 * 
	 * @param typeNode  An ast.Type node to convert.
	 * @return 		    The corresponding type_table.Type type.
	 */
	public Type getTypeFromASTTypeNode(ast.Type typeNode) {
		if (typeNode instanceof PrimitiveType) {
			PrimitiveType primitiveType = (PrimitiveType) typeNode;
			Type primitive = getPrimitiveType(typeNode.getName());
			if (primitiveType.getDimension() == 0) 
				return primitive;
			else
				return getArrayFromType(primitive, primitiveType.getDimension());
		}
		else {
			UserType userType = (UserType)typeNode;
			Type classType = uniqueClassTypes.get(userType.getName());
			if (userType.getDimension() == 0)
				return classType;
			else 
				return getArrayFromType(classType, userType.getDimension());
		}
	}

	/**
	 * Initializes primitive types in the table.
	 */
	public void addPrimitiveTypes() {
		this.intType = new IntType();
		this.boolType = new BoolType();
		this.nullType = new NullType();
		this.stringType = new StringType();
		this.voidType = new VoidType();
		
		values.put(intType, 1);
		values.put(boolType, 2);
		values.put(nullType, 3);
		values.put(stringType, 4);
		values.put(voidType, 5);
		this.idCounter = 6;
	}

	/**
	 * Adds all of the array types concluded from typeNode to this table.
	 * 
	 * @param typeNode  An ast.Type node
	 */
	public void addArrayType(ast.Type typeNode) {
		Type currArrType;
		if (typeNode instanceof PrimitiveType) 
			currArrType = getPrimitiveType(typeNode.getName()); 
		else
			currArrType = uniqueClassTypes.get(typeNode.getName());

		for (int i = 0; i < typeNode.getDimension(); i++) 
			currArrType = addAndReturnArraySingleType(currArrType);
	}

	/**
	 * Adds a new class type based on an ICClass node.
	 * 
	 * @param classAST  An ICClass node to add.
	 * @return 			True if the class extends an existing class, otherwise returns false.
	 */
	public Boolean addClassType(ICClass classAST) {
		if (uniqueClassTypes.containsKey(classAST.getName()))
			return true;
		
		ClassType superClassType = null;
		if (classAST.hasSuperClass()) {
			if (!uniqueClassTypes.containsKey(classAST.getSuperClassName()))
				return false;
			superClassType = uniqueClassTypes.get(classAST.getSuperClassName());
		}
		
		ClassType classType = new ClassType(classAST.getName(), superClassType);
		uniqueClassTypes.put(classAST.getName(), classType);
		values.put(classType, idCounter);
		idCounter++;

		return true;
	}

	/**
	 * Gets the class type named className.
	 * 
	 * @param className  Class type name.
	 * @return 			 Class type named className.
	 */
	public ClassType getClassType(String className) {
		return uniqueClassTypes.get(className);
	}

	/**
	 * Adds a new method type based on a Method node.
	 * 
	 * @param method  A Method node to add.
	 */
	public void addMethodType(Method method) {
		MethodType methodType = generateMethodType(method);
		if (uniqueMethodTypes.containsKey(methodType.toString()))
			return;
		
		uniqueMethodTypes.put(methodType.toString(), methodType);
		values.put(methodType, idCounter);
		idCounter++;
	}

	/**
	 * Gets the method type corresponding to the specified method node.
	 * 
	 * @param method  A Method node.
	 * @return 		  Method type corresponding to method node.
	 */
	public MethodType getMethodType(Method method) {
		MethodType methodType = generateMethodType(method);
		return uniqueMethodTypes.get(methodType.toString());
	}

	/**
	 * Generates a method type based on a Method node.
	 * 
	 * @param method  A Method node.
	 * @return		  Method type corresponding to method node.
	 */
	private MethodType generateMethodType(Method method) {
		Type[] params = new Type[method.getFormals().size()];
		List<Formal> formals = method.getFormals(); 
		for (int i = 0; i < params.length; i++)
			params[i] = getTypeFromASTTypeNode(formals.get(i).getType());
		MethodType methodType = new MethodType(params, getTypeFromASTTypeNode(method.getType()));
		return methodType;
	}
	
	/**
	 * Gets the return type of the specified method type.
	 * @param type  A Method node.
	 * @return 		Return type of the method type.
	 */
	public Type getReturnTypeFromMethodType(Type type) {
		MethodType methodType = (MethodType)type;
		return methodType.getReturnType();
	}

	/**
	 * Gets the primitive type named dataTypeName.
	 * 
	 * @param dataTypeName Primitive type name.
	 * @return 			   Primitive type named dataTypeName, or null if not found.
	 */
	public Type getPrimitiveType(String dataTypeName) {
		if (dataTypeName == DataType.INT.getDescription())
			return intType;
		if (dataTypeName == DataType.STRING.getDescription())
			return stringType;
		if (dataTypeName == DataType.VOID.getDescription())
			return voidType;
		if (dataTypeName == DataType.BOOLEAN.getDescription())
			return boolType;

		return null;
	}
	
	/**
	 * Gets the literal type named literalTypeName.
	 * 
	 * @param literalTypeName  Literal type name.
	 * @return 			   	   Literal type named literalTypeName, or null if not found.
	 */
	public Type getLiteralType(String literalTypeName) {
		if (literalTypeName == LiteralType.INTEGER.getDescription())
			return intType;
		if (literalTypeName == LiteralType.STRING.getDescription())
			return stringType;
		if ((literalTypeName == LiteralType.TRUE.getDescription()) || (literalTypeName == LiteralType.FALSE.getDescription()))
			return boolType;
		if (literalTypeName == LiteralType.NULL.getDescription())
			return nullType;

		return null;

	}

	/**
	 * Gets the type of the specified array type.
	 * 
	 * @param type  	 Array type.
	 * @param dimention  Dimensions of originalType 
	 * @return			 Type of the specified array type
	 */
	public ArrayType getArrayFromType(Type type, int dimention) {
		Type currArrType = type;
		for (int i = 0; i < dimention; i++) 
			currArrType = uniqueArrayTypes.get(currArrType);

		return (ArrayType) currArrType;
	}

	/**
	 * Gets the element type of the specified array type.
	 * 
	 * @param type  Array type.
	 * @return		Element Type of the specified array type
	 */
	public Type getTypeFromArray(Type type) {
		ArrayType arrayType = (ArrayType)type;
		return arrayType.getElemType();
	}

	/**
	 * Adds a new array type with the specified element type to this table.
	 * 
	 * @param elemType Element type.
	 * @return		   The new created array type.
	 */
	private ArrayType addAndReturnArraySingleType(Type elemType) {
		if (uniqueArrayTypes.containsKey(elemType))
			return uniqueArrayTypes.get(elemType);

		ArrayType arrayType = new ArrayType(elemType);
		uniqueArrayTypes.put(elemType, arrayType);
		values.put(arrayType, idCounter);
		idCounter++;
		return arrayType;
	}
	
	/**
	 * Prints this type table to System.out
	 */
	public void printTable() {
		System.out.println("Type Table: " + id);

		// print the primitive symbol type
		System.out.println("    " + values.get(intType) + ": Primitive type: " + intType.toString());
		System.out.println("    " + values.get(boolType) + ": Primitive type: " + boolType.toString());
		System.out.println("    " + values.get(nullType) + ": Primitive type: " + nullType.toString());
		System.out.println("    " + values.get(stringType) + ": Primitive type: " + stringType.toString());
		System.out.println("    " + values.get(voidType) + ": Primitive type: " + voidType.toString());

		// sort the class symbol types
		List<Map.Entry<String,ClassType>> sorted_uniqueClassTypes =
				new ArrayList<Map.Entry<String,ClassType>>(uniqueClassTypes.entrySet());
		Collections.sort(sorted_uniqueClassTypes, new Comparator<Map.Entry<String,ClassType>>() {
			@Override
			public int compare( Map.Entry<String,ClassType> o1, Map.Entry<String,ClassType> o2 ) {
				return Integer.compare(values.get(o1.getValue()), values.get(o2.getValue()));
			}
		});
		
		// print the class symbol types
		for (Map.Entry<String,ClassType> entry : sorted_uniqueClassTypes) {
			System.out.print("    " + values.get(entry.getValue()) + ": Class: " + entry.getValue().toString());
			if (entry.getValue().hasSuperClass())
				System.out.print(", Superclass ID: " + values.get(uniqueClassTypes.get(
						entry.getValue().getSuperClassType().getClassName())));
			System.out.println();
		}

		// sort the array symbol types
		List<Map.Entry<Type,ArrayType>> sorted_uniqueArrayTypes =
				new ArrayList<Map.Entry<Type,ArrayType>>( uniqueArrayTypes.entrySet() );
		Collections.sort(sorted_uniqueArrayTypes, new Comparator<Map.Entry<Type,ArrayType>>() {
			@Override
			public int compare( Map.Entry<Type,ArrayType> o1, Map.Entry<Type,ArrayType> o2 ) {
				return Integer.compare(values.get(o1.getValue()), values.get(o2.getValue()));
			}
		});

		// print the array symbol types
		for (Map.Entry<Type,ArrayType> entry : sorted_uniqueArrayTypes) 
			System.out.println("    " + values.get(entry.getValue()) + ": Array type: " + entry.getValue().toString());

		// sort the method symbol types
		List<Map.Entry<String,MethodType>> sorted_uniqueMethodTypes =
				new ArrayList<Map.Entry<String,MethodType>>( uniqueMethodTypes.entrySet() );
		Collections.sort(sorted_uniqueMethodTypes, new Comparator<Map.Entry<String,MethodType>>() {
			public int compare( Map.Entry<String,MethodType> o1, Map.Entry<String,MethodType> o2 ) {
				return Integer.compare(values.get(o1.getValue()), values.get(o2.getValue()));
			}
		});

		// print the method symbol types
		for (Map.Entry<String,MethodType> entry : sorted_uniqueMethodTypes) 
			System.out.println("    " + values.get(entry.getValue()) + ": Method type: {" + entry.getValue().toString() + "}");
	}

}
