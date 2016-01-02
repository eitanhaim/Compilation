package lir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import ast.*;
import ast.Program;
import ast.Visitor;
import ic.BinaryOperator;
import lir.instructions.*;
import symbol_table.SymbolKind;

public class TranslationVisitor implements Visitor{
	int target;
	StringLiterals stringLiterals;
	StringBuilder emitted;
	//class layouts
	Map<String, ClassLayout> classLayouts;
	//Instructions
	List<Instruction> instructions;
	// errors
	private boolean[] _hasErrors;
	private final String[] _errorStrings = {
			"Runtime error: Null pointer dereference!",
			"Runtime error: Array index out of bounds!",
			"Runtime error: Array allocation with negative array size!",
			"Runtime error: Division by zero!",
	};
	Map<String,Integer> arrs;

	private boolean assignmentCall = false;

	private Queue<ASTNode> nodeHandlingQueue;
	// registers
	//private Map<String, Integer> _registers;
	private Registers registers;

	//labels
	private Labels labelHandler;
	private Stack<String> _whileLabelStack;
	private Stack<String> _endWhileLabelStack;
	private String currentClassName;
	private Map<String, List<String>> methodFullNamesMap;	
	private SymbolKind currentMethodKind; // virtual or static
	private Boolean isMainMethod;
	
	public TranslationVisitor() {
		this.classLayouts = new HashMap<String,ClassLayout>();
		this.stringLiterals = new StringLiterals(); //there is also a StringLiteral class in the Instructions package. Replace?? (Answer: no, I fixed it to use that class, it's OK)
		this.emitted = new StringBuilder();
		_hasErrors = new boolean[4];
		this.instructions = new ArrayList<Instruction>();
		this.registers = new Registers();
		this.labelHandler = new Labels();

		this._whileLabelStack = new Stack<String>();
		this._endWhileLabelStack = new Stack<String>();

		this.methodFullNamesMap = new HashMap<String, List<String>>();
		this.nodeHandlingQueue = new LinkedList<ASTNode>();
		this.arrs = new HashMap<String,Integer>();
	}

	public String printInstructions() {
		StringBuffer output = new StringBuffer();
		
		//print string literals
		for(StringLiteral sl : stringLiterals.toStringLiteralList())
			output.append(sl.toString() + "\n");

		//print dispatch tables
		for(ClassLayout cl : classLayouts.values()) {
			output.append(cl + "\n");
		}

		//print instructions
		for (Instruction inst : instructions)
			output.append(inst.toString() + "\n");
		
		return output.toString();
	}

	public void translate(Program root)  {
		nodeHandlingQueue.add(root);
		ASTNode currentNode;
		while (!nodeHandlingQueue.isEmpty()) { 
			// BFS queue scan. The queue allows to scan all the classes, then all the fields and then all the methods.
			// The statements inside a method will be scanned "deeply" (DFS) and will not be added to the queue.
			currentNode = nodeHandlingQueue.poll();
			currentNode.accept(this);
		}
	}

	@Override
	public Object visit(Program program) {
		for (ICClass iccls : program.getClasses()) 
			nodeHandlingQueue.add(iccls);

		return null;
	}

	@Override
	public Object visit(ICClass icClass) {
		ClassLayout superCl = icClass.hasSuperClass() ? 
				classLayouts.get(icClass.getSuperClassName()) : null;
				ClassLayout cl = new ClassLayout(icClass.getName(), superCl);

				for (Field field : icClass.getFields()) {
					field.accept(this);
					cl.addField(field.getName());
				}

				for (Method method : icClass.getMethods()) {
					nodeHandlingQueue.add(method);
					cl.addMethod(method.getName());
					String methodFullName = cl.getMethodString(method.getName());
					this.methodFullNamesMap.put(methodFullName, generatMethodParamsList(method));
				}
				if(!icClass.getName().equals("Library"))
					classLayouts.put(icClass.getName(), cl);
				return null;
	}

	@Override
	public Object visit(Field field) {
		//System.out.println("1");
		return null;
	}

	@Override
	public Object visit(VirtualMethod method) {
		//System.out.println("2");
		return visitMethod(method);
	}

	@Override
	public Object visit(StaticMethod method) {
		//System.out.println("3");
		return visitMethod(method);
	}

	@Override
	public Object visit(LibraryMethod method) {
		return null;
	}

	private Object visitMethod(Method method)
	{
		isMainMethod = method.getName().equals("main");
		this.target = 1;
		this.currentMethodKind = method.getSymbolTable().getEntry(method.getName()).getKind();
		// add method label
		currentClassName = method.getSymbolTable().getId();
		String methodFullName = classLayouts.get(currentClassName).getMethodString(method.getName());
		//emit(fullMethodName+":");
		instructions.add(new LabelInstr(labelHandler.requestStr(methodFullName)));

		// add new registers for this method
		//    _registers = new HashMap<>();

		// add all statements
		for (Stmt stmt : method.getStatements()) {
			stmt.accept(this);
		}
		
		if (isMainMethod) {
			instructions.add(new LabelInstr(labelHandler.requestStr("_PROGRAM_END")));
			List<Operand> exitSinglOperandList = new ArrayList<Operand>();
			exitSinglOperandList.add(new Immediate(0));
			instructions.add(new LibraryCall(labelHandler.requestStr("__exit"), exitSinglOperandList, registers.request(-1)));
		}
		else if (!method.doesHaveFlowWithoutReturn())
			instructions.add(new ReturnInstr(registers.request(-1)));
		//System.out.println(method.getName()+" flow: "+method.doesHaveFlowWithoutReturn());
		isMainMethod = false;
		return null;
	}

	@Override
	public Object visit(Formal formal) {
		return null;
	}

	@Override
	public Object visit(PrimitiveType type) {
		return null;
	}

	@Override
	public Object visit(UserType type) {
		return null;
	}

	@Override
	public Object visit(AssignStmt assignment) {
		target++;
		assignment.getAssignVal().accept(this);
		target--;
		assignmentCall=true;
		assignment.getVariable().accept(this);
		assignmentCall=false;
		//instructions.add(currentAssignmentInstruction);
		//target--;
		return null;
	}


	@Override
	public Object visit(CallStmt callStatement) {
		callStatement.getCall().accept(this);
		return null;
	}

	@Override
	public Object visit(ReturnStmt returnStatement) {
		if (!isMainMethod) {
			if(returnStatement.hasValue()) {
				returnStatement.getValue().accept(this);
				instructions.add(new ReturnInstr(registers.request(target)));
			} else {
				instructions.add(new ReturnInstr(registers.request(-1)));
			}
		}
		return null;
	}

	@Override
	public Object visit(IfStmt ifStatement) {

		ifStatement.getCond().accept(this);
		labelHandler.increaseLabelsCounter();
		int ifLabel = labelHandler.getLabelsCounter();
		instructions.add(new CompareInstr(new Immediate(1), registers.request(target)));
		CommonLabels jumpingLabel = ifStatement.hasElse() 
				? CommonLabels.FALSE_LABEL : CommonLabels.END_LABEL;
		instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(jumpingLabel, ifLabel), Cond.False));
		ifStatement.getStmt().accept(this);
		if (ifStatement.hasElse()) {
			instructions.add(new JumpInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, ifLabel)));
			instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.FALSE_LABEL, ifLabel)));
			ifStatement.getElseStmt().accept(this);
		}
		instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, ifLabel)));

		return null;
	}

	@Override
	public Object visit(WhileStmt whileStatement) {
		labelHandler.increaseLabelsCounter();
		int whileLabel = labelHandler.getLabelsCounter();
		instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.TEST_LABEL, whileLabel)));
		whileStatement.getCond().accept(this);
		instructions.add(new CompareInstr(new Immediate(1), registers.request(target)));
		instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, whileLabel), Cond.False));

		this._whileLabelStack.add(CommonLabels.TEST_LABEL.toString()+whileLabel);
		this._endWhileLabelStack.add(CommonLabels.END_LABEL.toString()+whileLabel);
		whileStatement.getStmt().accept(this);
		this._whileLabelStack.pop();
		this._endWhileLabelStack.pop();
		instructions.add(new JumpInstr(labelHandler.innerLabelRequest(CommonLabels.TEST_LABEL, whileLabel)));
		instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, whileLabel)));
		return null;
	}

	@Override
	public Object visit(BreakStmt breakStatement) {
		instructions.add(new JumpInstr(labelHandler.requestStr(this._endWhileLabelStack.lastElement())));
		return null;
	}

	@Override
	public Object visit(ContinueStmt continueStatement) {
		instructions.add(new JumpInstr(labelHandler.requestStr(this._whileLabelStack.lastElement())));
		return null;
	}

	@Override
	public Object visit(StmtBlock statementsBlock) {

		for(Stmt stmnt : statementsBlock.getStatements())
		{
			stmnt.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(LocalVarStmt localVariable) {
		if (localVariable.hasInitValue()) {
			localVariable.getInitValue().accept(this);
			instructions.add(new MoveInstr(registers.request(target), 
					new Memory(localVariable.getSymbolEntry().getGlobalId())));
			target++;
		}
		return null;
	}

	@Override
	public Object visit(VarLocationExpr location) {
		if (location.isExternal()) {
			boolean tmp = assignmentCall;
			assignmentCall = false;
			location.getLocation().accept(this);
			assignmentCall = tmp;
			checkNullRefAndEmit(registers.request(target));
			String externalClsName = location.getLocation().getEntryType().toString();
			int fieldIndex = this.classLayouts.get(externalClsName).getFieldIndex(location.getName());
			if(assignmentCall)
				instructions.add(new MoveFieldInstr(registers.request(target), new Immediate(fieldIndex), registers.request(target+1), false)); 

			instructions.add(new MoveFieldInstr(registers.request(target), new Immediate(fieldIndex), registers.request(target), true));
		}
		else {
			if (location.getSymbolEntry().getKind() == SymbolKind.FIELD) {
				instructions.add(new MoveInstr(new Memory("this"), registers.request(target)));
				int fieldIndex = this.classLayouts.get(currentClassName).getFieldIndex(location.getName());
				if(assignmentCall)
					instructions.add(new MoveFieldInstr(registers.request(target), new Immediate(fieldIndex), registers.request(target+1), false)); 
				instructions.add(new MoveFieldInstr(registers.request(target), new Immediate(fieldIndex), registers.request(target), true));
			}
			else {
				Memory locationMemory = new Memory(location.getSymbolEntry().getGlobalId());
				if(assignmentCall)
					instructions.add(new MoveInstr(registers.request(target+1), locationMemory));
				instructions.add(new MoveInstr(locationMemory, registers.request(target)));
			}
		}
		return null;
	}

	@Override
	public Object visit(ArrayLocationExpr location) {

		for(Integer cl : arrs.values()) {
			System.out.println(cl);
		}

		int assignmentTarget = target;
		target+=3;

		boolean tmp = assignmentCall;
		assignmentCall = false;
		location.getArray().accept(this);
		target--;
		location.getIndex().accept(this);
		assignmentCall = tmp;
		
		checkNullRefAndEmit(registers.request(target + 1));
		// check if index > length 
		checkGTLengthAndEmit(registers.request(target),registers.request(target+1));

		// check if index < 0
		checkSizeGtZeroAndEmit(registers.request(target));

		
		if(assignmentCall)
			instructions.add(new MoveArrayInstr(
					registers.request(target+1), registers.request(target),
					registers.request(assignmentTarget+1), false));
		instructions.add(new MoveArrayInstr(registers.request(target+1), 
				registers.request(target), registers.request(/*--target*/assignmentTarget), true));

		target=assignmentTarget;



		return null;
	}

	private void checkGTLengthAndEmit(Reg index,Reg array)
	{
		target++;
		int labelCounter = labelHandler.increaseLabelsCounter();
		Reg currentSize=registers.request(++target);
		instructions.add(new ArrayLengthInstr(array, currentSize));	
		instructions.add(new CompareInstr(index, currentSize));
		instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter), Cond.G));
		instructions.add(new MoveInstr(new Memory("str"+stringLiterals.add(_errorStrings[1])), registers.request(target)));
		List<Operand> args = new ArrayList<Operand>();
		args.add(registers.request(target));
		instructions.add(new LibraryCall(labelHandler.requestStr("__print"), args, new Reg("Rdummy")));
		instructions.add(new JumpInstr(labelHandler.requestStr("_PROGRAM_END")));
		instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter)));

		target-=2;
		_hasErrors[1] = true;
	}

	@Override
	public Object visit(StaticCallExpr call) {

		int unusedMethodTarget = target;
		//library method call		
		if (call.getClassName().equals("Library")) {
			List<Operand> operands = new ArrayList<Operand>();

			for (Expr arg : call.getArguments()) {
				arg.accept(this);
				operands.add(registers.request(target));
				target++;
			}
			target = unusedMethodTarget;
			int retTrarget = call.getMethodType().getReturnType().isVoidType() ? -1 : target;
			instructions.add(new LibraryCall(labelHandler.requestStr("__" + call.getName()), operands, registers.request(retTrarget)));
		} // regular method call
		else {
			List<ParamOpPair> paramOpRegs = new ArrayList<ParamOpPair>();
			String staticCallMethodFullName = classLayouts.get(call.getClassName()).getMethodString(call.getName());
			List<String> methodParams = this.methodFullNamesMap.get(staticCallMethodFullName);
			int i = 0;
			for (Expr arg : call.getArguments()) {

				arg.accept(this);
				paramOpRegs.add(new ParamOpPair(new Memory(methodParams.get(i)), registers.request(target)));
				i++;
				target++;
			}
			target = unusedMethodTarget;
			int retTrarget = call.getMethodType().getReturnType().isVoidType() ? -1 : target;
			instructions.add(new lir.instructions.StaticCall(
					labelHandler.requestStr(staticCallMethodFullName), paramOpRegs, 
					registers.request(retTrarget)));
		}
		return null;
	}

	@Override
	public Object visit(VirtualCallExpr call) {
		if ((!call.isExternal()) && (currentMethodKind == SymbolKind.STATIC_METHOD)) {
			StaticCallExpr staticCall = new StaticCallExpr(call.getLine(),
					this.currentClassName, call.getName(), call.getArguments());
			staticCall.setEntryType(call.getEntryType());
			staticCall.setMethodType(call.getMethodType());
			return staticCall.accept(this);
		}
		if (call.isExternal()) {
			call.getLocation().accept(this);
		}
		else 
			instructions.add(new MoveInstr(new Memory("this"), registers.request(target)));
		
		int clsTarget = target;

		//check if the ref is null
		checkNullRefAndEmit(registers.request(target));

		String clsName = call.isExternal() ?
				call.getLocation().getEntryType().toString() : this.currentClassName;
				int unusedMethodTarget = target;
				target++;
				List<ParamOpPair> paramOpRegs = new ArrayList<ParamOpPair>();
				String virtualCallMethodFullName = classLayouts.get(clsName).getMethodString(call.getName());
				List<String> methodParams = this.methodFullNamesMap.get(virtualCallMethodFullName);
				int i = 0;
				for (Expr arg : call.getArguments()) {
					arg.accept(this);
					paramOpRegs.add(new ParamOpPair(new Memory(methodParams.get(i)), registers.request(target)));
					i++;
					target++;
				}
				Immediate funcIndex = new Immediate(classLayouts.get(clsName).getMethodIndex(call.getName()));
				target = unusedMethodTarget;
				int retTrarget = call.getMethodType().getReturnType().isVoidType() ? -1 : target;
				instructions.add(new lir.instructions.VirtualCall(
						registers.request(clsTarget), funcIndex, paramOpRegs, registers.request(retTrarget)));

				return null;
	}

	@Override
	public Object visit(ThisExpr thisExpression) {
		instructions.add(new MoveInstr(new Memory("this"), registers.request(target)));
		return true;
	}

	@Override
	public Object visit(NewClassExpr newClass) {
		List<Operand> args = new ArrayList<Operand>();
		args.add(new Immediate(classLayouts.get(newClass.getName()).getAllocatedSize()));
		instructions.add(new LibraryCall(labelHandler.requestStr("__allocateObject"), args , registers.request(target)));

		instructions.add(new MoveFieldInstr(registers.request(target), new Immediate(0), labelHandler.requestStr("_DV_"+newClass.getName()), false));
		return null;
	}

	@Override
	public Object visit(NewArrayExpr newArray) {

		List<Operand> args = new ArrayList<Operand>();
		target++;

		newArray.getSize().accept(this); 
		instructions.add(new BinOpInstr(new Immediate(4), registers.request(target), Operator.MUL)); //multiply size by 4
		args.add(registers.request(target--));

		// check if array size is non-negative
		checkSizeGtZeroAndEmit(args.get(0));	
		
		
		instructions.add(new LibraryCall(labelHandler.requestStr("__allocateArray"), args, registers.request(target)));
		
		return true;
	}

	private void checkSizeGtZeroAndEmit(Operand size)
	{

		int labelCounter = labelHandler.increaseLabelsCounter();
		instructions.add(new CompareInstr(new Immediate(0), size));
		instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter), Cond.GE));
		instructions.add(new MoveInstr(new Memory("str"+stringLiterals.add(_errorStrings[2])), registers.request(target)));
		List<Operand> args = new ArrayList<Operand>();
		args.add(registers.request(target));
		instructions.add(new LibraryCall(labelHandler.requestStr("__print"), args, new Reg("Rdummy")));
		instructions.add(new JumpInstr(labelHandler.requestStr("_PROGRAM_END")));
		instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter)));

		_hasErrors[2] = true;
	}

	@Override
	public Object visit(LengthExpr length) {
		target++;
		length.getArray().accept(this);
		target--;
		checkNullRefAndEmit(registers.request(target + 1));
		instructions.add(new ArrayLengthInstr(registers.request(target+1), registers.request(target)));

		return null;
	}

	private void checkNullRefAndEmit(Reg reg)
	{
		int labelCounter = labelHandler.increaseLabelsCounter();
		instructions.add(new CompareInstr(new Immediate(0), reg));
		instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter), Cond.False));
		instructions.add(new MoveInstr(new Memory("str"+stringLiterals.add(_errorStrings[0])), registers.request(target)));
		List<Operand> args = new ArrayList<Operand>();
		args.add(registers.request(target));
		instructions.add(new LibraryCall(labelHandler.requestStr("__print"), args , new Reg("Rdummy")));
		instructions.add(new JumpInstr(labelHandler.requestStr("_PROGRAM_END")));
		instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter)));

		_hasErrors[0] = true;
	}


	@Override
	public Object visit(MathBinaryOpExpr binaryOp) {
		binaryOp.getFirstOperand().accept(this);
		target++;
		binaryOp.getSecondOperand().accept(this);
		//String instruction="";
		Operator op;
		switch(binaryOp.getOperator()) {
		case PLUS:
			if(binaryOp.getFirstOperand().getEntryType().isStringType() && binaryOp.getSecondOperand().getEntryType().isStringType()){
				List<Operand> args = new ArrayList<Operand>();
				args.add(registers.request(target-1));
				args.add(registers.request(target));
				instructions.add(new LibraryCall(labelHandler.requestStr("__stringCat"), args, registers.request(--target)));
				return true;
			} else {
				//instruction="Add";
				op = Operator.ADD;
				break;
			}
		case MINUS:
			//instruction="Sub";
			op = Operator.SUB;
			break;
		case DIVIDE:
			//instruction="Div";
			op = Operator.DIV;
			break;
		case MULTIPLY:
			//instruction="Mul";
			op = Operator.MUL;
			break;
		case MOD:
			//instruction="Mod";
			op = Operator.MOD;
			break;
		default:
			return false;
		}
		//emit(instruction+" R"+target+",R"+(--target));
		Reg leftReg=registers.request(target);
		Reg rightReg=registers.request(--target);

		//zero division check
		if(binaryOp.getOperator()==BinaryOperator.DIVIDE || binaryOp.getOperator()==BinaryOperator.MOD)
			divisionCheck(leftReg);

		instructions.add(new BinOpInstr(leftReg, rightReg, op));
		return true;
	}

	@Override
	public Object visit(LogicalBinaryOpExpr binaryOp) {

		//target++;
		//binaryOp.getSecondOperand().accept(this);
		int labelCounter =labelHandler.increaseLabelsCounter();
		switch(binaryOp.getOperator()) {
		case GT:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare R,"+target+",R"+(--target));
			instructions.add(new CompareInstr(registers.request(--target), registers.request(target+1)));
			//emit("JumpG _true_label"+labels);
			instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(CommonLabels.TRUE_LABEL,labelCounter), Cond.G));
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			//emit("Jump _end_label"+labels);
			instructions.add(new JumpInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL,labelCounter)));
			instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.TRUE_LABEL,labelCounter)));
			instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
			//emit(CommonLabels.END_LABEL.toString()+labels);
			instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL,labelCounter)));
			break;
		case GTE:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare R,"+target+",R"+(--target));
			instructions.add(new CompareInstr(registers.request(--target), registers.request(target+1)));
			//emit("JumpGE _true_label"+labels);
			instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(CommonLabels.TRUE_LABEL,labelCounter), Cond.GE));
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			//emit("Jump _end_label"+labels);
			instructions.add(new JumpInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL,labelCounter)));
			//emit(CommonLabels.TRUE_LABEL.toString()+labels);
			instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.TRUE_LABEL, labelCounter)));
			//emit("Move 1,R"+target);
			instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
			//emit(CommonLabels.END_LABEL.toString()+labels);
			instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter)));
			break;
		case EQUAL:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare R,"+target+",R"+(--target));
			instructions.add(new CompareInstr(registers.request(--target), registers.request(target+1)));
			//emit("JumpFalse _true_label"+labels);
			instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(CommonLabels.TRUE_LABEL, labelCounter), Cond.True));
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			//emit("Jump _end_label"+labels);
			instructions.add(new JumpInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter)));
			//emit(CommonLabels.TRUE_LABEL.toString()+labels);
			instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.TRUE_LABEL, labelCounter)));
			//emit("Move 1,R"+target);
			instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
			//emit(CommonLabels.END_LABEL.toString()+labels);
			instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter)));
			break;
		case NEQUAL:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare R,"+target+",R"+(--target));
			instructions.add(new CompareInstr(registers.request(--target), registers.request(target+1)));
			//emit("JumpFalse _false_label"+labels);
			instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(CommonLabels.TRUE_LABEL, labelCounter), Cond.False));
			//emit("Move 1,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			//emit("Jump _end_label"+labels);
			instructions.add(new JumpInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter)));
			//emit(CommonLabels.FALSE_LABEL.toString()+labels);
			instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.TRUE_LABEL, labelCounter)));
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
			//emit(CommonLabels.END_LABEL.toString()+labels);
			instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter)));
			break;
		case LT:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare R,"+target+",R"+(--target));
			instructions.add(new CompareInstr(registers.request(--target), registers.request(target+1)));
			//emit("JumpL _true_label"+labels);
			instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(CommonLabels.TRUE_LABEL, labelCounter), Cond.L));
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			//emit("Jump _end_label"+labels);
			instructions.add(new JumpInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter)));
			//emit(CommonLabels.TRUE_LABEL.toString()+labels);
			instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.TRUE_LABEL, labelCounter)));
			//emit("Move 1,R"+target);
			instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
			//emit(CommonLabels.END_LABEL.toString()+labels);
			instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter)));
			break;
		case LTE:
			binaryOp.getSecondOperand().accept(this);
			target++;
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare R,"+target+",R"+(--target));
			instructions.add(new CompareInstr(registers.request(--target), registers.request(target+1)));
			//emit("JumpLE _true_label"+labels);
			instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(CommonLabels.TRUE_LABEL, labelCounter), Cond.LE));
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			//emit("Jump _end_label"+labels);
			instructions.add(new JumpInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter)));;
			//emit(CommonLabels.TRUE_LABEL.toString()+labels);
			instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.TRUE_LABEL, labelCounter)));
			//emit("Move 1,R"+target);
			instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
			//emit(CommonLabels.END_LABEL.toString()+labels);
			instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter)));
			break;
		case LAND:
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare 0,R"+target);
			//emit("JumpTrue _end_label"+labels);
			instructions.add(new CompareInstr(new Immediate(0), registers.request(target)));
			instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter), Cond.True));
			target++;
			binaryOp.getSecondOperand().accept(this);
			//emit("And R"+target+",R"+(--target));
			//emit(CommonLabels.END_LABEL.toString()+labels);
			instructions.add(new BinOpInstr(registers.request(--target), registers.request(target+1), Operator.AND));
			instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter)));
			break;
		case LOR:
			binaryOp.getFirstOperand().accept(this);
			//emit("Compare 1,R"+target);
			//emit("JumpTrue _end_label"+labels);
			instructions.add(new CompareInstr(new Immediate(1), registers.request(target)));
			instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter), Cond.True));
			binaryOp.getSecondOperand().accept(this);
			//emit("Or R"+target+",R"+(--target));
			//emit(CommonLabels.END_LABEL.toString()+labels);
			
			instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter)));
			break;
		default:

		}

		return null;
	}

	@Override
	public Object visit(MathUnaryOpExpr unaryOp) {
		target++;
		unaryOp.getOperand().accept(this);
		target--;
		instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
		instructions.add(new BinOpInstr(registers.request(target+1), registers.request(target), Operator.SUB));
		return null;
	}

	@Override
	public Object visit(LogicalUnaryOpExpr unaryOp) {
		target++;
		unaryOp.getOperand().accept(this);
		target--;
		//emit("Move 1,R"+target);
		//emit("Sub R"+(target+1)+",R"+target);
		instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
		instructions.add(new BinOpInstr(registers.request(target+1), registers.request(target), Operator.SUB));
		return null;
	}

	@Override
	public Object visit(LiteralExpr literal) {
		switch(literal.getType()) {
		case STRING:
			//emit("Move str"+stringLiterals.add((String)literal.getValue())+",R"+target); 
			instructions.add(new MoveInstr(new Memory("str"+stringLiterals.add((String)literal.getValue())), registers.request(target)));
			break;
		case INTEGER:
			//emit("Move "+((Integer)literal.getValue())+",R"+target);
			instructions.add(new MoveInstr(new Immediate((Integer)literal.getValue()), registers.request(target)));
			break;
		case TRUE:
			//emit("Move 1,R"+target);
			instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
			break;
		case FALSE:
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			break;
		case NULL:
			//emit("Move 0,R"+target);
			instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
			break;
		default:

		}
		return null; 
	}

	@Override
	public Object visit(ExprBlock expressionBlock) {
		expressionBlock.getExpr().accept(this);

		return null;
	}

	public void emit(String s) {
		emitted.append(s+"\n");
	}

	public String getEmissionString()
	{
		return this.emitted.toString();
	}

	private List<String> generatMethodParamsList(Method method) {
		List<String> output = new ArrayList<String>();
		for (Formal formal : method.getFormals()) 
			output.add(formal.getSymbolEntry().getGlobalId());
		return output;
	}

	private void divisionCheck(Reg reg) {
		_hasErrors[3] = true;
		//        target+=2;
		//        instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
		//		instructions.add(new MoveInstr(reg, registers.request(++target)));
		//		instructions.add(new CompareInstr(registers.request(target), registers.request(--target)));
		//		instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(CommonLabels.TRUE_LABEL), Cond.False));
		//		instructions.add(new MoveInstr(new Immediate(0), registers.request(target)));
		//		instructions.add(new JumpInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL)));
		//		instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.TRUE_LABEL)));
		//		instructions.add(new MoveInstr(new Immediate(1), registers.request(target)));
		//		
		//		instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL)));
		//
		//		labelHandler.increaseLabelsCounter();
		//		int ifLabel = labelHandler.getLabelsCounter();
		//		instructions.add(new CompareInstr(new Immediate(1), registers.request(target)));
		//		CommonLabels jumpingLabel = CommonLabels.END_LABEL;
		//		
		//		instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(jumpingLabel, ifLabel), Cond.False));
		//		
		//		instructions.add(new MoveInstr(new Memory("str"+stringLiterals.add(_errorStrings[3])), registers.request(target)));
		//		//List<ParamOpPair> paramOpRegs = new ArrayList<ParamOpPair>();
		//		//paramOpRegs.add(new ParamOpPair(new Memory("s"), registers.request(target)));
		//		//instructions.add(new IC.lir.Instructions.StaticCall
		//		//		(labelHandler.requestStr("Library_print"), paramOpRegs, registers.request(-1)));
		//		List<Operand> args = new ArrayList<Operand>();
		//		args.add(registers.request(target));
		//		instructions.add(new LibraryCall(labelHandler.requestStr("__print"), args , new Reg("Rdummy")));
		//		
		//		instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, ifLabel)));

		int labelCounter = labelHandler.increaseLabelsCounter();
		instructions.add(new CompareInstr(new Immediate(0), reg));
		instructions.add(new CondJumpInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter), Cond.False));
		instructions.add(new MoveInstr(new Memory("str"+stringLiterals.add(_errorStrings[3])), registers.request(target)));
		List<Operand> args = new ArrayList<Operand>();
		args.add(registers.request(target));
		instructions.add(new LibraryCall(labelHandler.requestStr("__print"), args , new Reg("Rdummy")));
		instructions.add(new JumpInstr(labelHandler.requestStr("_PROGRAM_END")));
		instructions.add(new LabelInstr(labelHandler.innerLabelRequest(CommonLabels.END_LABEL, labelCounter)));

	}



}
