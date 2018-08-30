package bomberman;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JButton;

public class ImageButton extends JButton {

	private Image image;

	public ImageButton() {
		super();
		setContentAreaFilled(false);
		setBorderPainted(false);
	}

	public ImageButton(Image image) {
		super();
		setContentAreaFilled(false);
		setBorderPainted(false);
		this.image = image;
	}

	public void setImage(Image image) {
		if (!image.equals(null)) {
			this.image = image;
		}
	}

	public Image getImage() {
		return this.image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
	}

}
