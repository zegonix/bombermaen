package bomberman;

public class TypePlayer extends FieldType {

	/* plain constructor do not use */
	public TypePlayer() {
		super();
		type = Const.PLAYER;
		solid = true;
		breakable = 2;
		owner = Const.NO_OWNER;
		time = Const.NO_TIME;
		altTime = Const.NO_TIME;
		variant = 0;
		altVariant = 0;
		buffer = 0;
	}

	public TypePlayer(int owner) {
		super();
		type = Const.PLAYER;
		solid = true;
		breakable = 2;
		this.owner = owner;
		time = Const.NO_TIME;
		altTime = Const.NO_TIME;
		variant = 0;
		altVariant = 0;
		buffer = 0;
	}

}
