
package ast;

import java.util.*;


public class SemanticAnalayzer implements Visitor{
	//private PriorityQueue<HashMap<String, Type>> myVars = new PriorityQueue<HashMap<String, Type>> (); //map variable -> type
	//private HashMap<String, PriorityQueue<Type>> myVars = new HashMap<String, PriorityQueue<Type>> ();
	private HashMap<String, Stack<String>> myVars = new HashMap<String, Stack<String>> ();
	private boolean in_loop = false;
	private boolean in_method = false;
	private boolean in_virtual_method = false;
	private Type return_type = null; //expected return value of a method, if in_methdo is set true
	
	
//retrieve first non-null type from stack
public String getType(String variable)//it's static since this depends on the location in the code
{
	for (String string : this.myVars.get(variable))
	{
		if (string != null)
			return string;
	}
	return null;
}

//pop from each stack
public void popAll()
{
	for (String string: this.myVars.keySet())
	{
		this.myVars.get(string).pop();
	}
}


public void addAll()
{
	for (String string : this.myVars.keySet())
	{
		add_variable(string, (String) null);
		//this.myVars.get(string).add(null);
	}
}
	
	public SemanticAnalayzer(ASTNode root, SemanticAnalayzer sa)
	{
		this.in_loop = sa.in_loop;
		this.in_method = sa.in_method;
		this.myVars = sa.myVars;
		this.return_type = sa.return_type;
	}
	
	public SemanticAnalayzer() {
	}
	
	//each function returns a List of SemanticAnalyzer objects, represent the sons of the current in the tree
	public Object visit(Program program)
	{
		//SemanticAnalayzer sa = (SemanticAnalayzer) program.accept(this);
		
		
		//List <SemanticAnalayzer> res = new ArrayList<SemanticAnalayzer>();
		
		for (ICClass icClass : program.getClasses())
		{
			//res.add(new SemanticAnalayzer(icClass, this));
			icClass.accept(this);
		}
		//return res;
		return null;
			
		//return (Object) (new SemanticAnalayzer(my_class, sa.myVars));
	}

public void add_variable(Field field)
{
	add_variable(field.getName(), field.getType());
}


public void add_variable(Method method)
{
	add_variable(method.getName(), method.getType());
}

public void add_variable(String name_of_var, String name_of_type)
{
	if (name_of_type.split(" ")[0]==null)
	{
		name_of_type = name_of_type+" 0";
	}
	Stack<String> new_stack = this.myVars.get(name_of_var);
	if (new_stack == null)
	{
		new_stack = new Stack<String>();
	}
	else
	{
		String prevType = new_stack.pop();
		if (prevType != null && name_of_type != null)
		{
			System.err.println("Error: variable "+name_of_var+" already exists in this block");
		}
	}
	new_stack.add(name_of_type);
	this.myVars.put(name_of_var, new_stack);
}

public void add_variable(String name, int line)
{
	add_variable(name, new UserType(line, name));
}

public void add_variable(String name, Type type)
{
	add_variable(name, type.getName());
}


public Object visit(ICClass icClass)
	{
		//List <SemanticAnalayzer> res = new ArrayList<SemanticAnalayzer>();
		
		for (Field field : icClass.getFields())
		{
			add_variable(field);
			field.accept(this);
		}
		for (Method method : icClass.getMethods())
		{
			//add_variable(method);
			method.accept(this);
		}
		//add_variable(icClass.getName(), new UserType(this.root.getLine(),i));
		return null;
	}

	public Object visit(Field field)
	{
		add_variable(field);
		return null;
	}
	
	public Object visit(VirtualMethod method)
	{
		this.return_type = method.getType();
		this.in_method = true;
		this.in_virtual_method = true;
		add_variable(method.getName(), method.getLine());
		for (Formal formal : method.getFormals())
		{
			formal.accept(this);
		}
		for (Stmt stmt : method.getStatements())
		{
			stmt.accept(this);
		}
		this.in_virtual_method = false;
		this.in_method = false;
		this.return_type = null;
		return null;
	}
	
	public Object visit(StaticMethod method)
	{
		this.return_type = method.getType();
		this.in_method = true;
		add_variable(method.getName(), method.getLine());
		for (Formal formal : method.getFormals())
		{
			formal.accept(this);
		}
		for (Stmt stmt : method.getStatements())
		{
			stmt.accept(this);
		}
		this.in_virtual_method = false;
		this.in_method = false;
		return null;
	}
	public Object visit(LibraryMethod method)
	{
		return null;
	}
	public Object visit(Formal formal)
	{
		add_variable(formal.getName(), formal.getType());
		return null;
	}
	public Object visit(PrimitiveType type)
	{
		add_variable(type.getName(), type.getLine());
		return null;
	}
	public Object visit(UserType type)
	{
		return null;
	}
	public Object visit(AssignStmt assignment)
	{
		if (!type(assignment.getVariable()).equals(type(assignment.getAssignVal())))
		{
			System.err.println(type(assignment.getVariable()));
			System.err.println(type(assignment.getAssignVal()));
			System.err.println("Error: Assignment of different types");
		}
		
		return null;
	}
	public Object visit(CallStmt callStatement)
	{
		//if (!this.myVars.contains(callStatement.getCall()))
		return null;
	}
	public Object visit(ReturnStmt returnStatement)
	{
		if (!this.in_method)
		{
			System.err.println("Error: return outside a mehtod");
		}
		if (! this.return_type.getName().equals(type(returnStatement.getValue())))
		{
			System.err.println("Error: expected other return type");
		}
		return null;
	}
	public Object visit(IfStmt ifStatement)
	{
		return null;
	}
	public Object visit(WhileStmt whileStatement)
	{
		whileStatement.getCond().accept(this);
		this.in_loop = true;
		whileStatement.getStmt().accept(this);
		this.in_loop =false;
		return null;
	}
	public Object visit(BreakStmt breakStatement)
	{
		if (this.in_loop == false)
		{
			System.err.println("Error: break statement outside loop");
		}
		return null;
	}
	public Object visit(ContinueStmt continueStatement)
	{
		if (this.in_loop == false)
		{
			System.err.println("Error: continue statement outside loop");
		}
		return null;
	}
	public Object visit(StmtBlock statementsBlock)
	{
		addAll();
		for (Stmt stmt : statementsBlock.getStatements())
		{
			stmt.accept(this);
		}
		popAll();
		return null;
	}
	public Object visit(LocalVarStmt localVariable)
	{
		String first_type = type(localVariable.getInitValue());
		String second_type;
		
		if (localVariable.getType() instanceof PrimitiveType)
		{
			PrimitiveType primitiveType = (PrimitiveType) localVariable.getType();
			second_type = localVariable.getType().getName() + " "+ primitiveType.getDimension();
		}
		else
		{
			System.err.println(localVariable.getType().getName());
			second_type = localVariable.getType().getName()+ " "+0;
		}
		add_variable(localVariable.getName(), second_type);
		
		//if (!type(localVariable.getInitValue()) .equals(localVariable.getType().getName()))
		if (first_type!= null && !first_type.equals(second_type))
		{
			System.err.println("first ="+first_type+ " and second ="+second_type);
			System.err.println("Error: assignment of incorrect type");
		}
		return null;
	}
	public Object visit(VarLocationExpr location)
	{
		return null;
	}
	public Object visit(ArrayLocationExpr location)
	{
		System.out.println("RRRRRRRRRRRR");
		if (!type(location.getIndex()).equals("int"))
		{
			System.err.println("Error: non-integer array subscript");
		}
		return null;
	}
	public Object visit(StaticCallExpr call)
	{
		return null;
	}
	public Object visit(VirtualCallExpr call)
	{
		return null;
	}
	public Object visit(ThisExpr thisExpression)
	{
		if (this.in_virtual_method == false)
		{
			System.err.println("Error: this expression outside a virtual method");
		}
		return null;
	}
	public Object visit(NewClassExpr newClass)
	{
		return null;
	}
	public Object visit(NewArrayExpr newArray)
	{
		return null;
	}
	public Object visit(LengthExpr length)
	{
		return null;
	}
	public Object visit(MathBinaryOpExpr binaryOp)
	{
		return null;
	}
	public Object visit(LogicalBinaryOpExpr binaryOp)
	{
		return null;
	}
	public Object visit(MathUnaryOpExpr unaryOp)
	{
		return null;
	}
	public Object visit(LogicalUnaryOpExpr unaryOp)
	{
		return null;
	}
	public Object visit(LiteralExpr literal)
	{
		return null;
	}
	public Object visit(ExprBlock expressionBlock)
	{
		return null;
	}
	

public String type(Expr expr)
	 {
		 if (expr instanceof VarLocationExpr)
		 {
			 VarLocationExpr varLocationExpr = (VarLocationExpr) expr;
			 
			 String res = getType(varLocationExpr.getName());
			 return res;
		 }
		 else if (expr instanceof LiteralExpr)
		 {
			 LiteralExpr literalExpr = (LiteralExpr) expr;
			 return MyToLowercase(literalExpr.getType().getDescription().split(" ")[0]);
		 }
		 else if (expr instanceof ArrayLocationExpr)
		 {
			 ArrayLocationExpr arrayLocationExpr = (ArrayLocationExpr) expr;
			 String my_type = type(arrayLocationExpr.getArray());
			 if (my_type != null)
				 return my_type;
			 String[] my_arr = my_type.split(" ");
			 int dimension;
		     dimension = Integer.parseInt(my_arr[1]);
		     if (dimension ==0)
		     {
					System.err.println("Error: subscript of two low dimensions");
			 }
			 my_type = my_arr[0];
			 return my_type + " " + (dimension-1);
			 
		 }
		 else
	     try
		 {
			 System.err.println(expr.getClass());
		 }
		 catch (Exception e)
		 {
			 
		 }
		 
		 return null;
	} 
	

public String MyToUppercase(String x)
{
	if (x.equals("int"))
		return "Integer";
	else if (x.equals("boolean"))
		return "Boolean";
	else if (x.equals("string"))
		return "String";
	else
		return x;
}

public String MyToLowercase(String x)
{
	if (x.equals("Integer"))
		return "int";
	else if(x.equals("Boolean"))
		return "boolean";
	else if (x.equals("String"))
		return "string";
	else
		return x;
}
	
}
