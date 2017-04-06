import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Table extends JFrame {
	private TablePanel tablePanel;
	private int pot;

	/**
	 * Constructor class.
	 */
	public Table(ArrayList<Player> players, ArrayList<Card> communityCards) {
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		Container cp = getContentPane();
		Box box = new Box(BoxLayout.X_AXIS);
		box.add(new JLabel("     "));
		box.add(new JLabel("Round 1 of 4"));
		cp.add(box, BorderLayout.NORTH);

		tablePanel = new TablePanel(players, communityCards);
		cp.add(tablePanel, BorderLayout.CENTER);

		Box b = new Box(BoxLayout.X_AXIS);
		b.add(new JLabel("     "));
		b.add(new JLabel("Pot: " + pot));
		b.add(new JLabel("     "));
		b.add(new JLabel("Bet: 360"));
		b.add(new JLabel("     "));
		b.add(new JButton("Call"));
		b.add(new JLabel("     "));
		b.add(new JButton("Fold"));
		b.add(new JLabel("     "));
		b.add(new JButton("Place Bet"));
		cp.add(b, BorderLayout.SOUTH);

		pack();
		setVisible(true);
	}

	public void updatePlayers(ArrayList<Player> players) {
		this.tablePanel.updatePlayers(players);
	}

	public void updateCommunityCards(ArrayList<Card> communityCards) {
		this.tablePanel.updateCommunityCards(communityCards);
	}
	
	public void updateRoundOver(boolean roundOver) {
		this.tablePanel.updateRoundOver(roundOver);
	}

}