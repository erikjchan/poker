import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Table extends JFrame implements ActionListener {
	private RoundPanel roundPanel;
	private TablePanel tablePanel;
	private JButton callButton = new JButton("Call");
	private JButton foldButton = new JButton("Fold");
	private JButton raiseButton = new JButton("Raise");
	private JButton nextButton = new JButton("Next Phase");
	private String decision = "";
	private boolean decisionMade;
	private boolean nextPhaseReady;

	/**
	 * Constructor class.
	 */
	public Table(ArrayList<Player> players, ArrayList<Card> communityCards, int pot, int currentBet) {
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		Container cp = getContentPane();

		roundPanel = new RoundPanel();
		cp.add(roundPanel, BorderLayout.NORTH);

		tablePanel = new TablePanel(players, communityCards, pot, currentBet);
		cp.add(tablePanel, BorderLayout.CENTER);

		Box b = new Box(BoxLayout.X_AXIS);
		b.add(Box.createRigidArea(new Dimension(340, 0)));
		b.add(callButton);
		b.add(Box.createRigidArea(new Dimension(50, 0)));
		b.add(foldButton);
		b.add(Box.createRigidArea(new Dimension(50, 0)));
		b.add(raiseButton);
		b.add(Box.createRigidArea(new Dimension(200, 0)));
		b.add(nextButton);
		cp.add(b, BorderLayout.SOUTH);

		callButton.addActionListener(this);
		foldButton.addActionListener(this);
		raiseButton.addActionListener(this);
		nextButton.addActionListener(this);

		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == callButton) {
			decision = "call";
			decisionMade = true;

		} else if (e.getSource() == foldButton) {
			decision = "fold";
			decisionMade = true;

		} else if (e.getSource() == raiseButton) {
			decision = "raise";
			decisionMade = true;

		} else if (e.getSource() == nextButton) {
			nextPhaseReady = true;
		}
	}

	/**
	 * Update the table panel.
	 * 
	 * @param players
	 *            the players in the table
	 * @param communityCards
	 *            the community cards in the table
	 * @param pot
	 *            the pot in the table
	 * @param currentBet
	 *            the current bet in the table
	 * @param turn
	 *            the turn in the table
	 */
	public void updateTablePanel(ArrayList<Player> players, ArrayList<Card> communityCards, int pot, int currentBet,
			int turn) {
		this.tablePanel.update(players, communityCards, pot, currentBet, turn);
	}

	public void updateRound(int currentRound, int numberRounds, String phase, boolean roundOver) {
		this.roundPanel.updateRounds(currentRound, numberRounds, phase);
		this.tablePanel.updateRoundOver(roundOver);
	}

	public void updateButtons(ArrayList<Player> players, int currentBet, boolean roundOver, boolean playerTurn) {
		callButton.setEnabled(!roundOver && playerTurn);
		foldButton.setEnabled(
				!roundOver && playerTurn && !(players.get(0).getMoney() == 0 && !players.get(0).isBankrupt())
						&& !((players.get(1).hasFolded() || players.get(1).isBankrupt())
								&& (players.get(2).hasFolded() || players.get(2).isBankrupt())
								&& (players.get(3).hasFolded() || players.get(3).isBankrupt())));
		raiseButton.setEnabled(!roundOver && playerTurn && players.get(0).getMoney() > currentBet);
		nextButton.setEnabled(roundOver);
	}

	public String getDecision() {
		decision = "";
		decisionMade = false;
		while (!decisionMade) {
			try {
				Thread.sleep(200);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return decision;
	}

	public void awaitNextPhase() {
		nextPhaseReady = false;
		while (!nextPhaseReady) {
			try {
				Thread.sleep(200);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}