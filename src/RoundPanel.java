import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RoundPanel extends JPanel {
	public static final int HEIGHT = 30;
	public static final int WIDTH = 1000;
	private int currentRound;
	private int numberRounds;

	/**
	 * Constructor class.
	 */
	public RoundPanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.drawString("Round " + currentRound + " of " + numberRounds, 10, 20);
	}

	public void updateRounds(int currentRound, int numberRounds) {
		this.currentRound = currentRound;
		this.numberRounds = numberRounds;
	}

}