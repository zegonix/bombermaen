package bomberman;

public class Coordinate {

	public int x;
	public int y;

	public Coordinate() {
		x = 0;
		y = 0;
	}

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate(int[] pos) {
		x = pos[0];
		y = pos[1];
	}

	public int[] getCoordinate() {
		int[] array = new int[2];
		array[0] = x;
		array[1] = y;
		return array;
	}

	public void setCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets coordinates with an array as input
	 * 
	 * @param pos
	 */
	public void setCoordinate(int[] pos) {
		x = pos[0];
		y = pos[1];
	}

}
