package bomberman;

import java.util.Random;

public final class Const {

	// states enum
	static enum gameState {
		VOID, MENU, OPTIONS, RUNNING, PAUSED
	};

	// random number generator
	static Random random = new Random(System.currentTimeMillis());

	static int getRandom(int min, int max) {
		return min + (int) (random.nextDouble() * (max - min));
	}

	// direction constants
	final static int NORTH = 0;
	final static int EAST = 1;
	final static int SOUTH = 2;
	final static int WEST = 3;

	// time constants
	final static int NO_TIME = -1;
	final static int BOMB_TIME = 40;
	final static int EXP_TIME = 20;

	// type names
	final static String VOID = "Void";
	final static String EMPTY = "Empty";
	final static String WALL = "Wall";
	final static String CRATE = "Crate";
	final static String PLAYER = "Player";
	final static String BOMB = "Bomb";
	// final static String PLAYER_AND_BOMB = "Player & Bomb";
	final static String EXPLOSION = "Explosion";
	final static String ITEM = "Item";

	// owner constants
	final static int NO_OWNER = -1;
	final static int PLAYER1 = 0;
	final static int PLAYER2 = 1;
	final static int PLAYER3 = 2;
	final static int PLAYER4 = 3;

	// bomb types
	final static int NORMAL_BOMB = 1;
	final static int TRIGGER_BOMB = 2;

	// crate/item variants
	final static int NO_ITEM = 0;
	final static int ITEM_BOMBSIZE = 1;
	final static int ITEM_BOMB_PLUS = 2;
	final static int ITEM_TRIGGER = 4;
	final static int ITEM_SHIELD = 5;
	final static int ITEM_EMP = 6;

	// important explosion variants
	final static int EXP_END_NORTH = 0b0001;
	final static int EXP_END_EAST = 0b0010;
	final static int EXP_END_SOUTH = 0b0100;
	final static int EXP_END_WEST = 0b1000;
	final static int EXP_VERTICAL = 0b0101;
	final static int EXP_HORIZONTAL = 0b1010;

	// levels of breakability
	final static int UNBREAKABLE = 0;
	final static int DESTRUCTABLE = 1;
	final static int NO_OBSTACLE = 2;

}
