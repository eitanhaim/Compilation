package IC.lir.Targets;

public class FieldTarget extends Target {
	int tarValue;
	int offset;
	
	public FieldTarget(int tarValue, int offset) {
		this.tarValue = tarValue;
		this.offset = offset;
	}
	
	public int getTargetValue() {
		return this.tarValue;
	}
	
	public int getOffset() {
		return this.offset;
	}
}
