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
			System.out.println("Call clicked");
			decisionMade = true;

		} else if (e.getSource() == foldButton) {
			System.out.println("Fold clicked");
			decisionMade = true;

		} else if (e.getSource() == raiseButton) {
			System.out.println("Raise clicked");
			decisionMade = true;

		} else if (e.getSource() == nextButton) {
			System.out.println("Next clicked");
			nextPhaseReady = true;
		}
	}

	public void updateTablePanel(ArrayList<Player> players, ArrayList<Card> communityCards, int pot, int currentBet) {
		this.tablePanel.update(players, communityCards, pot, currentBet);
	}

	public void updateRound(int currentRound, int numberRounds, String phase, boolean roundOver) {
		this.roundPanel.updateRounds(currentRound, numberRounds, phase);
		this.tablePanel.updateRoundOver(roundOver);
	}

	public void updateButtons(Player player, int currentBet, boolean roundOver) {
		callButton.setEnabled(!roundOver);
		foldButton.setEnabled(!roundOver);
		raiseButton.setEnabled(!roundOver && player.getMoney() > currentBet);
		nextButton.setEnabled(roundOver);
	}

	public void awaitDecision() {
		decisionMade = false;
		while (!decisionMade) {
			try {
				Thread.sleep(200);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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