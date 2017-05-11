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
		int numberRounds = 0;
		boolean numberRoundsValid = false;
		while (!numberRoundsValid) {
			try {
				numberRounds = Integer.parseInt(
						JOptionPane.showInputDialog("Please enter the number of rounds you would like to play: "));
				if (numberRounds > 0) {
					numberRoundsValid = true;
				} else {
					JOptionPane.showMessageDialog(null, "The number has to be positive. Please try again.");
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "The number was invalid. Please try again.");
			}
		}
		game.run(numberRounds);

	}

	/**
	 * Constructor class.
	 */
	public Game() {
		players.add(new HumanPlayer("Tony"));
		players.add(new RandomPlayer("Bruce"));
		players.add(new ComputerPlayer("Thor"));
		players.add(new DefensivePlayer("Steve"));
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
			table.updateButtons(players, currentBet, false, false);
			updateAndRepaint();
			startPhase();
			updateAndRepaint();
			table.updateButtons(players, currentBet, true, false);
			table.awaitNextPhase();

			// set initial bets
			table.updateRound(i + 1, n, "Pre-Flop", false);
			table.updateButtons(players, currentBet, false, false);
			updateAndRepaint();
			preFlop();
			updateAndRepaint();
			table.updateButtons(players, currentBet, true, false);
			table.awaitNextPhase();

			// reveal three cards and run flop phase
			reveal(3);
			table.updateRound(i + 1, n, "Flop", false);
			table.updateButtons(players, currentBet, false, false);
			updateAndRepaint();
			betPhase();
			updateAndRepaint();
			table.updateButtons(players, currentBet, true, false);
			table.awaitNextPhase();

			// reveal one more card and run turn phase
			reveal(1);
			table.updateRound(i + 1, n, "Turn", false);
			table.updateButtons(players, currentBet, false, false);
			updateAndRepaint();
			betPhase();
			updateAndRepaint();
			table.updateButtons(players, currentBet, true, false);
			table.awaitNextPhase();

			// reveal one more card and run river phase
			reveal(1);
			table.updateRound(i + 1, n, "River", false);
			table.updateButtons(players, currentBet, false, false);
			updateAndRepaint();
			betPhase();
			updateAndRepaint();
			table.updateButtons(players, currentBet, true, false);
			table.awaitNextPhase();

			// determine the round winner and distribute the pot
			table.updateRound(i + 1, n, "Showdown", true);
			for (int j = 0; j < 4; j++) {
				pot += players.get(j).getBet();
				players.get(j).setBet(0);
			}
			currentBet = 0;
			updateAndRepaint();
			table.updateButtons(players, currentBet, true, false);
			table.awaitNextPhase();
			table.updateRound(i + 1, n, "Distribute Pot", true);
			updateAndRepaint();
			showdown();
			updateAndRepaint();
			table.updateButtons(players, currentBet, true, false);
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
			updateAndRepaint();
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

		// each active player draws two cards
		table.updateButtons(players, currentBet, false, false);
		for (int i = 0; i < 4; i++) {
			if (!players.get(i).isBankrupt()) {
				players.get(i).setFirstCard(deck.drawCard());
				players.get(i).setSecondCard(deck.drawCard());
				updateAndRepaint();
				pause(1);
			}
		}

		// additional players pay big blind (or eventually choose to call or
		// fold)
		// TODO: figure out while loop logic

		while (((players.get(0).getBet() != currentBet && !players.get(0).isBankrupt() && !players.get(0).hasFolded()
				&& players.get(0).getMoney() != 0))
				|| ((players.get(1).getBet() != currentBet && !players.get(1).isBankrupt()
						&& !players.get(1).hasFolded() && players.get(1).getMoney() != 0))
				|| ((players.get(2).getBet() != currentBet && !players.get(2).isBankrupt()
						&& !players.get(2).hasFolded() && players.get(2).getMoney() != 0))
				|| ((players.get(3).getBet() != currentBet && !players.get(3).isBankrupt()
						&& !players.get(3).hasFolded() && players.get(3).getMoney() != 0))) {
			if (!players.get(turn % 4).isBankrupt() || turn % 4 == 0) {
				if (turn % 4 == 0) {
					if (!players.get(0).isBankrupt()) {
						table.updateButtons(players, currentBet, false, true);
						String decision = table.getDecision();
						table.updateButtons(players, currentBet, false, false);
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
				} else if (turn % 4 == 1) {
					// TODO: bet or set as folded
					makeDecision(1, true);

				} else if (turn % 4 == 3) {
					makeDecision(3, true);

				} else {
					players.get(turn % 4).setBet(currentBet);
				}
				updateAndRepaint();
				pause(1);
			}
			turn++;
			updateAndRepaint();
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
		int nturns = 0;
		// TODO: fix if all players have no money or if some went all in and
		// thus don't match bet; currently stuck when money = 0
		while (((players.get(0).getBet() != currentBet && !players.get(0).isBankrupt() && !players.get(0).hasFolded()
				&& players.get(0).getMoney() != 0))
				|| ((players.get(1).getBet() != currentBet && !players.get(1).isBankrupt()
						&& !players.get(1).hasFolded() && players.get(1).getMoney() != 0))
				|| ((players.get(2).getBet() != currentBet && !players.get(2).isBankrupt()
						&& !players.get(2).hasFolded() && players.get(2).getMoney() != 0))
				|| ((players.get(3).getBet() != currentBet && !players.get(3).isBankrupt()
						&& !players.get(3).hasFolded() && players.get(3).getMoney() != 0))
				|| nturns < 5) {
			nturns++;
			if (nturns > 4 && currentBet == 0) {
				break;
			}
			if ((!players.get(turn % 4).isBankrupt() && !players.get(turn % 4).hasFolded()) || turn % 4 == 0) {
				if (turn % 4 == 0) {
					if (!players.get(0).isBankrupt() && !players.get(0).hasFolded()) {
						table.updateButtons(players, currentBet, false, true);
						String decision = table.getDecision();
						table.updateButtons(players, currentBet, false, false);
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
				} else if (turn % 4 == 1) {
					makeDecision(1, false);

				} else if (turn % 4 == 3) {
					makeDecision(3, false);

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
			updateAndRepaint();
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

	/**
	 * Update the table panel and repaint it.
	 */
	public void updateAndRepaint() {
		table.updateTablePanel(players, communityCards, pot, currentBet, turn);
		table.getContentPane().repaint();
	}

	/**
	 * Make the player's decision.
	 * 
	 * @param i
	 *            the player making the decision
	 * @param decision
	 *            the decision the player is making
	 * @param isPreflop
	 *            whether the round is in preflop
	 */
	public void makeDecision(int i, boolean isPreflop) {
		String decision = "";
		if (players.get(i) instanceof DefensivePlayer) {
			decision = ((DefensivePlayer) players.get(i)).getDecision(players, currentBet, isPreflop);

		} else if (players.get(i) instanceof RandomPlayer) {
			decision = ((RandomPlayer) players.get(i)).getDecision(players, currentBet);
		}

		players.get(i).setLastDecision(decision);

		if (decision.equals("call")) {
			players.get(i).setBet(currentBet);

		} else if (decision.equals("fold")) {
			players.get(i).setFolded(true);

		} else {
			String[] decisionArray = decision.split(" ");
			int raisedBet = Integer.parseInt(decisionArray[1]);
			players.get(i).setBet(raisedBet);
			currentBet = players.get(i).getBet();
		}
	}

}

// one pair tie breaker is to look at the kicker card.