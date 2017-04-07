import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Top-level class that represents the Poker game.
 * 
 * @author echan
 */
public class Game {
	private static Table table; // GUI
	private Deck deck;
	private int turn;
	private static int currentBet;
	private static int pot;
	private int smallBlind = 5;
	private int bigBlind = 10;
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static ArrayList<Card> communityCards = new ArrayList<Card>();

	public static void main(String args[]) {
		Game game = new Game();
		table = new Table(players, communityCards, pot, currentBet);
		table.setTitle("Poker");
		game.run(5);
	}

	/**
	 * Constructor class.
	 */
	public Game() {
		players.add(new HumanPlayer("Tony"));
		players.add(new ComputerPlayer("Bruce"));
		players.add(new ComputerPlayer("Thor"));
		players.add(new ComputerPlayer("Steve"));
	}

	/**
	 * Runs the game for n rounds.
	 * 
	 * @param n
	 *            the number of rounds to run the game
	 */
	public void run(int n) {
		for (int i = 0; i < n; i++) {
			// initialize the round
			startPhase();
			table.updateRound(i + 1, n, "Start Phase", false);
			table.updateTablePanel(players, communityCards, pot, currentBet);
			table.getContentPane().repaint();
			table.updateButtons(players.get(0), currentBet, true);
			table.awaitNextPhase();

			// set initial bets
			preFlop();
			table.updateRound(i + 1, n, "Pre-Flop", false);
			table.updateTablePanel(players, communityCards, pot, currentBet);
			table.getContentPane().repaint();
			table.updateButtons(players.get(0), currentBet, true);
			table.awaitNextPhase();

			// reveal three cards and run flop round
			reveal(3);
			betPhase();
			table.updateRound(i + 1, n, "Flop", false);
			table.updateTablePanel(players, communityCards, pot, currentBet);
			table.getContentPane().repaint();
			table.updateButtons(players.get(0), currentBet, true);
			table.awaitNextPhase();

			// reveal one more card and run turn round
			reveal(1);
			betPhase();
			table.updateRound(i + 1, n, "Turn", false);
			table.updateTablePanel(players, communityCards, pot, currentBet);
			table.getContentPane().repaint();
			table.updateButtons(players.get(0), currentBet, true);
			table.awaitNextPhase();

			// reveal one more card and run river round
			reveal(1);
			betPhase();
			table.updateRound(i + 1, n, "River", false);
			table.updateTablePanel(players, communityCards, pot, currentBet);
			table.getContentPane().repaint();
			table.updateButtons(players.get(0), currentBet, true);
			table.awaitNextPhase();

			// determine the round winner and distribute the pot
			table.updateRound(i + 1, n, "Showdown", true);
			table.getContentPane().repaint();
			table.awaitNextPhase();
			table.updateRound(i + 1, n, "Distribute Pot", true);
			showdown();
			table.updateTablePanel(players, communityCards, pot, currentBet);
			table.getContentPane().repaint();
			table.updateButtons(players.get(0), currentBet, true);
			table.awaitNextPhase();
		}

		// determine the game winner
		endGame();
	}

	/**
	 * Start a new round with a new deck and hands.
	 */
	public void startPhase() {
		deck = new Deck();

		// test for number of bankrupt people
		int numberBankrupt = 0;
		for (int i = 0; i < 4; i++) {
			if (players.get(i).getMoney() == 0) {
				numberBankrupt++;
			}
			// Resets players hands and the community cards
			players.get(i).setFolded(false);
			communityCards = new ArrayList<Card>();
			players.get(i).setFirstCard(null);
			players.get(i).setSecondCard(null);
			players.get(i).setHand(null);
			players.get(i).setBet(0);
		}

		// end game quickly if three players go bankrupt
		if (numberBankrupt == 3) {
			endGame();
		}
	}

	/**
	 * Run the start bets.
	 */
	public void preFlop() {
		// first player pays small blind
		players.get(turn % 4).setBet(smallBlind);
		turn++;

		// additional players pay big blind (or eventually choose to call or
		// fold)
		for (int i = 0; i < 3; i++) {
			players.get(turn % 4).setBet(bigBlind);
			turn++;
		}
		currentBet = bigBlind;

		// each player draws two cards
		for (int i = 0; i < 4; i++) {
			players.get(i).setFirstCard(deck.drawCard());
			players.get(i).setSecondCard(deck.drawCard());
		}
	}

	/**
	 * Reveal n cards to all the players.
	 * 
	 * @param n
	 *            the number of cards revealed to all players
	 */
	public void reveal(int n) {
		communityCards.addAll(deck.drawCards(n));
		for (int i = 0; i < 4; i++) {
			players.get(i).setCommunityCards(communityCards);
			players.get(i).updateHand(communityCards);
		}
	}

	/**
	 * Run intermediate bets.
	 */
	public void betPhase() {
		// add bets from previous phase
		for (int i = 0; i < 4; i++) {
			pot += players.get(i).getBet();
			players.get(i).setBet(0);
		}
		currentBet = 0;

		table.updateButtons(players.get(0), currentBet, false);
		table.awaitDecision();
		players.get(0).setBet(bigBlind);

		for (int i = 1; i < 4; i++) {
			// ask for bet from players who have not folded
			// AI LOGIC; fold, call or bet
			players.get(i).setBet(bigBlind);
			turn++;
		}
		currentBet = bigBlind;
	}

	/**
	 * End the round, and determine the round winner.
	 */
	public void showdown() {
		// if n players have same score, split the plot n ways
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < 4; i++) {
			if (!players.get(i).hasFolded()) {
				if (players.get(i).getHand().getScore() > max) {
					max = players.get(i).getHand().getScore();
				}
			}
		}

		int countMax = 0;
		for (int i = 0; i < 4; i++) {
			if (!players.get(i).hasFolded()) {
				if (players.get(i).getHand().getScore() == max) {
					countMax++;
				}
			}
		}

		for (int i = 0; i < 4; i++) {
			if (!players.get(i).hasFolded()) {
				if (players.get(i).getHand().getScore() == max) {
					players.get(i).setMoney(players.get(i).getMoney() + pot / countMax);
				}
			}
		}
		pot = 0;
	}

	/**
	 * End the game, and determine the game winner.
	 */
	public void endGame() {
		ArrayList<String> winners = new ArrayList<String>();
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < 4; i++) {
			if (players.get(i).getMoney() > max) {
				max = players.get(i).getMoney();
			}
		}

		for (int i = 0; i < 4; i++) {
			if (players.get(i).getMoney() == max) {
				winners.add(players.get(i).getName());
			}
		}

		if (winners.size() == 1) {
			JOptionPane.showMessageDialog(null, "Game complete. The winner is " + winners.get(0) + ".");

		} else if (winners.size() == 2) {
			JOptionPane.showMessageDialog(null,
					"Game complete. The winners are " + winners.get(0) + " and " + winners.get(1) + ".");

		} else if (winners.size() == 3) {
			JOptionPane.showMessageDialog(null, "Game complete. The winners are " + winners.get(0) + ", "
					+ winners.get(1) + ", and " + winners.get(2) + ".");

		} else if (winners.size() == 4) {
			JOptionPane.showMessageDialog(null, "Game complete. All four players tied.");
		}

		table.setVisible(false);
		System.exit(0);
	}

	/**
	 * Pause the game for a certain number of seconds.
	 * 
	 * @param time
	 *            the number of seconds to pause the game
	 */
	public void pause(int time) {
		try {
			Thread.sleep(time * 1000);

		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

}

// one pair tie breaker is to look at the kicker card.