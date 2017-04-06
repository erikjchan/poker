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
	private int pot;
	private JButton callButton = new JButton("Call");
	private JButton foldButton = new JButton("Fold");
	private JButton raiseButton = new JButton("Raise");
	private JButton nextButton = new JButton("Next Phase");
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
		b.add(Box.createRigidArea(new Dimension(340,0)));
		b.add(callButton);
		b.add(Box.createRigidArea(new Dimension(50,0)));
		b.add(foldButton);
		b.add(Box.createRigidArea(new Dimension(50,0)));
		b.add(raiseButton);
		b.add(Box.createRigidArea(new Dimension(200,0)));
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
		} else if (e.getSource() == foldButton) {
			System.out.println("Fold clicked");
		} else if (e.getSource() == raiseButton) {
			System.out.println("Raise clicked");
		} else if (e.getSource() == nextButton) {
			System.out.println("Next clicked");
			clicked = true;
		}
	}

	public void updatePlayers(ArrayList<Player> players) {
		this.tablePanel.updatePlayers(players);
	}

	public void updateCommunityCards(ArrayList<Card> communityCards) {
		this.tablePanel.updateCommunityCards(communityCards);
	}

	public void updateRound(int currentRound, int numberRounds, String phase, boolean roundOver) {
		this.roundPanel.updateRounds(currentRound, numberRounds, phase);
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