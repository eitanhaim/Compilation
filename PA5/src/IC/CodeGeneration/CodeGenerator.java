package IC.CodeGeneration;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import IC.lir.ClassLayout;
import IC.lir.Registers;
import IC.lir.Instructions.*;

public class CodeGenerator implements IC.lir.Instructions.Visitor {
	
	private final String PROLOGUE_COMMENT = "prologue";
	private final String EPILOGUE_COMMENT = "epilogue";
	
	private String fileName;
	private List<Instruction> instructionsList;
	private Collection<ClassLayout> classLayouts;
	private List<StringLiteral> stringLiterals;
	private Map<String, AssemblyMethod> assemblyMethods;
	
	private StringBuffer assemblyStrBuffer;
	
	private String currentMethod;
	
	public CodeGenerator(String fileName, List<Instruction> instructionsList, 
			Collection<ClassLayout> classLayouts, List<StringLiteral> stringLiterals, 
			Map<String, AssemblyMethod> assemblyMethods) {
		this.fileName = fileName;
		this.instructionsList = instructionsList;
		this.classLayouts = classLayouts;
		this.stringLiterals = stringLiterals;
		this.assemblyMethods = assemblyMethods;
		this.assemblyStrBuffer = new StringBuffer("");
		
		this.currentMethod = null;
	}
	
	/**
	 * 
	 * @return a complete assembly code in string
	 */
	public String generateCode() {
		addAssemblyLine(String.format(".title	\"%s\"", fileName));
		dropLine();
		
		addAssemblyComment("global declarations");
		addAssemblyLine(".global " + ClassLayout.MAIN_METHOD_LABEL);
		dropLine();
		
		addAssemblyComment("data section");
		addAssemblyLine(".data");
		addAssemblyLine(".align 4");
		dropLine();
		for (ClassLayout cl : classLayouts) 
			addAssemblyLine(cl.toAssemblyLineString());
		dropLine();
		for (StringLiteral sl : stringLiterals) {
			addAssemblyLine(".int " + (sl.value.length() - 2));
			addAssemblyLine(String.format("%s:\t.string %s", sl.var, sl.value));
		}
		dropLine();
		
		addAssemblyComment("text (code) section");
		addAssemblyLine(".text");

		for (Instruction inst : this.instructionsList) {
			inst.accept(this);
		}
		
		return assemblyStrBuffer.toString();
	}
	
	@Override
	public void visit(MoveInstr instr) {
		addAssemblyLineWithComment("mov " + getOperandReference(instr.src) + ", %eax", instr.toString());
		addAssemblyLine("mov %eax, " + getOperandReference(instr.dst));
		dropLine();
	}

	@Override
	public void visit(BinOpInstr instr) {
		addAssemblyLineWithComment("mov " + getOperandReference(instr.dst) + ", %eax", instr.toString());
		switch (instr.op) {
			case MUL:
				addAssemblyLine("mov " + getOperandReference(instr.src) + ", %ebx");
				addAssemblyLine("mul %ebx");
				addAssemblyLine("mov %eax, " + getOperandReference(instr.dst));
				break;
			case DIV:
				addAssemblyLine("mov " + getOperandReference(instr.src) + ", %ebx");
				addAssemblyLine("mov $0, %edx");
				addAssemblyLine("div %ebx");
				addAssemblyLine("mov %eax, " + getOperandReference(instr.dst));
				break;
			case MOD:
				addAssemblyLine("mov " + getOperandReference(instr.src) + ", %ebx");
				addAssemblyLine("mov $0, %edx");
				addAssemblyLine("div %ebx");
				addAssemblyLine("mov %edx, " + getOperandReference(instr.dst));
				break;
			default:
				addAssemblyLine(String.format("%s %s, %s", instr.op, getOperandReference(instr.src), "%eax"));
				addAssemblyLine("mov %eax, " + getOperandReference(instr.dst));
				break;
					
		}
		dropLine();
	}

	@Override
	public void visit(CompareInstr instr) {
		addAssemblyLineWithComment("mov " + getOperandReference(instr.dst) + ", %eax", instr.toString());
		addAssemblyLine(String.format(
				"cmp %s, %s", getOperandReference(instr.src), "%eax"));
		dropLine();
	}

	@Override
	public void visit(UnaryOpInstr instr) {
		addAssemblyLineWithComment("mov " + getOperandReference(instr.dst) + ", %eax", instr.toString());
		addAssemblyLine(String.format("%s %s", instr.op, "%eax"));
		addAssemblyLine("mov %eax, " + getOperandReference(instr.dst));
		dropLine();
	}

	@Override
	public void visit(LabelInstr instr) {
		String labelName = instr.label.name;
		boolean isMethodLabel = (assemblyMethods.keySet().contains(labelName));
		if (isMethodLabel) {
			currentMethod = labelName;
			dropLine();
			addAssemblyComment("-------------------");
			addAssemblyLine(".align 4");
		}
		addAssemblyLine(instr.toString());
		if (isMethodLabel)
			generatePrologueStatements(currentMethod);
		dropLine();
	}

	@Override
	public void visit(MoveArrayInstr instr) {
		addAssemblyLineWithComment("mov " + getOperandReference(instr.base) + ", %eax", instr.toString());
		addAssemblyLine("mov " + getOperandReference(instr.offset) + ", %ebx");
		if(instr.isLoad) {
			addAssemblyLine("mov (%eax,%ebx,4), %ecx");
			addAssemblyLine("mov %ecx, " + getOperandReference(instr.mem));
		} else {
			addAssemblyLine("mov " + getOperandReference(instr.mem) + ", %ecx");
			addAssemblyLine("mov %ecx, (%eax,%ebx,4)");
		}
		dropLine();
	}

	@Override
	public void visit(MoveFieldInstr instr) {
		addAssemblyLineWithComment("mov "+getOperandReference(instr.base)+", %eax", instr.toString());
		addAssemblyLine("mov " + getOperandReference(instr.offset) + ", %ebx");
		if(instr.isLoad) {
			addAssemblyLine("mov (%eax,%ebx,4), %ecx");
			addAssemblyLine("mov %ecx, " + getOperandReference(instr.mem));
		} else {
			addAssemblyLine("mov " + getOperandReference(instr.mem) + ", %ecx");
			addAssemblyLine("mov %ecx, (%eax,%ebx,4)");
		}
		dropLine();
	}

	@Override
	public void visit(ArrayLengthInstr instr) {
		addAssemblyLineWithComment("mov " + getOperandReference(instr.arr) + ", %eax", instr.toString());
		addAssemblyLine("mov -4(%eax), %eax");
		addAssemblyLine("mov $4, %ebx");
		addAssemblyLine("mov $0, %edx");
		addAssemblyLine("div %ebx");
		addAssemblyLine("mov %eax, " + getOperandReference(instr.dst));
		dropLine();
	}

	@Override
	public void visit(JumpInstr instr) {
		addAssemblyLine("JMP "+instr.label);
		dropLine();
	}

	@Override
	public void visit(CondJumpInstr instr) {
		switch(instr.cond) {
		case True:
			addAssemblyLine("JZ "+instr.label);
			break;
		case False:
			addAssemblyLine("JNZ "+instr.label);
			break;
		case G:
			addAssemblyLine("JG "+instr.label);
			break;
		case GE:
			addAssemblyLine("JGE "+instr.label);
			break;
		case L:
			addAssemblyLine("JL "+instr.label);
			break;
		case LE:
			addAssemblyLine("JLE "+instr.label);
			break;
		}
		dropLine();
	}

	@Override
	public void visit(StaticCall instr) {
		addAssemblyComment(instr.toString());
		for (int i = instr.args.size() - 1; i >= 0; i--) {
			addAssemblyLine("mov " + getOperandReference(instr.args.get(i).op) + ", %eax");
			addAssemblyLine("push %eax");
		}
		addAssemblyLine("call " + instr.func.name);
		if (instr.args.size() > 0)
			addAssemblyLine("add $" + Integer.toString(instr.args.size() * 4) + ", %esp");
		if (!instr.dst.name.equals(Registers.DUMMY_REG))
			addAssemblyLine("mov %eax, " + getOperandReference(instr.dst));
		dropLine();
	}

	@Override
	public void visit(VirtualCall instr) {
		addAssemblyLineWithComment("mov " + getOperandReference(instr.obj) + ", %eax", instr.toString());
		for (int i = instr.args.size() - 1; i >= 0; i--) {
			addAssemblyLine("mov " + getOperandReference(instr.args.get(i).op) + ", %ebx");
			addAssemblyLine("push %ebx");
		}
		addAssemblyLine("push %eax");
		addAssemblyLine("mov (%eax), %eax");
		addAssemblyLine("call *" + ((((Immediate)instr.func).val) * 4) + "(%eax)");
		
		addAssemblyLine("add $" + Integer.toString((instr.args.size() + 1) * 4) + ", %esp");
		
		if (!instr.dst.name.equals(Registers.DUMMY_REG))
			addAssemblyLine("mov %eax, " + getOperandReference(instr.dst));
		dropLine();
	}

	@Override
	public void visit(LibraryCall instr) {
		addAssemblyComment(instr.toString());
		
		// because the length byte was multiplied by 4 when the input was created 
		// through the library function stoa, before calling the atos it needs to 
		// be restored back to it's original value.
		if (instr.func.name.equals("__atos")) {
			addAssemblyLineWithComment("mov " + getOperandReference(instr.args.get(0)) + ","
					+ " %ebx", "fixing atos input's length before the call");
			addAssemblyLine("mov -4(%ebx), %eax");
			addAssemblyLine("mov $4, %ecx");
			addAssemblyLine("mov $0, %edx");
			addAssemblyLine("div %ecx");
			addAssemblyLine("mov %eax, -4(%ebx)");
			addAssemblyLine("push %ebx");
		}
		
		for (int i = instr.args.size() - 1; i >= 0; i--) { //pushing parameters in reverse order
			addAssemblyLine("mov " + getOperandReference(instr.args.get(i)) + ", %eax");
			addAssemblyLine("push %eax");
		}
				
		addAssemblyLine("call " + instr.func.name); //calling the function
		if (instr.args.size() > 0)
			addAssemblyLine("add $" + Integer.toString(instr.args.size() * 4) + ", %esp"); //cutting back the stack
		
		
		if (!instr.dst.name.equals(Registers.DUMMY_REG)) //move result into destination register
			addAssemblyLine("mov %eax, " + getOperandReference(instr.dst));
		
		if (instr.func.name.equals("__atos")) {
			addAssemblyLineWithComment("pop %ebx", "fixing atos input's length back");
			addAssemblyLine("mov -4(%ebx), %eax");
			addAssemblyLine("mov $4, %ecx");
			addAssemblyLine("mul %ecx");
			addAssemblyLine("mov %eax, -4(%ebx)");
		}
		
		// Setting the length of the array memory byte to be multiplied by 4 to keep consistency 
		// with the length function which always assumes the array is of integers 
		// (and not chars like in this special case) and therefore divides the length by 4.
		if (instr.func.name.equals("__stoa")) {
			addAssemblyLineWithComment("mov %eax, %ebx", "fixing stoa output length to be artificially multiblied by 4");
			addAssemblyLine("mov -4(%ebx), %eax");
			addAssemblyLine("mov $4, %ecx");
			addAssemblyLine("mul %ecx");
			addAssemblyLine("mov %eax, -4(%ebx)");
		}
		
		dropLine();
	}

	@Override
	public void visit(ReturnInstr instr) {
		boolean isDummyReg = false;
		if (instr.dst instanceof Reg) {
			Reg reg = (Reg)instr.dst;
			if (reg.name.equals(Registers.DUMMY_REG))
				isDummyReg = true;
		}
		if (!isDummyReg)
			addAssemblyLineWithComment(
					"mov " + getOperandReference(instr.dst) + ", %eax", instr.toString());
		generateEpilogueStatements(currentMethod);
		dropLine();
	}
	
	private void addAssemblyLine(String line) {
		assemblyStrBuffer.append(line + '\n');
	}
	
	private void addAssemblyLineWithComment(String line, String comment) {
		assemblyStrBuffer.append(line + "\t# " + comment + '\n');
	}
	
	private void addAssemblyComment(String comment) {
		assemblyStrBuffer.append("# " + comment + '\n');
	}
	
	private void dropLine() {
		assemblyStrBuffer.append('\n');
	}
	
	private void generatePrologueStatements(String methodName) {
		addAssemblyLineWithComment("push %ebp", PROLOGUE_COMMENT);
		addAssemblyLine("mov %esp, %ebp");
		addAssemblyLine("sub $" + getCurrentAssemblyMethod().getStackFrameSize() + ", %esp");
	}
	
	private void generateEpilogueStatements(String methodName) {
		addAssemblyLineWithComment("mov %ebp, %esp", EPILOGUE_COMMENT);
		addAssemblyLine("pop %ebp");
		addAssemblyLine("ret");
	}

	private AssemblyMethod getCurrentAssemblyMethod() {
		AssemblyMethod currentAssemblyMethod = (AssemblyMethod)assemblyMethods.get(currentMethod);
		return  currentAssemblyMethod;
	}
	
	private String getOperandReference(Operand operand) {
		if (operand instanceof Immediate)
			return "$" + Integer.toString(((Immediate)operand).val);
		if (operand instanceof Memory) {
			Memory mem = (Memory)operand;
			
			if (mem.name.equals("this"))
				return Integer.toString(8) + "(%ebp)";
			
			if (containsLiteralVar(stringLiterals, mem.name))
				return "$" + mem.name;
		}
		if (operand instanceof Label) {
			Label label = (Label)operand;
			if (label.name.startsWith("_DV"))
				return "$" + label.name;
		}

		return Integer.toString(getCurrentAssemblyMethod().getStackOffset(operand.toString())) + "(%ebp)";
	}
	
	private Boolean containsLiteralVar(List<StringLiteral> stringLiterals, String var) {
		for (StringLiteral sl : stringLiterals)
			if (sl.var.equals(var))
				return true;
		return false;
	}
}
