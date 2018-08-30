package bomberman;

public class TypeEmpty extends FieldType {

	public TypeEmpty() {
		super();
		type = Const.EMPTY;
		solid = false;
		breakable = 2;
		owner = Const.NO_OWNER;
		time = Const.NO_TIME;
		altTime = Const.NO_TIME;
		variant = 0;
		altVariant = 0;
		buffer = 0;
	}

}
