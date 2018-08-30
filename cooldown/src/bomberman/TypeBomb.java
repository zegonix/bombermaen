package bomberman;

public class TypeBomb extends FieldType {

	/* plain constructor do not use */
	public TypeBomb() {
		super();
		type = Const.BOMB;
		solid = true;
		breakable = 2;
		owner = Const.NO_OWNER;
		time = Const.BOMB_TIME;
		altTime = Const.NO_TIME;
		variant = Const.NORMAL_BOMB;
		altVariant = 0;
		buffer = 0;
	}

	public TypeBomb(int owner) {
		super();
		type = Const.BOMB;
		solid = true;
		breakable = 2;
		this.owner = owner;
		time = Const.BOMB_TIME;
		altTime = Const.NO_TIME;
		variant = Const.NORMAL_BOMB;
		altVariant = 0;
		buffer = 0;
	}

	public TypeBomb(int owner, boolean exploding) {
		super();
		type = Const.BOMB;
		solid = true;
		breakable = 2;
		this.owner = owner;
		if (exploding) {
			time = 1;
		} else {
			time = Const.BOMB_TIME;
		}
		altTime = Const.NO_TIME;
		variant = Const.NORMAL_BOMB;
		altVariant = 0;
		buffer = 0;
	}

	public TypeBomb(int owner, int bombtype) {
		super();
		type = Const.BOMB;
		solid = true;
		breakable = 2;
		this.owner = owner;
		switch (bombtype) {
		case 1:
			time = Const.BOMB_TIME;
			variant = Const.NORMAL_BOMB;
			break;
		case 2:
			time = Const.NO_TIME;
			variant = Const.TRIGGER_BOMB;
			break;
		default:
			break;
		}
		altTime = Const.NO_TIME;
		altVariant = 0;
		buffer = 0;
	}

}
