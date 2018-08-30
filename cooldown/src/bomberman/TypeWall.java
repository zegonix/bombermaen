package bomberman;

public class TypeWall extends FieldType {

	public TypeWall() {
		super();
		type = Const.WALL;
		solid = true;
		breakable = 0;
		owner = Const.NO_OWNER;
		time = Const.NO_TIME;
		altTime = Const.NO_TIME;
		variant = 0;
		altVariant = 0;
		buffer = 0;
	}

	public TypeWall(int variant) {
		super();
		type = Const.WALL;
		solid = true;
		breakable = 0;
		owner = Const.NO_OWNER;
		time = Const.NO_TIME;
		altTime = Const.NO_TIME;
		this.variant = variant;
		altVariant = 0;
		buffer = 0;
	}

}
