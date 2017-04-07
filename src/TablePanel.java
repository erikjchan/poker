import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TablePanel extends JPanel {
	public static final int HEIGHT = 650;
	public static final int WIDTH = 1000;
	private ArrayList<Player> players;
	private ArrayList<Card> communityCards;
	int pot;
	int currentBet;
	boolean roundOver;
	boolean clicked;

	/**
	 * Constructor class.
	 */
	public TablePanel(ArrayList<Player> players, ArrayList<Card> communityCards, int pot, int currentBet) {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.players = players;
		this.communityCards = communityCards;
		// console to write actions
	}

	/*
	 * paint this square using g. System calls paint whenever square has to be
	 * redrawn.
	 */
	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 200, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// game information
		g.setColor(Color.white);
		g.fillRect(430, 25, 140, 100);
		g.setColor(Color.black);
		g.drawRect(430, 25, 140, 100);
		g.drawString("Pot: " + pot, 450, 50);
		g.drawString("Current Bet: " + currentBet, 450, 70);
		g.drawString("Small Blind: 15", 450, 90);
		g.drawString("Big Blind: 30", 450, 110);

		// community cards
		g.setColor(Color.black);
		g.drawRect(250, 225, 500, 150);
		g.drawString("Community Cards", 250, 225 - 10);
		try {
			if (communityCards.isEmpty()) {
				g.drawImage(ImageIO.read(new File("img/back.png")), 265, 225 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 365, 225 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 465, 225 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 565, 225 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 665, 225 + 25, this);

			} else if (communityCards.size() == 3) {
				g.drawImage(ImageIO.read(new File(communityCards.get(0).getPath())), 265, 225 + 25, this);
				g.drawImage(ImageIO.read(new File(communityCards.get(1).getPath())), 365, 225 + 25, this);
				g.drawImage(ImageIO.read(new File(communityCards.get(2).getPath())), 465, 225 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 565, 225 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 665, 225 + 25, this);

			} else if (communityCards.size() == 4) {
				g.drawImage(ImageIO.read(new File(communityCards.get(0).getPath())), 265, 225 + 25, this);
				g.drawImage(ImageIO.read(new File(communityCards.get(1).getPath())), 365, 225 + 25, this);
				g.drawImage(ImageIO.read(new File(communityCards.get(2).getPath())), 465, 225 + 25, this);
				g.drawImage(ImageIO.read(new File(communityCards.get(3).getPath())), 565, 225 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 665, 225 + 25, this);

			} else if (communityCards.size() == 5) {
				g.drawImage(ImageIO.read(new File(communityCards.get(0).getPath())), 265, 225 + 25, this);
				g.drawImage(ImageIO.read(new File(communityCards.get(1).getPath())), 365, 225 + 25, this);
				g.drawImage(ImageIO.read(new File(communityCards.get(2).getPath())), 465, 225 + 25, this);
				g.drawImage(ImageIO.read(new File(communityCards.get(3).getPath())), 565, 225 + 25, this);
				g.drawImage(ImageIO.read(new File(communityCards.get(4).getPath())), 665, 225 + 25, this);
			}

			// player 1
			g.setColor(Color.black);
			g.drawRect(25, 25, 200, 150);
			g.drawString(players.get(0).getName() + " (YOU)", 25, 25 - 10);
			if (players.get(0).isBankrupt()) {
				g.drawString("Money: BANKRUPT", 25, 175 + 15);

			} else {
				g.drawString("Money: " + players.get(0).getMoney(), 25, 175 + 15);
				g.drawString("Bet: " + players.get(0).getBet(), 25, 175 + 30);
			}
			if (!players.get(0).isBankrupt()) {
				if (players.get(0).getFirstCard() != null) {
					g.drawImage(ImageIO.read(new File(players.get(0).getFirstCard().getPath())), 40, 25 + 25, this);
					g.drawImage(ImageIO.read(new File(players.get(0).getSecondCard().getPath())), 140, 25 + 25, this);
				}
				if (!communityCards.isEmpty()) {
					g.drawString("Hand: " + players.get(0).getHand().getHandType(), 25, 175 + 45);
					g.drawString("Score: " + players.get(0).getHand().getScore(), 25, 175 + 60);
				}
			}

			// player 2
			g.setColor(Color.black);
			g.drawRect(775, 25, 200, 150);
			g.drawString(players.get(1).getName(), 775, 25 - 10);
			if (players.get(1).isBankrupt()) {
				g.drawString("Money: BANKRUPT", 775, 175 + 15);

			} else {
				g.drawString("Money: " + players.get(1).getMoney(), 775, 175 + 15);
				g.drawString("Bet: " + players.get(1).getBet(), 775, 175 + 30);
				if (players.get(1).getFirstCard() == null) {

				} else if (!roundOver) {
					g.drawImage(ImageIO.read(new File("img/back.png")), 790, 25 + 25, this);
					g.drawImage(ImageIO.read(new File("img/back.png")), 890, 25 + 25, this);

				} else {
					g.drawImage(ImageIO.read(new File(players.get(1).getFirstCard().getPath())), 790, 25 + 25, this);
					g.drawImage(ImageIO.read(new File(players.get(1).getSecondCard().getPath())), 890, 25 + 25, this);
					g.drawString("Hand: " + players.get(1).getHand().getHandType(), 775, 175 + 45);
					g.drawString("Score: " + players.get(1).getHand().getScore(), 775, 175 + 60);
				}
			}

			// player 3
			g.setColor(Color.black);
			g.drawRect(775, 425, 200, 150);
			g.drawString(players.get(2).getName(), 775, 425 - 10);
			if (players.get(2).isBankrupt()) {
				g.drawString("Money: BANKRUPT", 775, 575 + 15);

			} else {
				g.drawString("Money: " + players.get(2).getMoney(), 775, 575 + 15);
				g.drawString("Bet: " + players.get(2).getBet(), 775, 575 + 30);
				if (players.get(2).getFirstCard() == null) {

				} else if (!roundOver) {
					g.drawImage(ImageIO.read(new File("img/back.png")), 790, 425 + 25, this);
					g.drawImage(ImageIO.read(new File("img/back.png")), 890, 425 + 25, this);

				} else {
					g.drawImage(ImageIO.read(new File(players.get(2).getFirstCard().getPath())), 790, 425 + 25, this);
					g.drawImage(ImageIO.read(new File(players.get(2).getSecondCard().getPath())), 890, 425 + 25, this);
					g.drawString("Hand: " + players.get(2).getHand().getHandType(), 775, 575 + 45);
					g.drawString("Score: " + players.get(2).getHand().getScore(), 775, 575 + 60);
				}
			}

			// player 4
			g.setColor(Color.black);
			g.drawRect(25, 425, 200, 150);
			g.drawString(players.get(3).getName(), 25, 425 - 10);
			if (players.get(3).isBankrupt()) {
				g.drawString("Money: BANKRUPT", 25, 575 + 15);

			} else {
				g.drawString("Money: " + players.get(3).getMoney(), 25, 575 + 15);
				g.drawString("Bet: " + players.get(3).getBet(), 25, 575 + 30);
				if (players.get(3).getFirstCard() == null) {

				} else if (!roundOver) {
					g.drawImage(ImageIO.read(new File("img/back.png")), 40, 425 + 25, this);
					g.drawImage(ImageIO.read(new File("img/back.png")), 140, 425 + 25, this);

				} else {
					g.drawImage(ImageIO.read(new File(players.get(3).getFirstCard().getPath())), 40, 425 + 25, this);
					g.drawImage(ImageIO.read(new File(players.get(3).getSecondCard().getPath())), 140, 425 + 25, this);
					g.drawString("Hand: " + players.get(3).getHand().getHandType(), 25, 575 + 45);
					g.drawString("Score: " + players.get(3).getHand().getScore(), 25, 575 + 60);
				}
			}

		} catch (

		IOException ex) {
			System.out.println("Invalid card.");
		}

	}

	public void updateRoundOver(boolean roundOver) {
		this.roundOver = roundOver;
	}

	public void update(ArrayList<Player> players, ArrayList<Card> communityCards, int pot, int currentBet) {
		this.players = players;
		this.communityCards = communityCards;
		this.pot = pot;
		this.currentBet = currentBet;
	}

}