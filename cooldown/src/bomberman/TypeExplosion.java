package bomberman;

public class TypeExplosion extends FieldType {

	/* plain constructor do not use */
	public TypeExplosion() {
		super();
		type = Const.EXPLOSION;
		solid = false;
		breakable = 2;
		owner = Const.NO_OWNER;
		time = Const.EXP_TIME;
		altTime = Const.NO_TIME;
		variant = 0;
		altVariant = 0;
		buffer = 0;
	}

	public TypeExplosion(int owner, int variant) {
		super();
		type = Const.EXPLOSION;
		solid = false;
		breakable = 2;
		this.owner = owner;
		time = Const.EXP_TIME;
		altTime = Const.NO_TIME;
		this.variant = variant;
		altVariant = 0;
		buffer = 0;
	}

	public TypeExplosion(int owner, int variant, int item) {
		super();
		type = Const.EXPLOSION;
		solid = false;
		breakable = 2;
		this.owner = owner;
		time = Const.EXP_TIME;
		altTime = Const.NO_TIME;
		this.variant = variant;
		altVariant = 0;
		buffer = item;
	}

	@Override
	public void decrementTime() {
		if (time > 0) {
			time--;
		} else {
			time = Const.NO_TIME;
			variant = 0;
		}
		if (altTime > 0) {
			altTime--;
		} else {
			altTime = Const.NO_TIME;
			altVariant = 0;
		}
	}

}
