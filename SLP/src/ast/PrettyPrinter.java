package ast;

/**
 * Pretty printing visitor - travels along the AST and prints info about each node,
 * in an easy-to-comprehend format.
 */
public class PrettyPrinter implements Visitor {
	private int depth = 0; // depth of indentation
	private String ICFilePath;
	private ASTNode root;

	/**
	 * Constructs a new pretty printer visitor.
	 * 
	 * @param ICFilePath  The path + name of the IC file being compiled.
	 * @param root The root of the AST.
	 */
	public PrettyPrinter(String ICFilePath, ASTNode root) {
		this.ICFilePath = ICFilePath;
		this.root = root;
	}
	
	/** 
	 * Prints the AST with the given root.
	 */
	public void print() {
		System.out.println(root.accept(this));
	}

	/**
	 * Appends to output, an indentation as well as node's line number,
	 * according to the current depth and the specified node respectively.
	 * 
	 * @param output The output buffer.
	 * @param node   The AST node that is visited now.
	 */
	private void indent(StringBuffer output, ASTNode node) {
		output.append("\n");
		for (int i = 0; i < depth; i++)
			output.append(" ");
		if (node != null)
			output.append(node.getLine() + ": ");
	}

	/**
	 * Appends an indentation to output according to the current depth.
	 * 
	 * @param output The output buffer.
	 * @param node   The AST node that is visited now.
	 */
	private void indent(StringBuffer output) {
		indent(output, null);
	}

	public Object visit(Program program) {
		StringBuffer output = new StringBuffer();

		indent(output);
		output.append("Abstract Syntax Tree: " + ICFilePath + "\n");
		for (ICClass icClass : program.getClasses())
			output.append(icClass.accept(this));
		return output.toString();
	}

	public Object visit(ICClass icClass) {
		StringBuffer output = new StringBuffer();
		
		indent(output, icClass);
		output.append("Declaration of class: " + icClass.getName());
		if (icClass.hasSuperClass())
			output.append(", subclass of " + icClass.getSuperClassName());
		depth += 2;
		for (Field field : icClass.getFields())
			output.append(field.accept(this));
		for (Method method : icClass.getMethods())
			output.append(method.accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(PrimitiveType type) {
		StringBuffer output = new StringBuffer();

		indent(output, type);
		output.append("Primitive data type: ");
		if (type.getDimension() > 0)
			output.append(type.getDimension() + "-dimensional array of ");
		output.append(type.getName());
		return output.toString();
	}

	public Object visit(UserType type) {
		StringBuffer output = new StringBuffer();

		indent(output, type);
		output.append("User-defined data type: ");
		if (type.getDimension() > 0)
			output.append(type.getDimension() + "-dimensional array of ");
		output.append(type.getName());
		return output.toString();
	}

	public Object visit(Field field) {
		StringBuffer output = new StringBuffer();

		indent(output, field);
		output.append("Declaration of field: " + field.getName());
		++depth;
		output.append(field.getType().accept(this));
		--depth;
		return output.toString();
	}

	public Object visit(LibraryMethod method) {
		StringBuffer output = new StringBuffer();

		indent(output, method);
		output.append("Declaration of library method: " + method.getName());
		depth += 2;
		output.append(method.getType().accept(this));
		for (Formal formal : method.getFormals())
			output.append(formal.accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(Formal formal) {
		StringBuffer output = new StringBuffer();

		indent(output, formal);
		output.append("Parameter: " + formal.getName());
		++depth;
		output.append(formal.getType().accept(this));
		--depth;
		return output.toString();
	}

	public Object visit(VirtualMethod method) {
		StringBuffer output = new StringBuffer();

		indent(output, method);
		output.append("Declaration of virtual method: " + method.getName());
		depth += 2;
		output.append(method.getType().accept(this));
		for (Formal formal : method.getFormals())
			output.append(formal.accept(this));
		for (Stmt statement : method.getStatements())
			output.append(statement.accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(StaticMethod method) {
		StringBuffer output = new StringBuffer();

		indent(output, method);
		output.append("Declaration of static method: " + method.getName());
		depth += 2;
		output.append(method.getType().accept(this));
		for (Formal formal : method.getFormals())
			output.append(formal.accept(this));
		for (Stmt statement : method.getStatements())
			output.append(statement.accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(AssignStmt assignment) {
		StringBuffer output = new StringBuffer();

		indent(output, assignment);
		output.append("Assignment statement");
		depth += 2;
		output.append(assignment.getVariable().accept(this));
		output.append(assignment.getAssignVal().accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(CallStmt callStatement) {
		StringBuffer output = new StringBuffer();

		indent(output, callStatement);
		output.append("Method call statement");
		++depth;
		output.append(callStatement.getCall().accept(this));
		--depth;
		return output.toString();
	}

	public Object visit(ReturnStmt returnStatement) {
		StringBuffer output = new StringBuffer();

		indent(output, returnStatement);
		output.append("Return statement");
		if (returnStatement.hasValue())
			output.append(", with return value");
		if (returnStatement.hasValue()) {
			++depth;
			output.append(returnStatement.getValue().accept(this));
			--depth;
		}
		return output.toString();
	}

	public Object visit(IfStmt ifStatement) {
		StringBuffer output = new StringBuffer();

		indent(output, ifStatement);
		output.append("If statement");
		if (ifStatement.hasElse())
			output.append(", with Else operation");
		depth += 2;
		output.append(ifStatement.getCond().accept(this));
		output.append(ifStatement.getStmt().accept(this));
		if (ifStatement.hasElse())
			output.append(ifStatement.getElseStmt().accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(WhileStmt whileStatement) {
		StringBuffer output = new StringBuffer();

		indent(output, whileStatement);
		output.append("While statement");
		depth += 2;
		output.append(whileStatement.getCond().accept(this));
		output.append(whileStatement.getStmt().accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(BreakStmt breakStatement) {
		StringBuffer output = new StringBuffer();

		indent(output, breakStatement);
		output.append("Break statement");
		return output.toString();
	}

	public Object visit(ContinueStmt continueStatement) {
		StringBuffer output = new StringBuffer();

		indent(output, continueStatement);
		output.append("Continue statement");
		return output.toString();
	}

	public Object visit(StmtBlock statementsBlock) {
		StringBuffer output = new StringBuffer();

		indent(output, statementsBlock);
		output.append("Block of statements");
		depth += 2;
		for (Stmt statement : statementsBlock.getStatements())
			output.append(statement.accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(LocalVarStmt localVariable) {
		StringBuffer output = new StringBuffer();

		indent(output, localVariable);
		output.append("Declaration of local variable: "
				+ localVariable.getName());
		if (localVariable.hasInitValue()) {
			output.append(", with initial value");
			++depth;
		}
		++depth;
		output.append(localVariable.getType().accept(this));
		if (localVariable.hasInitValue()) {
			output.append(localVariable.getInitValue().accept(this));
			--depth;
		}
		--depth;
		return output.toString();
	}

	public Object visit(VarLocationExpr location) {
		StringBuffer output = new StringBuffer();

		indent(output, location);
		output.append("Reference to variable: " + location.getName());
		if (location.isExternal())
			output.append(", in external scope");
		if (location.isExternal()) {
			++depth;
			output.append(location.getLocation().accept(this));
			--depth;
		}
		return output.toString();
	}

	public Object visit(ArrayLocationExpr location) {
		StringBuffer output = new StringBuffer();

		indent(output, location);
		output.append("Reference to array");
		depth += 2;
		output.append(location.getArray().accept(this));
		output.append(location.getIndex().accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(StaticCallExpr call) {
		StringBuffer output = new StringBuffer();

		indent(output, call);
		output.append("Call to static method: " + call.getName()
				+ ", in class " + call.getClassName());
		depth += 2;
		for (Expr argument : call.getArguments())
			output.append(argument.accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(VirtualCallExpr call) {
		StringBuffer output = new StringBuffer();

		indent(output, call);
		output.append("Call to virtual method: " + call.getName());
		if (call.isExternal())
			output.append(", in external scope");
		depth += 2;
		if (call.isExternal())
			output.append(call.getLocation().accept(this));
		for (Expr argument : call.getArguments())
			output.append(argument.accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(ThisExpr thisExpression) {
		StringBuffer output = new StringBuffer();

		indent(output, thisExpression);
		output.append("Reference to 'this' instance");
		return output.toString();
	}

	public Object visit(NewClassExpr newClass) {
		StringBuffer output = new StringBuffer();

		indent(output, newClass);
		output.append("Instantiation of class: " + newClass.getName());
		return output.toString();
	}

	public Object visit(NewArrayExpr newArray) {
		StringBuffer output = new StringBuffer();

		indent(output, newArray);
		output.append("Array allocation");
		depth += 2;
		output.append(newArray.getType().accept(this));
		output.append(newArray.getSize().accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(LengthExpr length) {
		StringBuffer output = new StringBuffer();

		indent(output, length);
		output.append("Reference to array length");
		++depth;
		output.append(length.getArray().accept(this));
		--depth;
		return output.toString();
	}

	public Object visit(MathBinaryOpExpr binaryOp) {
		StringBuffer output = new StringBuffer();

		indent(output, binaryOp);
		output.append("Mathematical binary operation: "
				+ binaryOp.getOperator().getDescription());
		depth += 2;
		output.append(binaryOp.getFirstOperand().accept(this));
		output.append(binaryOp.getSecondOperand().accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(LogicalBinaryOpExpr binaryOp) {
		StringBuffer output = new StringBuffer();

		indent(output, binaryOp);
		output.append("Logical binary operation: "
				+ binaryOp.getOperator().getDescription());
		depth += 2;
		output.append(binaryOp.getFirstOperand().accept(this));
		output.append(binaryOp.getSecondOperand().accept(this));
		depth -= 2;
		return output.toString();
	}

	public Object visit(MathUnaryOpExpr unaryOp) {
		StringBuffer output = new StringBuffer();

		indent(output, unaryOp);
		output.append("Mathematical unary operation: "
				+ unaryOp.getOperator().getDescription());
		++depth;
		output.append(unaryOp.getOperand().accept(this));
		--depth;
		return output.toString();
	}

	public Object visit(LogicalUnaryOpExpr unaryOp) {
		StringBuffer output = new StringBuffer();

		indent(output, unaryOp);
		output.append("Logical unary operation: "
				+ unaryOp.getOperator().getDescription());
		++depth;
		output.append(unaryOp.getOperand().accept(this));
		--depth;
		return output.toString();
	}

	public Object visit(LiteralExpr literal) {
		StringBuffer output = new StringBuffer();

		indent(output, literal);
		output.append(literal.getType().getDescription() + ": "
				+ literal.getType().toFormattedString(literal.getValue()));
		return output.toString();
	}

	public Object visit(ExprBlock expressionBlock) {
		StringBuffer output = new StringBuffer();

		indent(output, expressionBlock);
		output.append("Parenthesized expression");
		++depth;
		output.append(expressionBlock.getExpr().accept(this));
		--depth;
		return output.toString();
	}
}