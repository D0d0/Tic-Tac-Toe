import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

/**
 * 
 * @author Jozef
 * 
 */
public class MyButton extends JButton {
	private static final long serialVersionUID = 1280485105851144487L;

	/**
	 * Vlastny button
	 * 
	 * @param text
	 *            label buttonu
	 */
	public MyButton(String text) {
		super(text);
		setBorderPainted(false);
		setFocusPainted(false);
		setOpaque(false);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.setBackground(Color.BLACK);
		super.paintComponent(g);
	}
}
