import java.awt.BorderLayout;
import java.awt.Container;

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
	public Table() {
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		Container cp = getContentPane();
		JLabel roundLabel = new JLabel("Round 1 of 4");
		cp.add(roundLabel, BorderLayout.NORTH);

		tablePanel = new TablePanel();
		cp.add(tablePanel, BorderLayout.CENTER);

		Box b = new Box(BoxLayout.Y_AXIS);
		b.add(new JLabel("Pot: " + pot));
		b.add(new JLabel("Bet: 360"));
		b.add(new JButton("Call"));
		b.add(new JButton("Fold"));
		cp.add(b, BorderLayout.EAST);

		pack();
		setVisible(true);
	}

	public void updatePlayerName(String playerName) {
		this.tablePanel.updatePlayerName(playerName);
	}

}