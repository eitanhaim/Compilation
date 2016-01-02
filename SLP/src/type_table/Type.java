package type_table;

/**
 * Data structure representing a symbol type
 */
public abstract class Type {
	protected String name;
	
	/**
	 * Constructs a new symbol type.
	 * 
	 * @param name  Type name.
	 */
	public Type(String name) 
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Checks if this type is a subtype of type t.
	 * 
	 * @param t  A type.
	 * @return	 True if this type is a subtype of type t, otherwise returns false.
	 */
	public boolean subtypeOf(Type t)
	{
		if (this.equals(t))
			return true;
		if ((this.isNullType()) && (t.isClassType()))
			return true;
		if (this.isClassType()) {
			ClassType classType = (ClassType)this;
			if (classType.hasSuperClass())
				return classType.getSuperClassType().subtypeOf(t);
		}
		return false;
	}

	/**
	 * Checks if this type is an instance of ClassType.
	 * 
	 * @return  True if this type is an instance of ClassType, otherwise returns false.
	 */
	public boolean isClassType() {
		return (this instanceof ClassType);
	}
	
	/**
	 * Checks if this type is an instance of MethodType.
	 * 
	 * @return  True if this type is an instance of MethodType, otherwise returns false.
	 */
	public boolean isMethodType() {
		return (this instanceof MethodType);
	}
	
	/**
	 * Checks if this type is an instance of ArrayType.
	 * 
	 * @return  True if this type is an instance of ArrayType, otherwise returns false.
	 */
	public boolean isArrayType() {
		return (this instanceof ArrayType);
	}
	
	/**
	 * Checks if this type is an instance of IntType.
	 * 
	 * @return  True if this type is an instance of IntType, otherwise returns false.
	 */
	public boolean isIntType() {
		return (this instanceof IntType);
	}
	
	/**
	 * Checks if this type is an instance of StringType.
	 * 
	 * @return  True if this type is an instance of StringType, otherwise returns false.
	 */
	public boolean isStringType() {
		return (this instanceof StringType);
	}
	
	/**
	 * Checks if this type is an instance of BoolType.
	 * 
	 * @return  True if this type is an instance of BoolType, otherwise returns false.
	 */
	public boolean isBoolType() {
		return (this instanceof BoolType);
	}
	
	/**
	 * Checks if this type is an instance of VoidType.
	 * 
	 * @return  True if this type is an instance of VoidType, otherwise returns false.
	 */
	public boolean isVoidType() {
		return (this instanceof VoidType);
	}
	
	/**
	 * Checks if this type is an instance of NullType.
	 * 
	 * @return  True if this type is an instance of NullType, otherwise returns false.
	 */
	public boolean isNullType() {
		return (this instanceof NullType);
	}
	
	public Type getReturnType() {
		if (!this.isMethodType())
			return null;
		MethodType thisMethodType = (MethodType)this;
		return thisMethodType.getReturnType();
	}
	
	/**
	 * Checks if we can assign a null to this type.
	 * 
	 * @return  True if this type is a null assignable, otherwise returns false.
	 */
	public boolean isNullAssignable() {
		if ((this.isClassType()) || (this.isArrayType()) || (this.isStringType()))
			return true;
		if (this instanceof MethodType) {
			MethodType methodType = (MethodType)this;
			return methodType.getReturnType().isNullAssignable();
		}
		return false;
	}
}

/**
 * Data structure representing an integer symbol type
 */
class IntType extends Type 
{
	public IntType()
	{
		super("IntType");
	}
	
	@Override
	public String toString() {
		return "int";
	}
}

/**
 * Data structure representing a boolean symbol type
 */
class BoolType extends Type 
{
	public BoolType()
	{
		super("BoolType");
	}
	
	@Override
	public String toString() {
		return "boolean";
	}
}


/**
 * Data structure representing a string symbol type
 */
class StringType extends Type 
{
	public StringType()
	{
		super("StringType");
	}
	
	@Override
	public String toString() {
		return "string";
	}
}

/**
 * Data structure representing a null type
 */
class NullType extends Type 
{
	public NullType()
	{
		super("NullType");
	}
	
	@Override
	public String toString() {
		return "null";
	}
}

/**
 * Data structure representing a void symbol type
 */
class VoidType extends Type 
{
	public VoidType()
	{
		super("VoidType");
	}
	
	@Override
	public String toString() {
		return "void";
	}
}

/**
 * Data structure representing an array symbol type
 */
class ArrayType extends Type 
{
	private Type elemType;
	public ArrayType(Type elemType)
	{
		super("ArrayType");
		this.elemType = elemType;
	}
	
	public Type getElemType() {
		return elemType;
	}
	
	@Override
	public String toString() {
		return elemType.toString() + "[]";
	}
}

/**
 * Data structure representing a method symbol type
 */
class MethodType extends Type 
{  
	private Type[] paramTypes;
	private Type returnType;

	public MethodType(Type[] paramTypes,Type returnType)
	{
		super("MethodType");
		this.paramTypes=paramTypes;
		this.returnType=returnType;
	}
	
	public Type getReturnType() {
		return returnType;
	}
	
	public Type[] getParamTypes() {
		return paramTypes;
	}
	
	@Override
	public String toString() {
		String paramTypesStr = "";
		for (int i = 0; i < paramTypes.length; i++) {
			if (i == 0)
				paramTypesStr += paramTypes[i].toString();
			else
				paramTypesStr += ", " + paramTypes[i].toString();
		}
		return paramTypesStr + " -> " + returnType.toString();
	}
}

/**
 * Data structure representing a class symbol type
 */
class ClassType extends Type 
{   
	String className;
	ClassType superClassType;

	public ClassType(String clsName, ClassType superClassType)
	{
		super("ClassType");
		this.className = clsName;
		this.superClassType = superClassType;
	}
	
	public ClassType getSuperClassType() {
		return superClassType;
	}

	public Boolean hasSuperClass() {
		return (superClassType != null);
	}
	
	public String getClassName() {
		return this.className;
	}
	
	@Override
	public String toString() {
		return className;
	}
}
