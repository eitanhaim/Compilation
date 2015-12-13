package type_table;

import java.util.List;

import ic.DataType;
import ast.*;
import semantic_analysis.*;
import symbol_table.SymbolKind;
import symbol_table.SymbolTable;

/**
 * Validator for a type table.
 */
public class TypeValidator implements Visitor {
	private int loopNesting;
	private TypeTable typeTable;
	private SemanticErrorThrower semanticErrorThrower;
	
	/**
	 * Constructs a new validator for the specified type table.
	 *
	 * @param typeTable  A type table.
	 */
	public TypeValidator(TypeTable typeTable) {
		this.typeTable = typeTable;
	}
	
	/**
	 * Scans the AST tree recursively and check the following semantic issues:
	 * - Type checking
	 * - Each method has return statement in all of the computation paths.
	 * - No continue and break keywords appear outside a while scope.
	 * - This expression is not called from a static method.
	 * In addition, the scan sets types to expressions during the type checking.
	 * 
	 * @param program	The root of the AST.
	 */
	public void validate(Program program) throws SemanticError {
		if (!(Boolean)program.accept(this))
			this.semanticErrorThrower.execute();
	}
	
	@Override
	public Object visit(Program program) {
		loopNesting = 0;
		for (ICClass icClass : program.getClasses()) {
			if (!(Boolean)icClass.accept(this))
				return false;
		}
		return true;
	}

	@Override
	public Object visit(ICClass icClass) {
		for (Method method : icClass.getMethods()) 
			if (!(Boolean)method.accept(this))
				return false;
		
		return true;
	}

	@Override
	public Object visit(Field field) {
		return true;
	}
	
	@Override
	public Object visit(VirtualMethod method) {
		return visitMethod(method);
	}

	@Override
	public Object visit(StaticMethod method) {
		return visitMethod(method);
	}

	@Override
	public Object visit(LibraryMethod method) {
		return visitMethod(method);
	}

	/**
	 * Visit any kind of method.
	 * 
	 * @param method  Method to visit.
	 * @return 		  null
	 */
	private Object visitMethod(Method method) {
		// type checking of all the statements
		for (Formal formal : method.getFormals()) 
			if (!(Boolean)formal.accept(this))
				return false;
		for (Stmt statement : method.getStatements()) 
			if (!(Boolean)statement.accept(this))
				return false;
		
		// check that the method has a return statement in each computation path:
		MethodType methodType = (MethodType)method.getEntryType();
		if (methodType.getReturnType().isVoidType()) // if this is a void type method, no return statement is needed.
			return true;
		
		if((method instanceof LibraryMethod)) // if this is a library method, no return statement is needed.
			return true;
		
		if (!testRetrunPaths(method.getStatements())) { // no return statement error:
			semanticErrorThrower =  new SemanticErrorThrower(method.getLine(), String.format("Method %s has no return statement", method.getName()));
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks if the specified statements contain a return statement.
	 * 
	 * @param statements  Statements to check.
	 * @return			  True if the specified statements contain a return statement, otherwise returns false.
	 */
	private Boolean testRetrunPaths(List<Stmt> statements) {
		for (Stmt stmnt : statements) 
			if (testRetrunPaths(stmnt))
				return true;
		
		return false;
	}
	
	/**
	 * Checks if the specified statement contains a return statement.
	 * 
	 * @param statements  Statement to check.
	 * @return			  True if the specified statement contains a return statement, otherwise returns false.
	 */
	private Boolean testRetrunPaths(Stmt stmnt) {
		if (stmnt instanceof ReturnStmt)
			return true;
		if (stmnt instanceof StmtBlock) 
			return testRetrunPaths(((StmtBlock)stmnt).getStatements());
		if (stmnt instanceof IfStmt) {
			IfStmt ifStmnt = (IfStmt)stmnt;
			if (ifStmnt.hasElse())
				return (testRetrunPaths(ifStmnt.getStmt()) && testRetrunPaths(ifStmnt.getElseStmt()));
		}
		return false;
	}
	
	@Override
	public Object visit(Formal formal) {
		return true;
	}

	@Override
	public Object visit(PrimitiveType type) { 
		// not called
		return null;
	}

	@Override
	public Object visit(UserType type) {
		// not called
		return null;
	}

	@Override
	public Object visit(AssignStmt assignment) {
		if (!(Boolean)assignment.getVariable().accept(this))
			return false;
		Type variable = assignment.getVariable().getEntryType();
		if (!(Boolean)assignment.getAssignVal().accept(this))
			return false;
		Type assignValue = assignment.getAssignVal().getEntryType();
		
		// check the types are legal for assignment
		if (!isLegalAssignment(variable, assignValue)) {
			semanticErrorThrower =  new SemanticErrorThrower(assignment.getLine(), "Value assigned to local variable type mismatch");
			return false;
		} 

		return true;
	}
	
	/**
	 * Checks if the assignment of assignValue to variable is legal.
	 * 
	 * @param variable	   Variable to assign a value to.		 
	 * @param assignValue  Value to assign.
	 * @return			   True if the assignment is legal, otherwise returns false.
	 */
	private Boolean isLegalAssignment(Type variable, Type assignValue) {
		return (variable.isNullAssignable() && assignValue.isNullType()) || 
				(assignValue.subtypeOf(variable));
	}

	@Override
	public Object visit(CallStmt callStatement) {
		if (!(Boolean)callStatement.getCall().accept(this))
			return false;

		return true;
	}

	@Override
	public Object visit(ReturnStmt returnStatement) {
		// check the return value type corresponds the method return type. 
		Type typeInFact;
		if (returnStatement.hasValue()) {
			if (!(Boolean)returnStatement.getValue().accept(this))
				return false;
			typeInFact = returnStatement.getValue().getEntryType();
		}
		else
			typeInFact = typeTable.getPrimitiveType(DataType.VOID.getDescription());
		
		MethodType methodType = (MethodType)returnStatement.getMethodType();
		if (!isLegalAssignment(methodType.getReturnType(), typeInFact)) {
			semanticErrorThrower =  new SemanticErrorThrower(returnStatement.getLine(), String.format(
					"Return statement is not of type %s", methodType.getReturnType().toString()));
			return false;
		}
		return true;
	}

	@Override
	public Object visit(IfStmt ifStatement) {
		if (!(Boolean)ifStatement.getCond().accept(this))
			return false;
		
		Type typeCondition = ifStatement.getCond().getEntryType();
		if (!typeCondition.isBoolType()) { // condition expression must have a boolean type

			semanticErrorThrower =  new SemanticErrorThrower(ifStatement.getLine(), "Non boolean condition for if statement");
			return false;
		}
		
		if (!(Boolean)ifStatement.getStmt().accept(this))
			return false;
		
		if (ifStatement.hasElse())
			if (!(Boolean)ifStatement.getElseStmt().accept(this))
				return false;
		return true;
	}

	@Override
	public Object visit(WhileStmt whileStatement) {
		if (!(Boolean)whileStatement.getCond().accept(this))
			return false;
		
		Type typeCondition = whileStatement.getCond().getEntryType();
		if (!typeCondition.isBoolType()) { // condition expression must have a boolean type
			semanticErrorThrower =  new SemanticErrorThrower(whileStatement.getLine(), "Non boolean condition for while statement");
			return false;
		}
		loopNesting++;
		if (!(Boolean)whileStatement.getStmt().accept(this))
			return false;
		
		loopNesting--;
		return true;
	}

	@Override
	public Object visit(BreakStmt breakStatement) {
		// check if the break statement was called only from a while scope.
		if (!isBreakContinueValid()) {
			semanticErrorThrower =  new SemanticErrorThrower(breakStatement.getLine(), 
					"Use of 'break' statement outside of loop not allowed");
			return false;
		}
		return true;
	}

	@Override
	public Object visit(ContinueStmt continueStatement) {
		// check if the continue statement was called only from a while scope.
		if (!isBreakContinueValid()) {
			semanticErrorThrower =  new SemanticErrorThrower(continueStatement.getLine(), 
					"Use of 'continue' statement outside of loop not allowed");
			return false;
		}
		return true;
	}
	
	// help to check if a continue or a break statement were called from a while scope
	private boolean isBreakContinueValid() {
		return loopNesting > 0;
	}

	@Override
	public Object visit(StmtBlock statementsBlock) {
		// type validation on all the statements in a statement block
		for (Stmt statement : statementsBlock.getStatements()) 
			if (!(Boolean)statement.accept(this))
				return false;
		return true;
	}

	@Override
	public Object visit(LocalVarStmt localVariable) {
		if (localVariable.hasInitValue()) 
		{
			if (!(Boolean)localVariable.getInitValue().accept(this))
				return false;
			Type varType = localVariable.getEntryType();
			Type initType = localVariable.getInitValue().getEntryType();
			
			// check the types are legal for assignment
			if (!isLegalAssignment(varType, initType)) {
				semanticErrorThrower =  new SemanticErrorThrower(localVariable.getLine(), "Value assigned to local variable type mismatch");
				return false;
			} 
		}
		return true;
	}

	@Override
	public Object visit(VarLocationExpr location) {
		if (location.isExternal()) {
			if (!(Boolean)location.getLocation().accept(this))
				return false;
			
			// outside location must be an instance of a class
			if (!location.getLocation().getEntryType().isClassType()) {
				semanticErrorThrower =  new SemanticErrorThrower(location.getLine(), "External location must have a class type");
				return false;
			}
		}
		return true;
	}

	@Override
	public Object visit(ArrayLocationExpr location) {
		if (!(Boolean)location.getIndex().accept(this))
			return false;
		if (!(Boolean)location.getArray().accept(this))
			return false;
		Type typeIndex = location.getIndex().getEntryType();
		
		Type typeArray = location.getArray().getEntryType();
		// check the index expression has an int type.
		if (!typeIndex.isIntType()) {
			semanticErrorThrower = new SemanticErrorThrower(location.getLine(), "Array index must be an integer");
			return false;
		}
		
		// set the type of the array location with array T[] to be T.
		location.setEntryType(typeTable.getTypeFromArray(typeArray));
		return true;
	}

	@Override
	public Object visit(StaticCallExpr call) {
		for (Expr arg : call.getArguments())
			if (!(Boolean)arg.accept(this))
				return false;
		MethodType calledMethodType = (MethodType)call.getMethodType();
		if (call.getArguments().size() != calledMethodType.getParamTypes().length) {
			semanticErrorThrower = new SemanticErrorThrower(call.getLine(), 
					String.format("Method except %d arguments but gets %d", 
							calledMethodType.getParamTypes().length, call.getArguments().size()));
			return false;
		}

		for (int i = 0; i < call.getArguments().size(); i++) {
				if (!isLegalAssignment(calledMethodType.getParamTypes()[i], 
					call.getArguments().get(i).getEntryType())) {
				semanticErrorThrower = new SemanticErrorThrower(call.getLine(), 
						"Argument type dosen't match the method parameter type");
				return false;
			}
		}
		call.setEntryType(typeTable.getReturnTypeFromMethodType(calledMethodType));
		return true; 
	}

	@Override
	public Object visit(VirtualCallExpr call) {
		if (call.isExternal()) {
			if (!(Boolean)call.getLocation().accept(this))
				return false;
			
			Type locationType = call.getLocation().getEntryType();
			if (!locationType.isClassType()) {
				semanticErrorThrower = new SemanticErrorThrower(call.getLine(), "Object is not of class type");
				return false;
			}
		}	
		
		for (Expr arg : call.getArguments())
			if (!(Boolean)arg.accept(this))
				return false;
		MethodType calledMethodType = (MethodType)call.getMethodType();
		if (call.getArguments().size() != calledMethodType.getParamTypes().length) {
			semanticErrorThrower = new SemanticErrorThrower(call.getLine(), 
					String.format("Method expects %d arguments but gets %d", 
							calledMethodType.getParamTypes().length, call.getArguments().size()));
			return false;
		}
		for (int i = 0; i < call.getArguments().size(); i++) {
			if (!isLegalAssignment(calledMethodType.getParamTypes()[i], 
					call.getArguments().get(i).getEntryType())) {
				semanticErrorThrower = new SemanticErrorThrower(call.getLine(), "Argument type dosen't match the method parameter type");
				return false;
			}
		}
		call.setEntryType(typeTable.getReturnTypeFromMethodType(calledMethodType));
		return true; 
	}

	@Override
	public Object visit(ThisExpr thisExpression) {
		SymbolTable scope = thisExpression.getSymbolTable();
		while (scope.getId().contains("block#")) 
			scope = scope.getParentSymbolTable();
		if (scope.getParentSymbolTable().getEntry(scope.getId()).getKind() == SymbolKind.STATIC_METHOD) {
			semanticErrorThrower = new SemanticErrorThrower(thisExpression.getLine(), 
					"Use of 'this' expression inside static method is not allowed");
			return false;
		}
		scope = scope.getParentSymbolTable();
		
		thisExpression.setEntryType(typeTable.getClassType(scope.getId()));
		return true;
	}

	@Override
	public Object visit(NewClassExpr newClass) {
		newClass.setEntryType(typeTable.getClassType(newClass.getName()));
		return true;
	}

	@Override
	public Object visit(NewArrayExpr newArray) {
		if (!(Boolean)newArray.getSize().accept(this))
			return false;
		
		Type typeSize = newArray.getSize().getEntryType();
		
		// check the array size expression is of type int.
		if (typeSize == null || !typeSize.isIntType()) {
			semanticErrorThrower = new SemanticErrorThrower(newArray.getLine(), "Array size must be an integer");
			return false;
		}
		
		if (newArray.getType() instanceof PrimitiveType)
			newArray.setEntryType(typeTable.getArrayFromType(typeTable.getPrimitiveType(newArray.getType().getName()), 
					newArray.getType().getDimension()));
		if (newArray.getType() instanceof UserType)
			newArray.setEntryType(typeTable.getArrayFromType(typeTable.getClassType(newArray.getType().getName()), 
					newArray.getType().getDimension()));

		return true;
	}

	@Override
	public Object visit(LengthExpr length) {
		if (!(Boolean)length.getArray().accept(this))
			return false;
		Type type = length.getArray().getEntryType();
		
		// the expression which is evaluated by its length must have an array type. 
		if (!type.isArrayType()) {
			semanticErrorThrower = new SemanticErrorThrower(length.getLine(), "Length expression must have an array type");
			return false;
		}
		length.setEntryType(typeTable.getPrimitiveType(DataType.INT.getDescription()));
		return true;
	}

	@Override
	public Object visit(LiteralExpr literal) {
		literal.setEntryType(typeTable.getLiteralType(literal.getType().getDescription()));
		return true;
	}

	@Override
	public Object visit(LogicalUnaryOpExpr unaryOp) {
		if (!(Boolean)unaryOp.getOperand().accept(this))
			return false;
		Type type = unaryOp.getOperand().getEntryType();
		switch(unaryOp.getOperator()) {
			case LNEG:
				if (type.isBoolType()) {
					unaryOp.setEntryType(type);
					return true;
				}
				break;
			default:
				break;
		}
		
		semanticErrorThrower = new SemanticErrorThrower(unaryOp.getLine(), "Operand of unary operator has an invalid type");
		return false;
	}

	@Override
	public Object visit(LogicalBinaryOpExpr binaryOp) {
		if (!(Boolean)binaryOp.getFirstOperand().accept(this))
			return false;
			if (!(Boolean)binaryOp.getSecondOperand().accept(this))
			return false;
		Type typeFirst = binaryOp.getFirstOperand().getEntryType();
		Type typeSecond = binaryOp.getSecondOperand().getEntryType();

		String onWhat = "";
		String opType = "";
		switch(binaryOp.getOperator()) {
			case LAND:
			case LOR:
				if (typeFirst.isBoolType() && typeSecond.isBoolType()) {
					binaryOp.setEntryType(typeTable.getPrimitiveType(DataType.BOOLEAN.getDescription()));
					return true;
				}
				onWhat = "non-boolean";
				opType = "logical";
				break;
			case LT:
			case LTE:
			case GT:
			case GTE:
				if (typeFirst.isIntType() && typeSecond.isIntType()) {
					binaryOp.setEntryType(typeTable.getPrimitiveType(DataType.BOOLEAN.getDescription()));
					return true;
				}
				onWhat = "non-integer";
				opType = "logical";
				break;
			case EQUAL:
			case NEQUAL:
				if ((typeFirst.equals(typeSecond))
				|| ((typeFirst.isNullAssignable()) && (typeSecond.isNullType()))	
				|| ((typeFirst.isNullType()) && (typeSecond.isNullAssignable()))
				|| ((typeFirst.subtypeOf(typeSecond)))
				|| ((typeSecond.subtypeOf(typeFirst)))) {
					binaryOp.setEntryType(typeTable.getPrimitiveType(DataType.BOOLEAN.getDescription()));
					return true;
				}
				onWhat = "not-fitting";
				opType = "logical";
				break;
			default:
				break;
		}
		
		semanticErrorThrower = new SemanticErrorThrower(binaryOp.getLine(), String.format("Invalid %s binary op (%s) on %s expression",
				opType, binaryOp.getOperator().toString(), onWhat));
		return false;
	}


	@Override
	public Object visit(MathUnaryOpExpr unaryOp) {
		if (!(Boolean)unaryOp.getOperand().accept(this))
			return false;
		Type type = unaryOp.getOperand().getEntryType();
		switch(unaryOp.getOperator()) {
			case UMINUS:
				if (type.isIntType()) {
					unaryOp.setEntryType(type);
					return true;
				}
				break;
			default:
				break;
		}
		semanticErrorThrower = new SemanticErrorThrower(unaryOp.getLine(), "Operand of unary operator has an invalid type");
		return false;
	}

	@Override
	public Object visit(ExprBlock expressionBlock) {
		if (!(Boolean)expressionBlock.getExpr().accept(this))
			return false;
		expressionBlock.setEntryType(expressionBlock.getExpr().getEntryType());
		return true;
	}



	@Override
	public Object visit(MathBinaryOpExpr binaryOp) {
		if (!(Boolean)binaryOp.getFirstOperand().accept(this))
			return false;
			if (!(Boolean)binaryOp.getSecondOperand().accept(this))
			return false;
		Type typeFirst = binaryOp.getFirstOperand().getEntryType();
		Type typeSecond = binaryOp.getSecondOperand().getEntryType();

		String onWhat = "";
		String opType = "";
		switch(binaryOp.getOperator()) {
			case PLUS:
				if ((typeFirst.isIntType() && typeSecond.isIntType()) 
						|| (typeFirst.isStringType() && typeSecond.isStringType())) {
						binaryOp.setEntryType(typeFirst);
						return true;
				}
				onWhat = "non-integer or non-string";
				opType = "arithmetic";
				break;
			case MINUS:
			case MULTIPLY:
			case DIVIDE:
			case MOD:
				if (typeFirst.isIntType() && typeSecond.isIntType()) {
					binaryOp.setEntryType(typeFirst);
					return true;
				}
				onWhat = "non-integer";
				opType = "arithmetic";
				break;
				default:
					break;
		}
		semanticErrorThrower = new SemanticErrorThrower(binaryOp.getLine(), String.format("Invalid %s binary op (%s) on %s expression",
				opType, binaryOp.getOperator().toString(), onWhat));
		return false;
	}
}
