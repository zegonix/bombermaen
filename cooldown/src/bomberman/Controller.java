package bomberman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import bomberman.Const.gameState;

/**
 * the controller manages programm parts and listeners
 * 
 * @author jonas
 * @date 04.05.17
 */
public class Controller implements TimerListener, ModelListener {

	// -- game constants
	final private int FRAMERATE = 25; // Hz
	final private int TICKLENGTH = 1000 / FRAMERATE; // in milliseconds
	final private int DEFAULT_ARENA_SIZE = 15; // MUST be an odd number
	final private int MIN_ARENA_SIZE = 7;
	final private int MAX_ARENA_SIZE = 27;
	final private int ARENA_SIZE_STEP = 2;
	final private int DEFAULT_BOMBCOUNT = 1;
	final private int DEFAULT_BOMBSTRENGTH = 1;
	final private int DEFAULT_PLAYERSELECTION = 3; // <=> 0b0011

	// -- instances of custom classes
	Timer timer;
	Model model;
	View view;
	private GameParameters parameters;

	/**
	 * constructor of the controller class
	 */
	public Controller() {
		timer = new Timer(TICKLENGTH, this);
		parameters = new GameParameters("default", DEFAULT_ARENA_SIZE, DEFAULT_BOMBCOUNT, DEFAULT_BOMBSTRENGTH,
				DEFAULT_PLAYERSELECTION);
		view = new View(parameters, new MapSmallerListener(), new MapBiggerListener(), new BombcountDownListener(),
				new BombcountUpListener(), new BombstrengthDownListener(), new BombstrengthUpListener(),
				new PlayButtonListener());
		view.addKeyAdapter(new GameKeyListener());
		timer.start();
	}

	public void addListenerToModel() {
		model.addModelListener(this);
	}

	/**
	 * catches and processes key events
	 * 
	 * @author jonas
	 * @date 08.05.17
	 */
	class GameKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			gameState currentState = view.getGameState();
			switch (e.getKeyCode()) {
			// Player 1
			case KeyEvent.VK_UP:
				if (currentState == gameState.RUNNING) {
					model.movePlayer(Const.PLAYER1, Const.NORTH);
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (currentState == gameState.RUNNING) {
					model.movePlayer(Const.PLAYER1, Const.EAST);
				}
				break;
			case KeyEvent.VK_DOWN:
				if (currentState == gameState.RUNNING) {
					model.movePlayer(Const.PLAYER1, Const.SOUTH);
				}
				break;
			case KeyEvent.VK_LEFT:
				if (currentState == gameState.RUNNING) {
					model.movePlayer(Const.PLAYER1, Const.WEST);
				}
				break;
			case KeyEvent.VK_DELETE:
				if (currentState == gameState.RUNNING) {
					model.placeBomb(Const.PLAYER1);
				}
			case KeyEvent.VK_END:
				if (currentState == gameState.RUNNING) {
					// possibility to inset additional feature
				}
				break;

			// Player 2
			case KeyEvent.VK_W:
				if (currentState == gameState.RUNNING) {
					model.movePlayer(Const.PLAYER2, Const.NORTH);
				}
				break;
			case KeyEvent.VK_D:
				if (currentState == gameState.RUNNING) {
					model.movePlayer(Const.PLAYER2, Const.EAST);
				}
				break;
			case KeyEvent.VK_S:
				if (currentState == gameState.RUNNING) {
					model.movePlayer(Const.PLAYER2, Const.SOUTH);
				}
				break;
			case KeyEvent.VK_A:
				if (currentState == gameState.RUNNING) {
					model.movePlayer(Const.PLAYER2, Const.WEST);
				}
				break;
			case KeyEvent.VK_Q:
				if (currentState == gameState.RUNNING) {
					model.placeBomb(Const.PLAYER2);
				}
				break;
			case KeyEvent.VK_1:
				if (currentState == gameState.RUNNING) {
					// possibility to insert additional feature
				}
				break;

			// Player 3
			case KeyEvent.VK_I:
				if (currentState == gameState.RUNNING) {
					model.movePlayer(Const.PLAYER3, Const.NORTH);
				}
				break;
			case KeyEvent.VK_L:
				if (currentState == gameState.RUNNING) {
					model.movePlayer(Const.PLAYER3, Const.EAST);
				}
				break;
			case KeyEvent.VK_K:
				if (currentState == gameState.RUNNING) {
					model.movePlayer(Const.PLAYER3, Const.SOUTH);
				}
				break;
			case KeyEvent.VK_J:
				if (currentState == gameState.RUNNING) {
					model.movePlayer(Const.PLAYER3, Const.WEST);
				}
				break;
			case KeyEvent.VK_N:
				if (currentState == gameState.RUNNING) {
					model.placeBomb(Const.PLAYER3);
				}
				break;
			case KeyEvent.VK_B:
				if (currentState == gameState.RUNNING) {
					// possibility to insert additional feature
				}
				break;

			default:
				// do nothing
				break;
			}
		}
	}

	class PlayButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			model = new Model(parameters.size, parameters.bombcount, parameters.bombstrength);
			addListenerToModel();
			view.changeGameState(gameState.RUNNING);
		}
	}

	class MapPreviousListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO: add different maps and stuff
		}
	}

	class MapNextListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO: see MapPreviousListener
		}
	}

	class MapSmallerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (parameters.size >= (MIN_ARENA_SIZE + ARENA_SIZE_STEP)) {
				parameters.size -= ARENA_SIZE_STEP;
				view.setArenaSize(parameters.size);
			}
		}
	}

	class MapBiggerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (parameters.size <= (MAX_ARENA_SIZE - ARENA_SIZE_STEP)) {
				parameters.size += ARENA_SIZE_STEP;
				view.setArenaSize(parameters.size);
			}
		}
	}

	class BombcountDownListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (parameters.bombcount > 1) {
				parameters.bombcount--;
				view.setBombcount(parameters.bombcount);
			}
		}
	}

	class BombcountUpListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (parameters.bombcount < (parameters.size - 2)) {
				parameters.bombcount++;
				view.setBombcount(parameters.bombcount);
			}
		}
	}

	class BombstrengthDownListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (parameters.bombstrength > 1) {
				parameters.bombstrength--;
				view.setBombstrength(parameters.bombstrength);
			}
		}
	}

	class BombstrengthUpListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (parameters.bombstrength < (parameters.size - 2)) {
				parameters.bombstrength++;
				view.setBombstrength(parameters.bombstrength);
				System.out.println("HALP");
			}
		}
	}

	class PlayerButtonListener implements ActionListener {

		private int player;

		public PlayerButtonListener(int player) {
			super();
			this.player = player;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if ((player >= 1) && (player <= 4)) {
				parameters.playerselection = parameters.playerselection ^ (1 << (player - 1));
				// TODO: insert statement to change according buttons image
			}
		}

	}

	@Override
	public void timeElapsed() {
		if (view.getGameState() == gameState.RUNNING) {
			view.showModel(model.update());
		}
	}

	@Override
	public void gameover(int winner) {
		view.changeGameState(gameState.MENU);
	}

}
