package bomberman;

public class TypeCrate extends FieldType {

	public TypeCrate() {
		super();
		type = Const.CRATE;
		solid = true;
		breakable = 1;
		owner = Const.NO_OWNER;
		time = Const.NO_TIME;
		altTime = Const.NO_TIME;
		variant = 0;
		altVariant = 0;
		buffer = 0;
	}

	public TypeCrate(int variant) {
		super();
		type = Const.CRATE;
		solid = true;
		breakable = 1;
		owner = Const.NO_OWNER;
		time = Const.NO_TIME;
		altTime = Const.NO_TIME;
		this.variant = variant;
		altVariant = 0;
		buffer = 0;
	}

}
