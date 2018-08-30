package bomberman;

public class TypeItem extends FieldType {

	public TypeItem() {
		super();
		type = Const.ITEM;
		solid = false;
		breakable = Const.NO_OBSTACLE;
		owner = Const.NO_OWNER;
		time = Const.NO_TIME;
		altTime = Const.NO_TIME;
		variant = 0;
		altVariant = 0;
		buffer = 0;
	}

	public TypeItem(int item) {
		super();
		type = Const.ITEM;
		solid = false;
		breakable = Const.NO_OBSTACLE;
		owner = Const.NO_OWNER;
		time = Const.NO_TIME;
		altTime = Const.NO_TIME;
		variant = item;
		altVariant = 0;
		buffer = 0;
	}

}
