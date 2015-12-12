package semantic_analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ic.CompilerUtils;
import ast.*;
import type_table.*;
import type_table.MethodType.VirtualOrStatic;
import type_table.Type;

public class TypeTableConstructor implements Visitor {
	private ASTNode root;
	private Map<String, ClassType> classTypesMap;
	
	public TypeTableConstructor(ASTNode root) {
		this.root = root;
		classTypesMap = new HashMap<String, ClassType>();
	}
	
	public void run() {
		root.accept(this);
	}

	@Override
	public Object visit(Program program) {

        List<ICClass> classes = program.getClasses();

		for (ICClass icClass : classes) {
			classTypesMap.put(icClass.getName(), new ClassType(icClass));
		}
		
		for (ICClass icClass : classes) {
			icClass.accept(this);
		}
		
		for (ICClass icClass : classes) {
			if (icClass.hasSuperClass()) {
				ClassType currentClass = TypeTable.getClassType(icClass.getName());
				ClassType superClass = TypeTable.getClassType(icClass.getSuperClassName());
				currentClass.setSuperClass(superClass);
			}
		}
		
		return null;
	}

	@Override
	public Object visit(ICClass icClass) {
		Type currentType;
		ClassType classType = new ClassType(icClass);
		TypeTable.addClassType(classType);
		
		for (Field field : icClass.getFields()) {
			currentType = (Type)field.getType().accept(this);
			
			if (currentType instanceof ArrayType) {
				TypeTable.addArrayType((ArrayType)currentType);
			}
		}
		
		for (Method method : icClass.getMethods()) {
			method.accept(this);
		}
		
		return null;
	}

	@Override
	public Object visit(Field field) {
		return field.getType().accept(this);
	}

	@Override
	public Object visit(VirtualMethod method) {
		Type retType;
		List<Type> params = new ArrayList<>();
		
		Type currentType;
		for (Formal param : method.getFormals()) {
			currentType = (Type)param.accept(this);
			params.add(currentType);
			
			if (currentType instanceof ArrayType) {
				TypeTable.addArrayType((ArrayType)currentType);
			}
		}
		
		retType = (Type)method.getType().accept(this);
		
		if (retType instanceof ArrayType) {
			TypeTable.addArrayType((ArrayType)retType);
		}
		
		MethodType methodType = new MethodType(method.getName(), VirtualOrStatic.Virtual, params, retType);
		TypeTable.addMethodType(method.getName(), methodType);
		
		for (Stmt stmt : method.getStatements()) {
			stmt.accept(this);
		}
		
		return null;
	}

	@Override
	public Object visit(StaticMethod method) {
		Type retType = null;
		List<Type> params = new ArrayList<Type>();
		
		Type currentType = null;
		for (Formal param : method.getFormals()) {
			currentType = (Type)param.accept(this);
			params.add(currentType);
					
			if (currentType instanceof ArrayType) {
				TypeTable.addArrayType((ArrayType)currentType);
			}
		}
		
		retType = (Type)method.getType().accept(this);
		
		if (retType instanceof ArrayType) {
			TypeTable.addArrayType((ArrayType)retType);
		}
		
		MethodType methodType = new MethodType(VirtualOrStatic.Static, params, retType);
		
		//System.out.println("method.getName(): " + method.getName());
		//System.out.println("methodType.getName(): " + methodType.getName());
		
		TypeTable.addMethodType(method.getName(), methodType);
		
		for (Stmt stmt : method.getStatements()) {
			stmt.accept(this);
		}
		
		return null;
	}

	@Override
	public Object visit(LibraryMethod method) {
		Type retType = null;
		List<Type> params = new ArrayList<Type>();
		
		Type currentType = null;
		for (Formal param : method.getFormals()) {
			currentType = (Type)param.accept(this);
			params.add(currentType);
			
			if (currentType instanceof ArrayType) {
				TypeTable.addArrayType((ArrayType)currentType);
			}
		}
		
		retType = (Type)method.getType().accept(this);
		
		if (retType instanceof ArrayType) {
			TypeTable.addArrayType((ArrayType)retType);
		}
		
		MethodType methodType = new MethodType(VirtualOrStatic.Static, params, retType);
		
		//System.out.println("method.getName(): " + method.getName());
		//System.out.println("methodType.getName(): " + methodType.getName());
		
		TypeTable.addMethodType(method.toString(), methodType);
		
		for (Stmt stmt : method.getStatements()) {
			stmt.accept(this);
		}
		
		return null;
	}

	@Override
	public Object visit(Formal formal) {
		return formal.getType().accept(this);
	}

	@Override
	public Object visit(PrimitiveType type) {
		Type myType = CompilerUtils.primitiveTypeToMyType(type);
		return myType;
	}

	@Override
	public Object visit(UserType type) {
		String varTypeName = type.getName();
		return classTypesMap.get(varTypeName);
	}

	@Override
	public Object visit(AssignStmt assignment) {
		return null;
	}

	@Override
	public Object visit(CallStmt callStatement) {
		return null;
	}

	@Override
	public Object visit(ReturnStmt returnStatement) {
		return null;
	}

	@Override
	public Object visit(IfStmt ifStatement) {
		ifStatement.getStmt().accept(this);
		
		if (ifStatement.hasElse()) {
			ifStatement.getElseStmt().accept(this);
		}
		
		return null;
	}

	@Override
	public Object visit(WhileStmt whileStatement) {
		whileStatement.getStmt().accept(this);
		return null;
	}

	@Override
	public Object visit(BreakStmt breakStatement) {
		return null;
	}

	@Override
	public Object visit(ContinueStmt continueStatement) {
		return null;
	}

	@Override
	public Object visit(StmtBlock statementsBlock) {
		for (Stmt stmt : statementsBlock.getStatements()) {
			stmt.accept(this);
		}
		
		return null;
	}

	@Override
	public Object visit(LocalVarStmt localVariable) {
		return localVariable.getType().accept(this);
	}

	@Override
	public Object visit(VarLocationExpr location) {
		return null;
	}

	@Override
	public Object visit(ArrayLocationExpr location) {
		return null;
	}

	@Override
	public Object visit(StaticCallExpr call) {
		return null;
	}

	@Override
	public Object visit(VirtualCallExpr call) {
		return null;
	}

	@Override
	public Object visit(ThisExpr thisExpression) {
		return null;
	}

	@Override
	public Object visit(NewClassExpr newClass) {
		return null;
	}

	@Override
	public Object visit(NewArrayExpr newArray) {
		return null;
	}

	@Override
	public Object visit(LengthExpr length) {
		return null;
	}

	@Override
	public Object visit(MathBinaryOpExpr binaryOp) {
		return null;
	}

	@Override
	public Object visit(LogicalBinaryOpExpr binaryOp) {
		return null;
	}

	@Override
	public Object visit(MathUnaryOpExpr unaryOp) {
		return null;
	}

	@Override
	public Object visit(LogicalUnaryOpExpr unaryOp) {
		return null;
	}

	@Override
	public Object visit(LiteralExpr literal) {
		return CompilerUtils.literalTypeToMyType(literal.getType());
	}

	@Override
	public Object visit(ExprBlock expressionBlock) {
		return null;
	}
}