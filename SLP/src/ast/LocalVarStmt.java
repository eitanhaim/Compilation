package ast;

/**
 * An AST node for a local variable declaration statement.
 */
public class LocalVarStmt extends Stmt {
	private Type type;
	private String name;
	private Expr initValue = null;

	/**
	 * Constructs a new local variable declaration statement node.
	 * 
	 * @param type  Data type of local variable.
	 * @param name  Name of local variable.
	 */
	public LocalVarStmt(Type type, String name) {
		super(type.getLine());
		this.type = type;
		this.name = name;
	}

	/**
	 * Constructs a new local variable declaration statement node, with an
	 * initial value.
	 * 
	 * @param type		 Data type of local variable.
	 * @param name		 Name of local variable.
	 * @param initValue  Initial value of local variable.
	 */
	public LocalVarStmt(Type type, String name, Expr initValue) {
		this(type, name);
		this.initValue = initValue;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public boolean hasInitValue() {
		return (initValue != null);
	}

	public Expr getInitValue() {
		return initValue;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		return visitor.visit(this, context);
	}
}
