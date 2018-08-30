package bomberman;

/**
 * Class to describe a fields properties
 * 
 * @author jonas
 * @date 05.05.17
 *
 */
public abstract class FieldType {

	protected String type; // type name
	protected boolean solid; // declares whether a player can move through or
								// not
	protected int breakable; // 0: blocks explosions, 1: gets destroyed but
								// blocks explosions, 2: explosions pass through
	protected int owner; // defines to which player the field belongs
	protected int time;
	protected int altTime; // second time is needed if two explosions overlap
	protected int variant;
	protected int altVariant; // second variant is needed if two explosions
								// overlap
	protected int buffer = 0; // multiuse variable

	/**
	 * constructor of FieldType, meant to be overwritten by subclasses
	 */
	public FieldType() {

		type = Const.VOID;
		solid = false;
		breakable = Const.UNBREAKABLE;
		owner = Const.NO_OWNER;
		time = Const.NO_TIME;
		altTime = Const.NO_TIME;
		variant = 0;
		altVariant = 0;
		buffer = 0;

	}

	/* methods to read the objects values */
	public String getType() {
		return type;
	}

	public boolean isSolid() {
		return solid;
	}

	public int isBreakable() {
		return breakable;
	}

	public int getOwner() {
		return owner;
	}

	public int getTime() {
		return time;
	}

	public int getVariant() {
		return (variant | altVariant);
	}

	public int getBuffer() {
		return buffer;
	}

	/**
	 * for explosions only!!
	 * 
	 * @param variant
	 */
	public void addExpVariant(int variant) {
		owner = Const.NO_OWNER;
		if (variant == this.variant) {
			this.time = Const.EXP_TIME;
		} else if (variant == this.altVariant) {
			int temp = this.altVariant;
			this.altVariant = this.variant;
			this.altVariant = temp;
			this.altTime = this.time;
			this.time = Const.EXP_TIME;
		} else {
			altVariant = this.variant;
			altTime = time;
			this.variant = variant;
			time = Const.EXP_TIME;
		}
	}

	/* methods to set the objects values */
	public int setSolid(boolean value) {
		solid = value;
		return 0;
	}

	public int setBreakable(int value) {
		if (value >= 0 && value <= 2) {
			breakable = value;
			return 0;
		} else {
			return -1;
		}
	}

	public int setOwner(int value) {
		owner = value;
		return 0;
	}

	public int setTime(int value) {
		time = value;
		return 0;
	}

	public int setAltTime(int value) {
		altTime = value;
		return 0;
	}

	public int setVariant(int value) {
		variant = value;
		return 0;
	}

	public int setAltVariant(int value) {
		altVariant = value;
		return 0;
	}

	public void decrementTime() {
		if (time > 0) {
			time--;
		} else {
			time = Const.NO_TIME;
		}
		if (altTime > 0) {
			altTime--;
		} else {
			altTime = Const.NO_TIME;
		}
	}

	public int setBuffer(int value) {
		buffer = value;
		return 0;
	}

}
