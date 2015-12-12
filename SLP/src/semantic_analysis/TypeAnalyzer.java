package semantic_analysis;

import java.util.ArrayList;
import java.util.List;
import ast.*;
import ic.BinaryOperator;
import ic.CompilerUtils;
import ic.UnaryOperator;
import type_table.ArrayType;
import type_table.ClassType;
import type_table.MethodType;
import type_table.MethodType.VirtualOrStatic;
import type_table.Type;
import type_table.TypeTable;

public class TypeAnalyzer implements PropagatingVisitor<SymbolTable, Object>,
		Tester {

	private final ASTNode root;
	private StringBuffer errors;

	public TypeAnalyzer(ASTNode root) {
		this.root = root;
	}

	@Override
	public void test() throws Exception {
		errors = new StringBuffer();
		root.accept(this, null);
	}

    public String getErrors() {
        return errors.toString();
    }

	@Override
	public boolean isAllGood() {
		return errors.length() == 0;
	}

	@Override
	public Object visit(Program program, SymbolTable context) throws Exception {
		for (ICClass icClass : program.getClasses()) {
			icClass.accept(this, program.enclosingScope());
		}

		return null;
	}

	@Override
	public Object visit(ICClass icClass, SymbolTable context) throws Exception {
		for (Field field : icClass.getFields()) {
			field.accept(this, icClass.enclosingScope());
		}

		for (Method method : icClass.getMethods()) {
			method.accept(this, icClass.enclosingScope());
		}

		return null;
	}

	@Override
	public Object visit(Field field, SymbolTable context) throws Exception {
		return context.look(field.getName()).getType();
	}

	@Override
	public Object visit(VirtualMethod method, SymbolTable context)
			throws Exception {
		for (Formal formal : method.getFormals()) {
			formal.accept(this, method.enclosingScope());
		}

		for (Stmt stmt : method.getStatements()) {
			stmt.accept(this, method.enclosingScope());
		}

		return null;
	}

	@Override
	public Object visit(StaticMethod method, SymbolTable context)
			throws Exception {
		Type methodType = (Type) method.getType().accept(this, context);

		for (Formal formal : method.getFormals()) {
			formal.accept(this, method.enclosingScope());
		}

		for (Stmt stmt : method.getStatements()) {
			stmt.accept(this, method.enclosingScope());
		}

		return null;
	}

	@Override
	public Object visit(LibraryMethod method, SymbolTable context)
			throws Exception {
		for (Formal formal : method.getFormals()) {
			formal.accept(this, method.enclosingScope());
		}

		for (Stmt stmt : method.getStatements()) {
			stmt.accept(this, method.enclosingScope());
		}

		return null;
	}

	@Override
	public Object visit(Formal formal, SymbolTable context) throws Exception {
		return formal.getType().accept(this, context);
	}

	@Override
	public Object visit(PrimitiveType type, SymbolTable context)
			throws Exception {
		return CompilerUtils.primitiveTypeToMyType(type);
	}

	@Override
	public Object visit(UserType type, SymbolTable context) throws Exception {
		return CompilerUtils.userTypeToMyType(type);
	}

	@Override
	public Object visit(AssignStmt assignment, SymbolTable context)
			throws Exception {
		//System.out.println(assignment.getLine() + "   -> Assignment");
		Expr rhs = assignment.getAssignVal();
		LocationExpr lhs = assignment.getVariable();

		Type expressionValue = (Type) rhs.accept(this, context);
		Type variable = (Type) lhs.accept(this, context);

		//System.out.println("    >>> lhs: " + variable);
		//System.out.println("    >>> rhs: " + expressionValue);

		if (!variable.subtypeOf(expressionValue)) {
			addConvertError(expressionValue, variable, assignment.getLine());
		}

		//System.out.println(assignment.getLine() + "   <- Assignment");
		return null;
	}

	@Override
	public Object visit(CallStmt callStatement, SymbolTable context)
			throws Exception {
		//System.out.println(callStatement.getLine() + "    -> CallStatement");
		return callStatement.getCall().accept(this, context);
	}

	@Override
	public Object visit(ReturnStmt returnStatement, SymbolTable context)
			throws Exception {
		//System.out.println(returnStatement.getLine() + "    -> ReturnStatement");

		Type retValue = null;
		SymbolTable currentTable = context;

		if (returnStatement.hasValue()) {
			retValue = (Type) returnStatement.getValue().accept(this, context);
		} else {
			retValue = TypeTable.voidType;
		}

		while (currentTable.getKind() != Kind.METHOD) {
			currentTable = currentTable.getParentSymbolTable();
		}

		MethodType methodType = (MethodType) currentTable.getType();

		return retValue.subtypeOf(methodType.getReturnType());
	}

	@Override
	public Object visit(IfStmt ifStatement, SymbolTable context) throws Exception {
		//System.out.println(ifStatement.getLine() + "- - - - - ifStatement - - - - -");
		Type ifExpressionType = (Type) ifStatement.getCond().accept(this,
				context);

		if (ifExpressionType != TypeTable.boolType) {
			addConvertError(ifExpressionType, TypeTable.boolType, ifStatement.getLine());
		}

		ifStatement.getStmt().accept(this, context);

		if (ifStatement.hasElse()) {
			//System.out.println(ifStatement.getElseOperation().getLine() +
			//"- - - - - elseStatement - - - - -");
			ifStatement.getElseStmt().accept(this, context);
		}

		// System.out.println(ifStatement.getLine() +
		// "e - - - - ifStatement - - - - e");
		return null;
	}

	@Override
	public Object visit(WhileStmt whileStatement, SymbolTable context)
			throws Exception {
		// System.out.println(whileStatement.getLine() +
		// "- - - - - whileStatement - - - - -");
		Type whileStmtType = (Type) whileStatement.getCond().accept(this,
				context);

		if (whileStmtType != TypeTable.boolType) {
			addConvertError(whileStmtType, TypeTable.boolType,
					whileStatement.getLine());
		}

		whileStatement.getStmt().accept(this, context);
		// System.out.println(whileStatement.getLine() +
		// "e - - - - whileStatement - - - - e");
		return null;
	}

	@Override
	public Object visit(BreakStmt breakStatement, SymbolTable context)
			throws Exception {
		// System.out.println(breakStatement.getLine() +
		// "    -> BreakStatement");
		return null;
	}

	@Override
	public Object visit(ContinueStmt continueStatement, SymbolTable context)
			throws Exception {
		// System.out.println(continueStatement.getLine() +
		// "    -> ContinueStatement");
		return null;
	}

	@Override
	public Object visit(StmtBlock statementsBlock, SymbolTable context)
			throws Exception {
		// System.out.println(statementsBlock.getLine() +
		// "  -> StatementsBlock");

		for (Stmt stmt : statementsBlock.getStatements()) {
			stmt.accept(this, statementsBlock.enclosingScope());
		}

		return null;
	}

	@Override
	public Object visit(LocalVarStmt localVariable, SymbolTable context)
			throws Exception {
		// System.out.println(localVariable.getLine() + "    -> LocalVariable");

		Type localVariableType = (Type) localVariable.getType().accept(this, context);

		if (localVariable.hasInitValue()) {
			Type initValueType = (Type) localVariable.getInitValue().accept(this, context);

            if (initValueType != null && localVariableType != null) {
                if (!initValueType.subtypeOf(localVariableType)) {
                    addConvertError(initValueType, localVariableType, localVariable.getLine());
                }
            }
		}

		return localVariableType;
	}

	@Override
	public Object visit(VarLocationExpr location, SymbolTable context)
			throws Exception {
		//System.out.println(location.getLine() + "    -> VariableLocation");
		
		if (location.isExternal()) {
			Type type = (Type) location.getLocation().accept(this, context);
			
			ClassType classType = TypeTable.getClassType(type.getName());
			SymbolTableRow fieldRow = classType.getClassAST().enclosingScope().lookup(location.getName());
			if (fieldRow != null) {
				return fieldRow.getType();
			}
		} else {
			SymbolTableRow localVariableSymbol = context.lookup(location.getName());
			if (localVariableSymbol != null) {
				return localVariableSymbol.getType();
			}
		}

		return null;
	}

	@Override
	public Object visit(ArrayLocationExpr location, SymbolTable context)
			throws Exception {
		// System.out.println(location.getLine() + "    -> ArrayLocation");
		Type temp;

		ArrayType arrayType = (ArrayType) location.getArray().accept(this,
				context);
		Type indexType = (Type) location.getIndex().accept(this, context);

		if (arrayType.getDimention() > 1) {
			temp = TypeTable.getArrayType(arrayType.toString()).getType();
		} else {
			temp = arrayType.getType();
		}

		if (indexType.subtypeOf(TypeTable.intType)) {
			// System.out.println("E :- e0:" + arrayType + "  E :- e1:" +
			// indexType +
			// " => E :- e0[e1]:" + temp);
			// System.out.println(location.getLine() + "    <- ArrayLocation");
			return temp;
		} else {
			addConvertError(indexType, TypeTable.intType, location.getLine());
			// System.out.println(location.getLine() + "    <- ArrayLocation");
		}

		return null;
	}

	@Override
	public Object visit(StaticCallExpr call, SymbolTable context) throws Exception {
		// System.out.println(call.getLine() + "    -> StaticCall");

		List<Type> argumentsTypeList = new ArrayList<Type>();
		// A.doSomething(6,5);
		for (Expr expr : call.getArguments()) {
			argumentsTypeList.add((Type) expr.accept(this, context));
		}

		ClassType classType = TypeTable.getClassType(call.getClassName());
		if (classType != null)
		{
			SymbolTableRow row = classType.getClassAST().enclosingScope().lookup(call.getName());
			if (row != null) {

	            MethodType methodType = TypeTable.getMethodType(row.getType().toString());
	            if (methodType != null) {
	                MethodType methodToTest = new MethodType(call.getName(),
	                        VirtualOrStatic.Static, argumentsTypeList, methodType.getReturnType());

	                if (methodType.subtypeOf(methodToTest)) {
	                    // System.out.println("All good!");
	                    return methodType.getReturnType();
	                } else {
	                    addError("SemanticError: " + call.getName() + methodType.getParamType() + " in " + call.getClassName() + " cannot be applied to " + call.getName() + argumentsTypeList, call.getLine());
	                }

	                return methodType.getReturnType();
	            }

	            addError("SemanticError: Non static method used", call.getLine());
	        } else {
	            addError("SemanticError: Method not found", call.getLine());
	        }
		}

        
		// System.err.println("Not Good!");
		return null;
	}

	@Override
	public Object visit(VirtualCallExpr call, SymbolTable context) throws Exception {
		// System.out.println(call.getLine() + "    -> VirtualCall");
		MethodType methodType = null;
		MethodType methodToTest;
		SymbolTableRow row;
		List<Type> argumentsTypeList = new ArrayList<>();

		for (Expr expr : call.getArguments()) {
			argumentsTypeList.add((Type) expr.accept(this, context));
		}
		
		if (call.isExternal()) {
            String typeName = ((Type) call.getLocation().accept(this, context)).getName();
            ClassType classType = TypeTable.getClassType(typeName);

			row = classType.getClassAST().enclosingScope().lookup(call.getName());

            if (row != null) {
                MethodType myType = TypeTable.getMethodType(row.getType().toString());
                if (myType != null && myType.isStatic() && !call.isExternal()) {
                    addError("SemanticError: Non-Static method '" + call.getName() + "' cannot be referenced from a static context", call.getLine());
                }
            } else {
                addError("SemanticError: No virtual function matched to " + call.getName(), call.getLine());
            }

		} else {
			row = context.lookup(call.getName());
		}
		
		if (row != null) {
            methodType = (MethodType) row.getType();

            methodToTest = new MethodType(call.getName(), VirtualOrStatic.Static, argumentsTypeList, methodType.getReturnType());

            if (methodType.subtypeOf(methodToTest)) {
                return methodType.getReturnType();
            }
		}

		addError("SemanticError: No virtual function matched to " + methodType, call.getLine());
		return null;
	}

	@Override
	public Object visit(ThisExpr thisExpression, SymbolTable context)
			throws Exception {
		SymbolTableRow row = context.lookup("$this");
		return row.getType();
	}

	@Override
	public Object visit(NewClassExpr newClass, SymbolTable context)
			throws Exception {

		return TypeTable.getClassType(newClass.getName());
	}

	@Override
	public Object visit(NewArrayExpr newArray, SymbolTable context)
			throws Exception {
		// System.out.println(newArray.getLine() + "    -> newArray");

		Type arrayType = null;
		Type sizeType = (Type) newArray.getSize().accept(this, context);

		if (sizeType.subtypeOf(TypeTable.intType)) {
			arrayType = (Type) newArray.getType().accept(this, context);
		} else {
            addError("SemanticError: incompatible types " + sizeType + " cannot be converted to int", newArray.getLine());
        }

		return arrayType;
	}

	@Override
	public Object visit(LengthExpr length, SymbolTable context) throws Exception {
		//System.out.println(length.getLine() + "    -> length");
		length.getArray().accept(this, context);

		return TypeTable.intType;
	}

	@Override
	public Object visit(MathBinaryOpExpr binaryOp, SymbolTable context)
			throws Exception {
		// System.out.println(binaryOp.getLine() +
		// "- - - - MathBinaryOp - - - -");
		Type result = null;
		Type lhsVal = (Type) binaryOp.getFirstOperand().accept(this, context);
		Type rhsVal = (Type) binaryOp.getSecondOperand().accept(this, context);
		BinaryOperator op = binaryOp.getOperator();

		if (lhsVal.subtypeOf(rhsVal)) {
			if (lhsVal.subtypeOf(TypeTable.stringType) && op == BinaryOperator.PLUS) {
				result = TypeTable.stringType;
				// System.out.println("E :- e0:" + lhsVal + "   E :- e1:" +
				// rhsVal + "  op:" + binaryOp.getOperator() +
				// " => E :- e0 op e1:" + result);
				// System.out.println("TRUE");
			} else if (lhsVal.subtypeOf(TypeTable.intType)) {
				result = TypeTable.intType;
				// System.out.println("E :- e0:" + lhsVal + "   E :- e1:" +
				// rhsVal + "  op:" + binaryOp.getOperator() +
				// " => E :- e0 op e1:" + result);
				// System.out.println("TRUE");
			} else {
				addConvertError(rhsVal, lhsVal, binaryOp.getLine());
				// System.out.println("FALSE");
			}
		} else {
			addConvertError(rhsVal, lhsVal, binaryOp.getLine());
			// System.out.println("FALSE");
		}

		// System.out.println(binaryOp.getLine() +
		// "e - - - MathBinaryOp - - - e");
		return result;
	}

	@Override
	public Object visit(LogicalBinaryOpExpr binaryOp, SymbolTable context)
			throws Exception {
		// System.out.println(binaryOp.getLine() +
		// "- - - - LogicalBinaryOp - - - -");
		Type result = null;
		
		Type lhsVal = (Type) binaryOp.getFirstOperand().accept(this, context);
		Type rhsVal = (Type) binaryOp.getSecondOperand().accept(this, context);
		BinaryOperator op = binaryOp.getOperator();

		//System.out.println("lhsVal: " + lhsVal);
		//System.out.println("rhsVal: " + rhsVal);
        //System.out.println("Op: " + op.getOperatorString());

		if (lhsVal.subtypeOf(rhsVal) || rhsVal.subtypeOf(lhsVal)) {
			if (op == BinaryOperator.EQUAL || op == BinaryOperator.NEQUAL) {
				// System.out.println("TRUE");
				result = TypeTable.boolType;
			} else {
				if (lhsVal.subtypeOf(TypeTable.intType)
						&& (op == BinaryOperator.GT || op == BinaryOperator.GTE
								|| op == BinaryOperator.LT || op == BinaryOperator.LTE)) {
					// System.out.println("TRUE");
					result = TypeTable.boolType;
				} else if (lhsVal.subtypeOf(TypeTable.boolType)
						&& (op == BinaryOperator.LAND || op == BinaryOperator.LOR)) {
					// System.out.println("TRUE");
					result = TypeTable.boolType;
				} else {
					addLogicalBinaryError(rhsVal, lhsVal, op,
							binaryOp.getLine());
					// System.out.println("FALSE");
				}
			}
		} else {
			addLogicalBinaryError(rhsVal, lhsVal, op, binaryOp.getLine());
			// System.out.println("FALSE");
		}

		// System.out.println(binaryOp.getLine() +
		// "e - - - LogicalBinaryOp - - - e");
		return result;
	}

	@Override
	public Object visit(MathUnaryOpExpr unaryOp, SymbolTable context)
			throws Exception {
		// System.out.println(unaryOp.getLine() +
		// "- - - - MathUnaryOp - - - -");
		UnaryOperator op = unaryOp.getOperator();
		Expr expr = unaryOp.getOperand();

		Type exprType = (Type) expr.accept(this, context);

		if (op != UnaryOperator.UMINUS) {
			addError("SemanticError: Encountered unexpected operator " + op,
					unaryOp.getLine());
			// throw new
			// RuntimeException("SemanticError: Encountered unexpected operator "
			// + op);
		}

		if (exprType.subtypeOf(TypeTable.intType)) {
			// System.out.println("E :- e:" + exprType + " => E :- -e:" +
			// exprType);
			// System.out.println("e - - - MathUnaryOp - - - e");
			return unaryOp.getOperand().accept(this, context);
		}
		addUnaryError(exprType, op, unaryOp.getLine());
		// System.out.println(unaryOp.getLine() +
		// "e - - - MathUnaryOp - - - e");
		return null;
	}

	@Override
	public Object visit(LogicalUnaryOpExpr unaryOp, SymbolTable context)
			throws Exception {
		// System.out.println(unaryOp.getLine() +
		// "- - - - LogicalUnaryOp - - - -");
		UnaryOperator op = unaryOp.getOperator();
		Expr expr = unaryOp.getOperand();

		Type exprType = (Type) expr.accept(this, context);

		if (op != UnaryOperator.LNEG) {
			addError("SemanticError: Encountered unexpected operator " + op,
					unaryOp.getLine());
			// throw new
			// RuntimeException("SemanticError: Encountered unexpected operator "
			// + op);
		}

		if (exprType.subtypeOf(TypeTable.boolType)) {
			// System.out.println("E :- e:" + exprType + " => E :- !e:" +
			// exprType);
			// System.out.println("e - - - LogicalUnaryOp - - - e");
			return unaryOp.getOperand().accept(this, context);
		}

		// System.out.println(unaryOp.getLine() +
        // "e - - - LogicalUnaryOp - - - e");
		addUnaryError(exprType, op, unaryOp.getLine());
		return null;
	}

	@Override
	public Object visit(LiteralExpr literal, SymbolTable context) throws Exception {
		return CompilerUtils.literalTypeToMyType(literal.getType());
	}

	@Override
	public Object visit(ExprBlock expressionBlock, SymbolTable context)
    throws Exception {
		return expressionBlock.getExpr().accept(this, context);
	}

	private void addUnaryError(Type lhsVal, UnaryOperator op, int line) {
		addError("SemanticError: The operator " + op.getOperatorString()
				+ " is undefined for the argument type(s) " + lhsVal, line);
	}

	private void addLogicalBinaryError(Type rhsVal, Type lhsVal, BinaryOperator op,
			int line) {
		addError("SemanticError: The operator " + op.getOperatorString()
				+ " is undefined for the argument type(s) " + lhsVal + ", "
				+ rhsVal, line);
	}

	private void addConvertError(Type first, Type second, int line) {
		addError("Type mismatch: cannot convert from " + first + " to "
				+ second, line);
	}

	private void addError(String message, int line) {
		errors.append(message + " " + printLineString(line) + "\n");
	}

	private String printLineString(int line) {
		return "at line " + line;
	}
}
