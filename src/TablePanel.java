import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TablePanel extends JPanel {
	public static final int HEIGHT = 600;
	public static final int WIDTH = 1000;

	/** Const: square at (x, y). Red/green? Parity of x+y. */
	public TablePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	/*
	 * paint this square using g. System calls paint whenever square has to be
	 * redrawn.
	 */
	public void paint(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// game information
		g.setColor(Color.black);
		g.drawString("Pot: 1000", 450, 50);

		// community cards
		g.setColor(Color.black);
		g.drawRect(250, 225, 500, 150);
		g.drawString("Community Cards", 250, 225 - 10);
		BufferedImage image = new BufferedImage(1, 1, 1);
		try {
			image = ImageIO.read(new File("img/back.png"));
		} catch (IOException ex) {
			// handle exception...
		}
		g.drawImage(image, 265, 225 + 25, this);
		g.drawImage(image, 365, 225 + 25, this);
		g.drawImage(image, 465, 225 + 25, this);
		g.drawImage(image, 565, 225 + 25, this);
		g.drawImage(image, 665, 225 + 25, this);

		// player 1
		g.setColor(Color.black);
		g.drawRect(25, 25, 200, 150);
		g.drawString("Player 1 (YOU)", 25, 25 - 10);
		g.drawImage(image, 40, 25 + 25, this);
		g.drawImage(image, 140, 25 + 25, this);

		// player 2
		g.setColor(Color.black);
		g.drawRect(775, 25, 200, 150);
		g.drawString("Player 2", 775, 25 - 10);
		g.drawImage(image, 790, 25 + 25, this);
		g.drawImage(image, 890, 25 + 25, this);

		// player 3
		g.setColor(Color.black);
		g.drawRect(775, 425, 200, 150);
		g.drawString("Player 3", 775, 425 - 10);
		g.drawImage(image, 790, 425 + 25, this);
		g.drawImage(image, 890, 425 + 25, this);

		// player 4
		g.setColor(Color.black);
		g.drawRect(25, 425, 200, 150);
		g.drawString("Player 4", 25, 425 - 10);
		g.drawImage(image, 40, 425 + 25, this);
		g.drawImage(image, 140, 425 + 25, this);

	}
}