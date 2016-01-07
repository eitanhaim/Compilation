package IC.lir.Targets;

import IC.lir.Instructions.Memory;

public class MemoryTarget extends Target {
	Memory memTarget;
	public MemoryTarget(Memory memTarget) {
		this.memTarget = memTarget;
	}
	
	public Memory getMemoryTarget() {
		return this.memTarget;
	}
	
}
