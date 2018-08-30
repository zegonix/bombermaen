package bomberman;

import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JButton;

public class TextButton extends JButton {

	private String text;
	
	public TextButton () {
		super();
	}
	
	public TextButton (String text){
		super();
		this.text = text;
	}
	
	public void setText (String text){
		this.text = text;
	}
	
	@Override
	public void paint(Graphics g){
		
		g.setFont(this.getFont());
		FontMetrics m = g.getFontMetrics();
		g.drawString(text, (this.getWidth() - m.stringWidth(text))/2, (this.getHeight()-m.getAscent())/2);
		
	}
	
}
