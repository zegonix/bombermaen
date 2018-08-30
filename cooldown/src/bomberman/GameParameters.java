package bomberman;

public class GameParameters {

	public String map;
	public int size;
	public int bombcount;
	public int bombstrength;
	public int playerselection;

	public GameParameters() {
		map = "default";
		size = 15;
		bombcount = 1;
		bombstrength = 1;
		playerselection = 3; // <=> 0b0011
	}

	public GameParameters(String map, int size, int bombcount, int bombstrength, int playerselection) {
		this.map = map;
		this.size = size;
		this.bombcount = bombcount;
		this.bombstrength = bombstrength;
		this.playerselection = playerselection;
	}

}
