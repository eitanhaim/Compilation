package IC.CodeGeneration;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssemblyMethod {
	
	private List<String> localVariables;
	private List<String> params;
	private int usedRegistersCount;
	private boolean isVirtual;
	
	public AssemblyMethod(List<String> params, List<String> localVariables, int usedRegistersCount, boolean isVirtual) {
		this.params = params;
		this.localVariables = localVariables;
		this.usedRegistersCount = usedRegistersCount;
		this.isVirtual = isVirtual;
	}

	public int getStackOffset(String operand) {
		// Checking if this is a virtual register operand:
		String virtualRegisterPattern = "R[0-9]+";
		Pattern pattern = Pattern.compile(virtualRegisterPattern);
		Matcher matcher = pattern.matcher(operand);
		int offset = 0;
		if (matcher.matches()) {
			int virtualRegNum = Integer.parseInt(operand.substring(1));	
			offset = -4 * virtualRegNum;
		}
		int isVirtual = this.isVirtual ? 1 : 0;
		if (params.contains(operand))
			offset = 8 + (isVirtual * 4) + params.indexOf(operand) * 4;
		if (localVariables.contains(operand))
			offset = -4 * (usedRegistersCount + localVariables.indexOf(operand) + 1);
		return offset;
	}
	
	public int getStackFrameSize() {
		return 4 * (usedRegistersCount + localVariables.size());
	}
}
