package IC.lir.Targets;

public class RegisterTarget extends Target {
	int tarValue;
	
	public RegisterTarget(int tarValue) {
		this.tarValue = tarValue;
	}
	
	public int getTargetValue() {
		return this.tarValue;
	}
}
