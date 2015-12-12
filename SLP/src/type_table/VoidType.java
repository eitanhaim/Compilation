package type_table;

public class VoidType extends Type {

	public VoidType() {
		super("void");
	}

	@Override
	public boolean subtypeOf(Type t) {
		return (t == this);
	}
}
