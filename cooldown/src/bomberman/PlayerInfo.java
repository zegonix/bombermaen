package bomberman;

public class PlayerInfo {

	public Coordinate pos;
	public boolean alive;
	public int bombtype;
	public int bombsize; // explosion range
	public int bombnum; // number of bombs the player may place

	public PlayerInfo() {
		pos = new Coordinate(0, 0);
		alive = false;
		bombtype = Const.NORMAL_BOMB;
		bombsize = 2;
		bombnum = 1;
	}

}
