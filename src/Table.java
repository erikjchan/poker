import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Table extends JFrame implements ActionListener {
	private RoundPanel roundPanel;
	private TablePanel tablePanel;
	private int pot;
	private JButton callButton = new JButton("Call");
	private JButton foldButton = new JButton("Fold");
	private boolean clicked;

	/**
	 * Constructor class.
	 */
	public Table(ArrayList<Player> players, ArrayList<Card> communityCards) {
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		Container cp = getContentPane();

		roundPanel = new RoundPanel();
		cp.add(roundPanel, BorderLayout.NORTH);

		tablePanel = new TablePanel(players, communityCards);
		cp.add(tablePanel, BorderLayout.CENTER);

		Box b = new Box(BoxLayout.X_AXIS);
		b.add(new JLabel("     "));
		b.add(new JLabel("Pot: " + pot));
		b.add(new JLabel("     "));
		b.add(new JLabel("Bet: 360"));
		b.add(new JLabel("     "));
		b.add(callButton);
		b.add(new JLabel("     "));
		b.add(foldButton);
		b.add(new JLabel("     "));
		b.add(new JButton("Raise"));
		cp.add(b, BorderLayout.SOUTH);

		callButton.addActionListener(this);
		foldButton.addActionListener(this);

		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == callButton) {
			System.out.println("Call clicked");
		} else if (e.getSource() == foldButton) {
			System.out.println("Fold clicked");
		}

		clicked = true;
	}

	public void updatePlayers(ArrayList<Player> players) {
		this.tablePanel.updatePlayers(players);
	}

	public void updateCommunityCards(ArrayList<Card> communityCards) {
		this.tablePanel.updateCommunityCards(communityCards);
	}

	public void updateRound(int currentRound, int numberRounds, boolean roundOver) {
		this.roundPanel.updateRounds(currentRound, numberRounds);
		this.tablePanel.updateRoundOver(roundOver);
	}

	public void awaitClick() {
		clicked = false;
		while (!clicked) {
			try {
				Thread.sleep(200);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}