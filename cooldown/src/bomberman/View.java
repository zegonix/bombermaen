package bomberman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import bomberman.Const.gameState;

public class View extends JFrame {

	// -- constants
	final private double FONTSCALER = 0.92;
	final private double FACTOR_MAX_FIELD_SIZE = 0.06;

	// -- variables
	private gameState state = gameState.VOID;
	private GameParameters parameters;
	private Font font;
	int fieldsize;
	int offsetX;
	int offsetY;

	// -- GUI elements
	private JLabel titleLabel;
	private JLabel mapselectionLabel;
	private JLabel mapsizeLabel;
	private JLabel bombcountLabel;
	private JLabel bombstrengthLabel;
	private ImageButton mapPreviousButton;
	private ImageButton mapNextButton;
	private ImageButton mapSmallerButton;
	private ImageButton mapBiggerButton;
	private ImageButton bombcountDownButton;
	private ImageButton bombcountUpButton;
	private ImageButton bombstrengthDownButton;
	private ImageButton bombstrengthUpButton;
	private JButton player1Button;
	private JButton player2Button;
	private JButton player3Button;
	private JButton player4Button;
	private JButton playButton;
	private Display g;

	// -- Listeners
	private KeyAdapter keylistener;
	private ActionListener mapSmallerListener;
	private ActionListener mapBiggerListener;
	private ActionListener bombcountDownListener;
	private ActionListener bombcountUpListener;
	private ActionListener bombstrengthDownListener;
	private ActionListener bombstrengthUpListener;
	private ActionListener playButtonListener;

	// -- files
	final private File font_planecrash = new File("src\\res\\plane_crash.ttf");

	final private File arrow_left = new File("src\\res\\arrow_left.png");
	final private File arrow_right = new File("src\\res\\arrow_right.png");
	final private File player_button_unselected = new File("src\\res\\playerButtonUnselected.png");
	final private File player_button_selected = new File("src\\res\\playerButtonSelected.png");

	final private File file_wall = new File("src\\res\\wall.png");
	final private File file_crate = new File("src\\res\\crate.png");
	final private File file_exp_0 = new File("src\\res\\exp_0.png");
	final private File file_exp_1 = new File("src\\res\\exp_1.png");
	final private File file_exp_2 = new File("src\\res\\exp_2.png");
	final private File file_exp_3 = new File("src\\res\\exp_3.png");
	final private File file_exp_4 = new File("src\\res\\exp_4.png");
	final private File file_exp_5 = new File("src\\res\\exp_5.png");
	final private File file_exp_6 = new File("src\\res\\exp_6.png");
	final private File file_exp_7 = new File("src\\res\\exp_7.png");
	final private File file_exp_8 = new File("src\\res\\exp_8.png");
	final private File file_exp_9 = new File("src\\res\\exp_9.png");
	final private File file_exp_a = new File("src\\res\\exp_a.png");
	final private File file_exp_b = new File("src\\res\\exp_b.png");
	final private File file_exp_c = new File("src\\res\\exp_c.png");
	final private File file_exp_d = new File("src\\res\\exp_d.png");
	final private File file_exp_e = new File("src\\res\\exp_e.png");
	final private File file_exp_f = new File("src\\res\\exp_f.png");

	// -- Images
	private Image arrowLeft;
	private Image arrowRight;
	private Image playerButtonUnselected;
	private Image playerButtonSelected;
	private Image wall;
	private Image crate;
	private Image[] explosion = new Image[16];

	final private Color background = new Color(215, 210, 205);

	public View(GameParameters gameParameters, ActionListener smallerListener, ActionListener biggerListener,
			ActionListener bombcountDownListener, ActionListener bombcountUpListener,
			ActionListener bombstrengthDownListener, ActionListener bombstrengthUpListener,
			ActionListener playListener) {

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screen.width, screen.height);
		setResizable(false);
		setVisible(true);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.parameters = gameParameters;
		this.mapSmallerListener = smallerListener;
		this.mapBiggerListener = biggerListener;
		this.bombcountDownListener = bombcountDownListener;
		this.bombcountUpListener = bombcountUpListener;
		this.bombstrengthDownListener = bombstrengthDownListener;
		this.bombstrengthUpListener = bombstrengthUpListener;
		this.playButtonListener = playListener;

		/*-- import and register custom font --*/
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, font_planecrash);
		} catch (IOException | FontFormatException e) {
			System.out.println(e.getMessage());
		}

		readImages();
		initGUI();

		// initiate the view with the mainmenu
		changeGameState(gameState.MENU);

	}

	public void addKeyAdapter(KeyAdapter listener) {
		keylistener = listener;
		g.addKeyListener(keylistener);
		this.addKeyListener(keylistener);
	}

	public void setMap(String map) {
		if (state == Const.gameState.MENU) {
			mapselectionLabel.setText(map);
		}
		repaint();
	}

	public void setArenaSize(int size) {
		if (state == Const.gameState.MENU) {
			parameters.size = size;
			mapsizeLabel.setText("" + parameters.size);
		}
		repaint();
	}

	public void setBombcount(int bombcount) {
		if (state == Const.gameState.MENU) {
			parameters.bombcount = bombcount;
			bombcountLabel.setText("" + parameters.bombcount);
		}
		repaint();
	}

	public void setBombstrength(int bombstrength) {
		if (state == Const.gameState.MENU) {
			parameters.bombstrength = bombstrength;
			bombstrengthLabel.setText("" + parameters.bombstrength);
		}
		repaint();
	}

	public gameState getGameState() {
		return state;
	}

	public void changeGameState(gameState newState) {

		if (state == newState) {
			System.out.println("state already active");
		} else {
			// remove objects of the current state
			switch (state) {
			case VOID:
				break;

			case MENU:
				removeMainmenu();
				break;

			case OPTIONS:
				// TODO: insert Options
				break;

			case RUNNING:
				if (state != gameState.PAUSED) {
					removeGamescreen();
				}
				break;

			case PAUSED:
				if (state != gameState.RUNNING) {
					removeGamescreen();
				}
				break;

			default:
				System.out.println("error!!");
				break;
			}

			// add objects of the new state
			switch (newState) {
			case VOID:
				System.out.println("incorrect state input");
				break;

			case MENU:
				addMainmenu();
				break;

			case OPTIONS:
				// TODO: insert Options
				break;

			case RUNNING:
				if (state != gameState.PAUSED) {
					addGamescreen();
				}
				break;

			case PAUSED:
				if (state != gameState.RUNNING) {
					addGamescreen();
				}
				break;

			default:
				System.out.println("incorrect state input");
				break;
			}

			state = newState;
		}
	}

	public void showModel(FieldType[][] model) {

		Graphics buf = g.getBuf();
		g.requestFocus();
		g.clear();

		for (int x = 0; x < parameters.size; x++) {
			for (int y = 0; y < parameters.size; y++) {
				buf.setColor(background);
				buf.fillRect(offsetX + x * fieldsize, offsetY + y * fieldsize, fieldsize, fieldsize);
				switch (model[x][y].type) {
				case Const.EMPTY:
					break;
				case Const.WALL:
					buf.drawImage(wall, offsetX + x * fieldsize, offsetY + y * fieldsize, fieldsize, fieldsize, null);
					break;
				case Const.CRATE:
					buf.drawImage(crate, offsetX + x * fieldsize, offsetY + y * fieldsize, fieldsize, fieldsize, null);
					break;
				case Const.PLAYER:
					buf.setColor(Color.BLUE);
					buf.fillRect(offsetX + x * fieldsize, offsetY + y * fieldsize, fieldsize, fieldsize);
					break;
				case Const.BOMB:
					buf.setColor(Color.RED);
					buf.fillRect(offsetX + x * fieldsize, offsetY + y * fieldsize, fieldsize, fieldsize);
					break;
				case Const.EXPLOSION:
					buf.drawImage(explosion[model[x][y].getVariant()], offsetX + x * fieldsize, offsetY + y * fieldsize,
							fieldsize, fieldsize, null);
					break;
				case Const.ITEM:
					buf.setColor(Color.GREEN);
					buf.fillRect(offsetX + x * fieldsize, offsetY + y * fieldsize, fieldsize, fieldsize);
					break;
				default:
					buf.setColor(Color.PINK);
					buf.fillRect(offsetX + x * fieldsize, offsetY + y * fieldsize, fieldsize, fieldsize);
				}
			}
		}

		g.repaint();

	}

	/**
	 * adds and configures the components for the menu
	 * 
	 * @param arenaSelection
	 * @param size
	 * @param bombcount
	 * @param bombsize
	 * @param playerSelection
	 */
	private void addMainmenu() {

		int width = this.getWidth();
		int height = this.getHeight();

		String arenaSelection = parameters.map;
		int size = parameters.size;
		int bombcount = parameters.bombcount;
		int bombstrength = parameters.bombstrength;
		int playerSelection = parameters.playerselection;

		/*-- title --*/
		// parameters
		String titleText = "cooldown";
		int titleWidth = (int) (width);
		int titleHeight = (int) (height * 0.1);
		int titleX = (int) ((width * 0.5) - (titleWidth * 0.5));
		int titleY = (int) (height * 0.03);
		// configuration
		titleLabel.setBounds(titleX, titleY, titleWidth, titleHeight);
		titleLabel.setFocusable(false);
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setFont(font.deriveFont((float) (titleHeight * FONTSCALER)));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setText(titleText);
		add(titleLabel);

		/*-- Map Selection --*/
		// parameters
		int mapselWidth = (int) (width * 0.25);
		int mapselHeight = (int) (height * 0.04);
		int mapselX = (int) ((width * 0.5) - 2.5 * (mapselWidth * 0.5));
		int mapselY = (int) (height * 0.19);
		// configuration
		mapselectionLabel.setBounds(mapselX, mapselY, mapselWidth, mapselHeight);
		mapselectionLabel.setFocusable(false);
		mapselectionLabel.setForeground(Color.BLACK);
		mapselectionLabel.setFont(font.deriveFont((float) (mapselHeight * FONTSCALER)));
		mapselectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mapselectionLabel.setText(arenaSelection);
		add(mapselectionLabel);

		/*-- Previous --*/
		// parameters
		int mapPreviousWidth = (int) (mapselHeight * 0.5);
		int mapPreviousHeight = (int) (mapselHeight * 1.0);
		int mapPreviousX = (int) (mapselX - 2.0 * mapPreviousWidth);
		int mapPreviousY = (int) (mapselY);
		// configuration
		mapPreviousButton.setBounds(mapPreviousX, mapPreviousY, mapPreviousWidth, mapPreviousHeight);
		mapPreviousButton.setFocusable(true);
		add(mapPreviousButton);

		/*-- Next --*/
		// parameters
		int mapNextWidth = (int) (mapselHeight * 0.5);
		int mapNextHeight = (int) (mapselHeight * 1.0);
		int mapNextX = (int) (mapselX + mapselWidth + 2.0 * mapNextWidth);
		int mapNextY = (int) (mapselY);
		// configuration
		mapNextButton.setBounds(mapNextX, mapNextY, mapNextWidth, mapNextHeight);
		mapNextButton.setFocusable(true);
		add(mapNextButton);

		/*-- Size --*/
		// parameters
		int mapsizeWidth = mapselWidth;
		int mapsizeHeight = mapselHeight;
		int mapsizeX = mapselX;
		int mapsizeY = (int) (height * 0.26);
		// configuration
		mapsizeLabel.setBounds(mapsizeX, mapsizeY, mapsizeWidth, mapsizeHeight);
		mapsizeLabel.setFocusable(false);
		mapsizeLabel.setForeground(Color.BLACK);
		mapsizeLabel.setFont(font.deriveFont((float) (mapsizeHeight * FONTSCALER)));
		mapsizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mapsizeLabel.setText("" + size);
		add(mapsizeLabel);

		/*-- Smaller --*/
		// parameters
		int mapSmallerWidth = (int) (mapsizeHeight * 0.5);
		int mapSmallerHeight = (int) (mapsizeHeight * 1.0);
		int mapSmallerX = (int) (mapsizeX - 2.0 * mapSmallerWidth);
		int mapSmallerY = (int) (mapsizeY);
		// configuration
		mapSmallerButton.setBounds(mapSmallerX, mapSmallerY, mapSmallerWidth, mapSmallerHeight);
		mapSmallerButton.setFocusable(true);
		add(mapSmallerButton);

		/*-- Bigger --*/
		// parameters
		int mapBiggerWidth = (int) (mapsizeHeight * 0.5);
		int mapBiggerHeight = (int) (mapsizeHeight * 1.0);
		int mapBiggerX = (int) (mapsizeX + mapsizeWidth + 2.0 * mapBiggerWidth);
		int mapBiggerY = (int) (mapsizeY);
		// configuration
		mapBiggerButton.setBounds(mapBiggerX, mapBiggerY, mapBiggerWidth, mapBiggerHeight);
		mapBiggerButton.setFocusable(true);
		add(mapBiggerButton);

		/*-- Bombcount --*/
		// parameters
		int bombcountWidth = mapselWidth;
		int bombcountHeight = mapselHeight;
		int bombcountX = (int) ((width * 0.5) + 0.5 * (bombcountWidth * 0.5));
		int bombcountY = mapselY;
		// configuration
		bombcountLabel.setBounds(bombcountX, bombcountY, bombcountWidth, bombcountHeight);
		bombcountLabel.setFocusable(false);
		bombcountLabel.setForeground(Color.BLACK);
		bombcountLabel.setFont(font.deriveFont((float) (bombcountHeight * FONTSCALER)));
		bombcountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bombcountLabel.setText("" + bombcount);
		add(bombcountLabel);

		/*-- BombcountDown --*/
		// parameters
		int bombcountDownWidth = (int) (bombcountHeight * 0.5);
		int bombcountDownHeight = (int) (bombcountHeight * 1.0);
		int bombcountDownX = (int) (bombcountX - 2.0 * bombcountDownWidth);
		int bombcountDownY = (int) (bombcountY);
		// configuration
		bombcountDownButton.setBounds(bombcountDownX, bombcountDownY, bombcountDownWidth, bombcountDownHeight);
		bombcountDownButton.setFocusable(true);
		add(bombcountDownButton);

		/*-- BombcountUp --*/
		// parameters
		int bombcountUpWidth = (int) (bombcountHeight * 0.5);
		int bombcountUpHeight = (int) (bombcountHeight * 1.0);
		int bombcountUpX = (int) (bombcountX + bombcountWidth + 2.0 * bombcountUpWidth);
		int bombcountUpY = (int) (bombcountY);
		// configuration
		bombcountUpButton.setBounds(bombcountUpX, bombcountUpY, bombcountUpWidth, bombcountUpHeight);
		bombcountUpButton.setFocusable(true);
		add(bombcountUpButton);

		/*-- Bombstrength --*/
		// parameters
		int bombstrengthWidth = mapselWidth;
		int bombstrengthHeight = mapselHeight;
		int bombstrengthX = bombcountX;
		int bombstrengthY = mapsizeY;
		// configuration
		bombstrengthLabel.setBounds(bombstrengthX, bombstrengthY, bombstrengthWidth, bombstrengthHeight);
		bombstrengthLabel.setFocusable(false);
		bombstrengthLabel.setForeground(Color.BLACK);
		bombstrengthLabel.setFont(font.deriveFont((float) (bombstrengthHeight * FONTSCALER)));
		bombstrengthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bombstrengthLabel.setText("" + bombstrength);
		add(bombstrengthLabel);

		/*-- BombstrengthDown --*/
		// parameters
		int bombstrengthDownWidth = (int) (bombstrengthHeight * 0.5);
		int bombstrengthDownHeight = (int) (bombstrengthHeight * 1.0);
		int bombstrengthDownX = (int) (bombstrengthX - 2.0 * bombstrengthDownWidth);
		int bombstrengthDownY = (int) (bombstrengthY);
		// configuration
		bombstrengthDownButton.setBounds(bombstrengthDownX, bombstrengthDownY, bombstrengthDownWidth,
				bombstrengthDownHeight);
		bombstrengthDownButton.setFocusable(true);
		add(bombstrengthDownButton);

		/*-- BombstrengthUp --*/
		// parameters
		int bombstrengthUpWidth = (int) (bombstrengthHeight * 0.5);
		int bombstrengthUpHeight = (int) (bombstrengthHeight * 1.0);
		int bombstrengthUpX = (int) (bombstrengthX + bombstrengthWidth + 2.0 * bombstrengthUpWidth);
		int bombstrengthUpY = (int) (bombstrengthY);
		// configuration
		bombstrengthUpButton.setBounds(bombstrengthUpX, bombstrengthUpY, bombstrengthUpWidth, bombstrengthUpHeight);
		bombstrengthUpButton.setFocusable(true);
		add(bombstrengthUpButton);

		/*-- PLAY --*/
		// parameters
		int playWidth = (int) (width * 0.3);
		int playHeight = (int) (height * 0.07);
		int playX = (int) ((width - playWidth) * 0.5);
		int playY = (int) (height * 0.7);
		// configuration
		playButton.setBounds(playX, playY, playWidth, playHeight);
		playButton.setFocusable(true);
		playButton.setContentAreaFilled(false);
		playButton.setBorderPainted(false);
		playButton.setHorizontalAlignment(SwingConstants.CENTER);
		playButton.setVerticalAlignment(SwingConstants.CENTER);
		playButton.setForeground(Color.BLACK);
		playButton.setFont(font.deriveFont((float) (playHeight * FONTSCALER)));
		playButton.setText("play");
		add(playButton);

		// -- redraw the frame to show the new screen
		repaint();
	}

	private void addGamescreen() {
		g.setBounds(this.getBounds());
		add(g);

		fieldsize = (int) (this.getHeight() * 0.75 / parameters.size);
		offsetX = (int) ((this.getWidth() - parameters.size * fieldsize) / 2);
		offsetY = (int) ((this.getHeight() - parameters.size * fieldsize) / 2);
		if (fieldsize > (int) (this.getHeight() * FACTOR_MAX_FIELD_SIZE)) {
			fieldsize = (int) (this.getHeight() * FACTOR_MAX_FIELD_SIZE);
		}
	}

	private void removeMainmenu() {
		try {
			remove(titleLabel);
			remove(mapselectionLabel);
			remove(mapPreviousButton);
			remove(mapNextButton);
			remove(mapsizeLabel);
			remove(mapSmallerButton);
			remove(mapBiggerButton);
			remove(bombcountLabel);
			remove(bombcountDownButton);
			remove(bombcountUpButton);
			remove(bombstrengthLabel);
			remove(bombstrengthDownButton);
			remove(bombstrengthUpButton);
			remove(playButton);
			repaint();
		} catch (NullPointerException e) {
			System.out.println("NullPointerException: " + e.getMessage());
		}
	}

	private void removeGamescreen() {
		try {
			remove(g);
			repaint();
		} catch (NullPointerException e) {
			System.out.println("NullPointerException: " + e.getMessage());
		}
	}

	/**
	 * creates a new instance of all components contained by the JFrame !! call this
	 * method only once !!
	 */
	private void initGUI() {
		titleLabel = new JLabel();
		mapselectionLabel = new JLabel();
		mapsizeLabel = new JLabel();
		bombcountLabel = new JLabel();
		bombstrengthLabel = new JLabel();
		mapPreviousButton = new ImageButton(arrowLeft);
		mapNextButton = new ImageButton(arrowRight);
		mapSmallerButton = new ImageButton(arrowLeft);
		mapBiggerButton = new ImageButton(arrowRight);
		bombcountDownButton = new ImageButton(arrowLeft);
		bombcountUpButton = new ImageButton(arrowRight);
		bombstrengthDownButton = new ImageButton(arrowLeft);
		bombstrengthUpButton = new ImageButton(arrowRight);
		player1Button = new ImageButton(playerButtonUnselected);
		player2Button = new ImageButton(playerButtonUnselected);
		player3Button = new ImageButton(playerButtonUnselected);
		player4Button = new ImageButton(playerButtonUnselected);
		playButton = new JButton();
		g = new Display(this.getWidth(), this.getHeight());

		// mapPrevious.addActionListener();
		// mapNext.addActionListener();
		mapSmallerButton.addActionListener(mapSmallerListener);
		mapBiggerButton.addActionListener(mapBiggerListener);
		bombcountDownButton.addActionListener(bombcountDownListener);
		bombcountUpButton.addActionListener(bombcountUpListener);
		bombstrengthDownButton.addActionListener(bombstrengthDownListener);
		bombstrengthUpButton.addActionListener(bombstrengthUpListener);
		playButton.addActionListener(playButtonListener);
	}

	/**
	 * reads the files from the given paths
	 */
	private void readImages() {
		try {
			arrowLeft = ImageIO.read(arrow_left);
			arrowRight = ImageIO.read(arrow_right);
			playerButtonUnselected = ImageIO.read(player_button_unselected);
			playerButtonSelected = ImageIO.read(player_button_selected);
			wall = ImageIO.read(file_wall);
			crate = ImageIO.read(file_crate);
			explosion[0] = ImageIO.read(file_exp_0);
			explosion[1] = ImageIO.read(file_exp_1);
			explosion[2] = ImageIO.read(file_exp_2);
			explosion[3] = ImageIO.read(file_exp_3);
			explosion[4] = ImageIO.read(file_exp_4);
			explosion[5] = ImageIO.read(file_exp_5);
			explosion[6] = ImageIO.read(file_exp_6);
			explosion[7] = ImageIO.read(file_exp_7);
			explosion[8] = ImageIO.read(file_exp_8);
			explosion[9] = ImageIO.read(file_exp_9);
			explosion[10] = ImageIO.read(file_exp_a);
			explosion[11] = ImageIO.read(file_exp_b);
			explosion[12] = ImageIO.read(file_exp_c);
			explosion[13] = ImageIO.read(file_exp_d);
			explosion[14] = ImageIO.read(file_exp_e);
			explosion[15] = ImageIO.read(file_exp_f);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
