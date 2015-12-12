package type_table;

public class BoolType extends Type {

	public BoolType() {
		super("boolean");
	}
	
	@Override
	public boolean subtypeOf(Type t) {
		return (t == this);
	}
}
