package IC.lir.Targets;

public class ArrayTarget extends Target {
	int arrayTarValue;
	int indexTarValue;
	
	public ArrayTarget(int arrayTarValue, int indexTarValue) {
		this.arrayTarValue = arrayTarValue;
		this.indexTarValue = indexTarValue;
	}
	
	public int getArrayTargetValue() {
		return this.arrayTarValue;
	}
	
	public int getIndexTargetValue() {
		return this.indexTarValue;
	}
}
