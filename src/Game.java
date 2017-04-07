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
	private int smallBlind = 15;
	private int bigBlind = 30;
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
			table.updateRound(i + 1, n, "Start Phase", false);
			table.updateButtons(players.get(0), currentBet, false, false);
			updateAndRepaint();
			startPhase();
			updateAndRepaint();
			table.updateButtons(players.get(0), currentBet, true, false);
			table.awaitNextPhase();

			// set initial bets
			table.updateRound(i + 1, n, "Pre-Flop", false);
			table.updateButtons(players.get(0), currentBet, false, false);
			updateAndRepaint();
			preFlop();
			updateAndRepaint();
			table.updateButtons(players.get(0), currentBet, true, false);
			table.awaitNextPhase();

			// reveal three cards and run flop round
			reveal(3);
			table.updateRound(i + 1, n, "Flop", false);
			table.updateButtons(players.get(0), currentBet, false, false);
			System.out.println("testa");
			updateAndRepaint();
			System.out.println("testb");
			betPhase();
			System.out.println("testc");
			updateAndRepaint();
			table.updateButtons(players.get(0), currentBet, true, false);
			table.awaitNextPhase();

			// reveal one more card and run turn round
			reveal(1);
			table.updateRound(i + 1, n, "Turn", false);
			table.updateButtons(players.get(0), currentBet, false, false);
			updateAndRepaint();
			betPhase();
			updateAndRepaint();
			table.updateButtons(players.get(0), currentBet, true, false);
			table.awaitNextPhase();

			// reveal one more card and run river round
			reveal(1);
			table.updateRound(i + 1, n, "River", false);
			table.updateButtons(players.get(0), currentBet, false, false);
			updateAndRepaint();
			betPhase();
			updateAndRepaint();
			table.updateButtons(players.get(0), currentBet, true, false);
			table.awaitNextPhase();

			// determine the round winner and distribute the pot
			table.updateRound(i + 1, n, "Showdown", true);
			for (int j = 0; j < 4; j++) {
				pot += players.get(j).getBet();
				players.get(j).setBet(0);
			}
			currentBet = 0;
			updateAndRepaint();
			table.updateButtons(players.get(0), currentBet, true, false);
			table.awaitNextPhase();
			table.updateRound(i + 1, n, "Distribute Pot", true);
			updateAndRepaint();
			showdown();
			updateAndRepaint();
			table.updateButtons(players.get(0), currentBet, true, false);
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
			if (players.get(i).isBankrupt()) {
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
		boolean smallBlindPaid = false;
		boolean bigBlindPaid = false;
		currentBet = 0;
		updateAndRepaint();

		// first active player pays small blind
		while (!smallBlindPaid) {
			if (!players.get(turn % 4).isBankrupt()) {
				players.get(turn % 4).setBet(smallBlind);
				smallBlindPaid = true;
				updateAndRepaint();
				pause(1);
			}
			turn++;
		}

		// second active player pays big blind
		while (!bigBlindPaid) {
			if (!players.get(turn % 4).isBankrupt()) {
				players.get(turn % 4).setBet(bigBlind);
				bigBlindPaid = true;
				currentBet = bigBlind;
				updateAndRepaint();
				pause(1);
			}
			turn++;
		}

		// additional players pay big blind (or eventually choose to call or
		// fold)
		// TODO: figure out while loop logic
		while (((players.get(0).getBet() != currentBet && !players.get(0).isBankrupt()) || players.get(0).isBankrupt())
				|| ((players.get(1).getBet() != currentBet && !players.get(1).isBankrupt())
						|| players.get(1).isBankrupt())
				|| ((players.get(2).getBet() != currentBet && !players.get(2).isBankrupt())
						|| players.get(2).isBankrupt())
				|| ((players.get(3).getBet() != currentBet && !players.get(3).isBankrupt())
						|| players.get(3).isBankrupt())) {
			System.out.println(players.get(0).getBet() != currentBet);
			System.out.println(players.get(0).getMoney() != 0);
			System.out.println(!players.get(0).isBankrupt());
			System.out.println("fail");
			if (!players.get(turn % 4).isBankrupt() || turn % 4 == 0) {
				if (turn % 4 == 0) {
					if (!players.get(0).isBankrupt()) {
						table.updateButtons(players.get(0), currentBet, false, true);
						String decision = table.getDecision();
						table.updateButtons(players.get(0), currentBet, false, false);
						if (decision.equals("call")) {
							players.get(0).setBet(currentBet);

						} else if (decision.equals("fold")) {
							players.get(0).setFolded(true);

						} else if (decision.equals("raise")) {
							boolean raiseValid = false;
							while (!raiseValid) {
								try {
									int raisedBet = Integer
											.parseInt(JOptionPane.showInputDialog("Please input a value: "));
									if (raisedBet > currentBet) {
										players.get(0).setBet(raisedBet);
										currentBet = players.get(0).getBet();
										raiseValid = true;
									} else {
										JOptionPane.showMessageDialog(null, "The raise was invalid. Please try again.");
									}
								} catch (Exception e) {
									JOptionPane.showMessageDialog(null, "The raise was invalid. Please try again.");
								}
							}
						}
					}
				} else {
					// TODO: bet or set as folded
					players.get(turn % 4).setBet(currentBet);
				}
				updateAndRepaint();
				pause(1);
			}
			turn++;
		}
		System.out.println("fail2");
		currentBet = bigBlind;

		// each active player draws two cards
		table.updateButtons(players.get(0), currentBet, false, false);
		for (int i = 0; i < 4; i++) {
			if (!players.get(i).isBankrupt()) {
				players.get(i).setFirstCard(deck.drawCard());
				players.get(i).setSecondCard(deck.drawCard());
				updateAndRepaint();
				pause(1);
			}
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
			if (!players.get(i).isBankrupt()) {
				players.get(i).setCommunityCards(communityCards);
				players.get(i).updateHand(communityCards);
			}
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
		updateAndRepaint();
		pause(1);
		// TODO: fix if all players have no money or if some went all in and
		// thus don't match bet; currently stuck when money = 0
		while (currentBet == 0
				|| ((players.get(0).getBet() != currentBet && !players.get(0).isBankrupt()
						&& !players.get(0).hasFolded()) || players.get(0).isBankrupt()  || players.get(0).isBankrupt())
				|| ((players.get(1).getBet() != currentBet && !players.get(1).isBankrupt()
						&& !players.get(1).hasFolded()) || players.get(1).isBankrupt())
				|| ((players.get(2).getBet() != currentBet && !players.get(2).isBankrupt()
						&& !players.get(2).hasFolded()) || players.get(2).isBankrupt())
				|| ((players.get(3).getBet() != currentBet && !players.get(3).isBankrupt()
						&& !players.get(3).hasFolded()) || players.get(3).isBankrupt())) {
			if ((!players.get(turn % 4).isBankrupt() && !players.get(turn % 4).hasFolded()) || turn % 4 == 0) {
				if (turn % 4 == 0) {
					if (!players.get(0).isBankrupt() && !players.get(0).hasFolded()) {
						table.updateButtons(players.get(0), currentBet, false, true);
						String decision = table.getDecision();
						table.updateButtons(players.get(0), currentBet, false, false);
						if (decision.equals("call")) {
							players.get(0).setBet(currentBet);
						} else if (decision.equals("fold")) {
							players.get(0).setFolded(true);
						} else if (decision.equals("raise")) {
							boolean raiseValid = false;
							while (!raiseValid) {
								try {
									int raisedBet = Integer
											.parseInt(JOptionPane.showInputDialog("Please input a value: "));
									if (raisedBet > currentBet) {
										players.get(0).setBet(raisedBet);
										currentBet = players.get(0).getBet();
										raiseValid = true;
									} else {
										JOptionPane.showMessageDialog(null, "The raise was invalid. Please try again.");
									}
								} catch (Exception e) {
									JOptionPane.showMessageDialog(null, "The raise was invalid. Please try again.");
								}
							}
						}
					}
				} else {
					if (currentBet == 0) {
						players.get(turn % 4).setBet(bigBlind);
						currentBet = bigBlind;
					} else {
						players.get(turn % 4).setBet(currentBet);
					}
				}
				updateAndRepaint();
				pause(1);
			}
			turn++;
		}

	}

	/**
	 * End the round, and determine the round winner.
	 */
	public void showdown() {
		// if n players have same score, split the plot n ways
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < 4; i++) {
			if (!players.get(i).isBankrupt() && !players.get(i).hasFolded()) {
				if (players.get(i).getHand().getScore() > max) {
					max = players.get(i).getHand().getScore();
				}
			}
		}

		int countMax = 0;
		for (int i = 0; i < 4; i++) {
			if (!players.get(i).isBankrupt() && !players.get(i).hasFolded()) {
				if (players.get(i).getHand().getScore() == max) {
					countMax++;
				}
			}
		}

		for (int i = 0; i < 4; i++) {
			if (!players.get(i).isBankrupt() && !players.get(i).hasFolded()) {
				if (players.get(i).getHand().getScore() == max) {
					players.get(i).setMoney(players.get(i).getMoney() + pot / countMax);
				}
			}
			if (players.get(i).getMoney() == 0) {
				players.get(i).setBankrupt(true);
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
	public void pause(double time) {
		try {
			Thread.sleep((int) time * 1000);

		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public void updateAndRepaint() {
		table.updateTablePanel(players, communityCards, pot, currentBet);
		table.getContentPane().repaint();
	}

}

// one pair tie breaker is to look at the kicker card.