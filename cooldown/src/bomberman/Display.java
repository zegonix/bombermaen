package bomberman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * JPanel using BufferedImage to suppress flickering
 * 
 * @author jonas
 * @date 05.05.17
 * 
 */
public class Display extends JPanel {

	final private Color defaultColor = Color.WHITE; // TODO: adjust default
													// color
	private BufferedImage image;
	private Graphics buf;
	private int width;
	private int height;

	/**
	 * Constructor of Display, sets the dimensions of the image
	 * 
	 * @param width
	 * @param height
	 */
	public Display(int width, int height) {
		this.width = width;
		this.height = height;
		setLayout(null);
		image = new BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_RGB);
		buf = image.getGraphics();
	}

	/**
	 * Returns the buffed Graphics
	 * 
	 * @return bufGraphics (Graphics)
	 * 
	 */
	public Graphics getBuf() {
		return buf;
	}

	/**
	 * clears the panel by filling the buffered image with the default color
	 */
	public void clear() {
		buf.setColor(defaultColor);
		buf.fillRect(0, 0, width, height);
	}

	/**
	 * clears the panel by filling the buffered image with the given color
	 * 
	 * @param background
	 */
	public void clear(Color background) {
		buf.setColor(background);
		buf.fillRect(0, 0, width, height);
	}

	/**
	 * Override of the update()-function is necessary to suppress the
	 * repaint()-function which deletes and then paints which would result in
	 * flickering of the picture
	 */
	@Override
	public void update(Graphics g) {
		paint(g);
	}

	/**
	 * paints the buffered image
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}

}
