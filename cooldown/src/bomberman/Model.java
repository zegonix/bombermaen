package bomberman;

import java.util.Random;

public class Model {

	// -- Game Parameters
	private final int chanceBombsize = 12;
	private final int chanceBombPlus = 12;
	private final int chanceTrigger = 0;

	// -- Variables
	private ModelListener listener;
	private int rows;
	private FieldType[][] arena;
	private final FieldType empty = new TypeEmpty();
	private PlayerInfo[] stats = { new PlayerInfo(), new PlayerInfo(), new PlayerInfo(), new PlayerInfo() };

	// -- Sounds
	// File soundExplosion = new File("src\\res\\explosion.wav");
	// File soundPlaceBomb = new File("src\\res\\placeBomb.wav");
	// File soundGameStart = new File("src\\res\\gameStart.wav");

	// Constructor
	public Model(int rows, int bombcount, int bombstrength) {
		this.rows = rows;
		arena = new FieldType[rows][rows];
		buildArena(3);

		for (int i = 0; i < 4; i++) {
			stats[i].bombnum = bombcount;
			stats[i].bombsize = bombstrength;
		}
	}

	public FieldType[][] update() {
		Coordinate pos = new Coordinate();
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < rows; y++) {
				pos.setCoordinate(x, y);
				arena[x][y].decrementTime();
				if (getField(pos).time == 0) {
					switch (getField(pos).type) {
					case Const.PLAYER:
					case Const.BOMB:
						detonateBomb(pos, stats[getField(pos).owner].bombsize);
						break;
					case Const.EXPLOSION:
						if (getField(pos).buffer > Const.NO_ITEM) {
							arena[x][y] = new TypeItem(getField(pos).buffer);
						} else {
							arena[x][y] = empty;
						}
					}
				}
			}
		}
		int winner = 0;
		int players = 0;
		if (stats[Const.PLAYER1].alive) {
			winner += Const.PLAYER1;
			players++;
		}
		if (stats[Const.PLAYER2].alive) {
			winner += Const.PLAYER2;
			players++;
		}
		if (stats[Const.PLAYER3].alive) {
			winner += Const.PLAYER3;
			players++;
		}
		if (stats[Const.PLAYER4].alive) {
			winner += Const.PLAYER4;
			players++;
		}

		if (players == 1) {
			listener.gameover(winner);
		}
		if (players == 0) {
			listener.gameover(Const.NO_OWNER);
		}

		return arena;

	}

	public void addModelListener(ModelListener listener) {
		this.listener = listener;
	}

	public FieldType[][] getArena() {
		return arena;
	}

	private void buildArena(int players) {
		// builds the walls and fills the rest with crates
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				if (i == 0 || j == 0 || i == rows - 1 || j == rows - 1 || (i % 2 == 0 && j % 2 == 0)) {
					arena[i][j] = new TypeWall();
				} else {
					int chance = Const.getRandom(0, 100);
					if (chance <= chanceBombsize) {
						arena[i][j] = new TypeCrate(Const.ITEM_BOMBSIZE);
					} else if (chance <= (chanceBombPlus + chanceBombsize)) {
						arena[i][j] = new TypeCrate(Const.ITEM_BOMB_PLUS);
					} else if (chance <= (chanceTrigger + chanceBombPlus + chanceBombsize)) {
						arena[i][j] = new TypeCrate(Const.ITEM_TRIGGER);
					} else {
						arena[i][j] = new TypeCrate();
					}
				}
			}
		}

		placePlayers(players);
	}

	private void placePlayers(int players) {

		if ((players & (1 << 0)) > 0) {
			stats[Const.PLAYER1].alive = true;
			stats[Const.PLAYER1].pos.setCoordinate(rows - 2, rows - 2);
			arena[rows - 2][rows - 2] = new TypePlayer(Const.PLAYER1);
			arena[rows - 2][rows - 3] = empty;
			arena[rows - 3][rows - 2] = empty;
		}
		if ((players & (1 << 1)) > 0) {
			stats[Const.PLAYER2].alive = true;
			stats[Const.PLAYER2].pos.setCoordinate(1, 1);
			arena[1][1] = new TypePlayer(Const.PLAYER2);
			arena[1][2] = empty;
			arena[2][1] = empty;
		}
		if ((players & (1 << 2)) > 0) {
			stats[Const.PLAYER3].alive = true;
			stats[Const.PLAYER3].pos.setCoordinate(rows - 2, 1);
			arena[rows - 2][1] = new TypePlayer(Const.PLAYER3);
			arena[rows - 2][2] = empty;
			arena[rows - 3][1] = empty;
		}
		if ((players & (1 << 3)) > 0) {
			stats[Const.PLAYER4].alive = true;
			stats[Const.PLAYER4].pos.setCoordinate(1, rows - 2);
			arena[1][rows - 2] = new TypePlayer(Const.PLAYER4);
			arena[1][rows - 3] = empty;
			arena[2][rows - 2] = empty;
		}
	}

	public void movePlayer(int player, int direction) {
		if (player >= Const.PLAYER1 && player <= Const.PLAYER4) {
			if (stats[player].alive == true) {
				Coordinate pos = stats[player].pos;
				Coordinate nextpos = calculateNextpos(pos, direction);
				if (getField(nextpos).isSolid() == false) {
					switch (getField(nextpos).type) {
					case Const.ITEM:
						if (getField(nextpos).variant == Const.ITEM_BOMBSIZE && stats[player].bombsize < rows - 2) {
							stats[player].bombsize++;
						} else if (getField(nextpos).variant == Const.ITEM_BOMB_PLUS
								&& stats[player].bombnum < rows - 2) {
							stats[player].bombnum++;
						}
					case Const.EMPTY:
						deletePlayer(pos, player);
						arena[nextpos.x][nextpos.y] = new TypePlayer(player);
						pos.setCoordinate(nextpos.getCoordinate());
						break;
					case Const.EXPLOSION:
						stats[player].alive = false;
						deletePlayer(pos, player);
						break;
					}
				}
			}
		}
	}

	// TODO: TRIGGER BOMB !!
	public void placeBomb(int player) {
		if (player >= Const.PLAYER1 && player <= Const.PLAYER4) {
			Coordinate pos = stats[player].pos;
			int count = countBombs(player);
			if (stats[player].bombtype == Const.NORMAL_BOMB) {
				if (count < stats[player].bombnum) {
					getField(pos).setVariant(Const.NORMAL_BOMB);
					getField(pos).setTime(Const.BOMB_TIME);
				}
			} else if (stats[player].bombtype == Const.TRIGGER_BOMB) {
				// TODO: insert Triggerbomb
			}
		}
	}

	private void deletePlayer(Coordinate pos, int player) {
		switch (getField(pos).variant) {
		case 0: // no bomb
			arena[pos.x][pos.y] = new TypeEmpty();
			break;
		case 1: // normal bomb
			arena[pos.x][pos.y] = new TypeBomb(player);
			break;
		case 2: // trigger bomb
			arena[pos.x][pos.y] = new TypeBomb(player, Const.TRIGGER_BOMB);
			break;
		default: // anomaly
			// System.out.println("uncool!");
			break;
		}
	}

	private void detonateBomb(Coordinate pos, int radius) {
		int owner = getField(pos).owner;
		int dirBlocked = 0;

		/* set epicenter */
		int variant = 0;
		// check if field to the north is breakable
		if (arena[pos.x][pos.y - 1].breakable > 0) {
			variant |= (1 << 0); // set Bit0
		} else {
			dirBlocked |= (1 << 0); // set Bit0 > path upwards blocked
		}
		// check if field to the east is breakable
		if (arena[pos.x + 1][pos.y].breakable > 0) {
			variant |= (1 << 1); // set Bit1
		} else {
			dirBlocked |= (1 << 1); // set Bit1 > path right blocked
		}
		// check if field to the south is breakable
		if (arena[pos.x][pos.y + 1].breakable > 0) {
			variant |= (1 << 2); // set Bit2
		} else {
			dirBlocked |= (1 << 2); // set Bit2 > path downwards blocked
		}
		// check if field to the west is breakbale
		if (arena[pos.x - 1][pos.y].breakable > 0) {
			variant |= (1 << 3); // set Bit3
		} else {
			dirBlocked |= (1 << 3); // set Bit3 > path left blocked
		}
		arena[pos.x][pos.y] = new TypeExplosion(owner, variant);

		/* calculate explosion path */
		Coordinate target = new Coordinate();
		for (int d = 0; d < 4; d++) {
			target.setCoordinate(pos.getCoordinate());
			for (int i = 1; i <= radius; i++) {
				if ((dirBlocked & (1 << d)) == 0) {
					target = calculateNextpos(target, d);
					if (i < radius) {
						dirBlocked |= calculateExp(target, d, owner, false);
					} else {
						dirBlocked |= calculateExp(target, d, owner, true);
					}
				} else {
					break;
				}
			}
		}
	}

	private int calculateExp(Coordinate pos, int dir, int owner, boolean isEnd) {
		int dirConvertion = 1 << (2 + dir) % 4; // converts direction to variant
												// for an explotion end
		int variant; // temp variable used to determine the correct variant

		switch (getField(pos).type) {
		case Const.CRATE:
			arena[pos.x][pos.y] = new TypeExplosion(owner, dirConvertion, getField(pos).variant);
			return (1 << dir);
		case Const.PLAYER:
			stats[getField(pos).owner].alive = false;
			if (getField(pos).variant > 0) {
				arena[pos.x][pos.y] = new TypeBomb(getField(pos).owner, true);
				if (getField(calculateNextpos(pos, dir)).breakable == Const.UNBREAKABLE) {
					return (1 << dir);
				}
			} else if ((getField(calculateNextpos(pos, dir)).breakable > Const.UNBREAKABLE) && isEnd == false) {
				if (dir % 2 == 0) {
					variant = Const.EXP_VERTICAL;
				} else {
					variant = Const.EXP_HORIZONTAL;
				}
				arena[pos.x][pos.y] = new TypeExplosion(owner, variant);
			} else {
				arena[pos.x][pos.y] = new TypeExplosion(owner, dirConvertion);
				return (1 << dir);
			}
			break;
		case Const.BOMB:
			arena[pos.x][pos.y].setTime(1);
			if (getField(calculateNextpos(pos, dir)).breakable == Const.UNBREAKABLE) {
				return (1 << dir);
			}
			break;
		case Const.EMPTY:
		case Const.ITEM:
			if ((getField(calculateNextpos(pos, dir)).breakable > Const.UNBREAKABLE) && isEnd == false) {
				if (dir % 2 == 0) {
					variant = Const.EXP_VERTICAL;
				} else {
					variant = Const.EXP_HORIZONTAL;
				}
			} else {
				variant = dirConvertion;
			}
			arena[pos.x][pos.y] = new TypeExplosion(owner, variant, getField(pos).variant);
			break;
		case Const.EXPLOSION:
			if ((getField(calculateNextpos(pos, dir)).breakable > Const.UNBREAKABLE) && isEnd == false) {
				if (dir % 2 == 0) {
					variant = Const.EXP_VERTICAL;
				} else {
					variant = Const.EXP_HORIZONTAL;
				}
				arena[pos.x][pos.y].addExpVariant(variant);
				break;
			} else {
				variant = dirConvertion;
				arena[pos.x][pos.y].addExpVariant(variant);
				return (1 << dir);
			}
		}
		return 0;
	}

	private Coordinate calculateNextpos(Coordinate pos, int dir) {
		Coordinate nextpos = new Coordinate(pos.getCoordinate());
		switch (dir) {
		case 0:
			if (nextpos.y > 0) {
				nextpos.y--;
			}
			break;
		case 1:
			if (nextpos.x < (rows - 1)) {
				nextpos.x++;
			}
			break;
		case 2:
			if (nextpos.y < (rows - 1)) {
				nextpos.y++;
			}
			break;
		case 3:
			if (nextpos.x > 0) {
				nextpos.x--;
			}
			break;
		default:
			break;
		}
		return nextpos;
	}

	private int countBombs(int player) {
		if (player >= Const.PLAYER1 && player <= Const.PLAYER4) {
			int bombcount = 0;
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < rows; j++) {
					if ((arena[i][j].type == Const.PLAYER && arena[i][j].variant > 0)
							|| (arena[i][j].type == Const.BOMB && arena[i][j].owner == player)) {
						bombcount++;
					}
				}
			}
			return bombcount;
		} else {
			return -1;
		}
	}

	private FieldType getField(Coordinate pos) {
		return arena[pos.x][pos.y];
	}

}
