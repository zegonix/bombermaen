package bomberman;

public interface ModelListener {
	public abstract void gameover(int winner);

	public abstract void itempickup(int player, int itemNumber);
}
