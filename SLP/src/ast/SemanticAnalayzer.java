package ast;

import java.util.*;


public class SemanticAnalayzer implements Visitor{
	private ASTNode root;
	//private PriorityQueue<HashMap<String, Type>> myVars = new PriorityQueue<HashMap<String, Type>> (); //map variable -> type
	//private HashMap<String, PriorityQueue<Type>> myVars = new HashMap<String, PriorityQueue<Type>> ();
	private HashMap<String, Stack<Type>> myVars = new HashMap<String, Stack<Type>> ();
	private Type rootType = null;
	private int in_loop = 0;//depth of loops - loop inside a loop inside a loop, etc.
	private boolean in_method = false;
	private boolean in_virtual_method = false;
	private Type return_type = null; //expected return value of a method, if in_methdo is set true
	
	
//retrieve first non-null type from stack
public Type getType(String variable)//it's static since this depends on the location in the code
{
	for (Type type : this.myVars.get(variable))
	{
		if (type != null)
			return type;
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
		add_variable(string, null);
		//this.myVars.get(string).add(null);
	}
}
	
	public SemanticAnalayzer(ASTNode root, SemanticAnalayzer sa)
	{
		this.root = root;
		this.in_loop = sa.in_loop;
		this.in_method = sa.in_method;
		this.myVars = sa.myVars;
		this.return_type = sa.return_type;
	}
	
	public SemanticAnalayzer(ASTNode root) {
		this.root = root;
	}
	
	//each function returns a List of SemanticAnalyzer objects, represent the sons of the current in the tree
	public Object visit(Program program)
	{
		//SemanticAnalayzer sa = (SemanticAnalayzer) program.accept(this);
		
		
		List <SemanticAnalayzer> res = new ArrayList<SemanticAnalayzer>();
		
		for (ICClass icClass : program.getClasses())
		{
			//res.add(new SemanticAnalayzer(icClass, this));
			icClass.accept(this);
		}
		return res;
			
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

public void add_variable(String name, int line)
{
	add_variable(name, new UserType(line, name));
}

public void add_variable(String name, Type type)
{
	
	Stack<Type> new_stack = this.myVars.get(name);
	if (new_stack == null)
	{
		new_stack = new Stack<Type>();
	}
	else
	{
		Type prevType = new_stack.pop();
		if (prevType != null && type != null)
		{
			System.err.println("Error: variable "+name+" already exists in this block");
		}
	}
	//else if (new_stack.pop()!=null && type != null) 
	{
		//System.out.println("Error: variable "+name+" already exists in this block");
	}
	new_stack.add(type);
	this.myVars.put(name, new_stack);
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
	public Object visit(AssigmStmt assignment)
	{
		
		if (type(assignment.getVariable())!= type(assignment.getAssignVal()))
		{
			System.err.println("Error: Assignment of different types.");
			//System.exit(1);
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
		if (this.return_type != this.rootType)
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
		this.in_loop ++;
		whileStatement.getStmt().accept(this);
		this.in_loop --;
		return null;
	}
	public Object visit(BreakStmt breakStatement)
	{
		if (this.in_loop == 0)
		{
			System.err.println("Error: break statement outside loop");
		}
		if (this.in_loop >0)
		{
			this.in_loop --;
		}
		return null;
	}
	public Object visit(ContinueStmt continueStatement)
	{
		if (this.in_loop == 0)
		{
			System.err.println("Error: continue statement outside loop");
		}
		if (this.in_loop >0)
		{
			this.in_loop --;
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
		add_variable(localVariable.getName(), localVariable.getType());
		return null;
	}
	public Object visit(VarLocationExpr location)
	{
		return null;
	}
	public Object visit(ArrayLocationExpr location)
	{
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
		//SemanticAnalayzer sa = (SemanticAnalayzer) expressionBlock.accept(this);
		//return new SemanticAnalayzer(expressionBlock, sa.myVars);
	}
	
	 
	 public Type type(BinaryOpExpr binop)
	 {
		 if (type(binop.getFirstOperand()) != type(binop.getSecondOperand()))
    		{
			  System.err.println("Error: operation"+binop.getOperator()+"on different types");
	    	}
		 return type(binop.getFirstOperand());
	 }
	 
	 public  Type type(UnaryOpExpr unop)
	 {
		 return type(unop.getOperand());
	 }
	 
	 public Type type(Expr expr){return null;} //this is for syntactic reasons. however, it is impossible to instatiate Expr, so this method is never used
	 
	 //public Type type()

	 
}
