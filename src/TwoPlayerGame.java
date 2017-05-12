import java.util.ArrayList;

/**
 * Top-level class that represents a 2 computer-player Poker game.
 * 
 * @author echan
 */
public class TwoPlayerGame {
	private static int playerOneWins = 0;
	private static int playerTwoWins = 0;
	private static int ties = 0;
	boolean gameOver;
	private Deck deck;
	private int turn;
	private int currentBet;
	private int pot;
	private int smallBlind = 15;
	private int bigBlind = 30;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Card> communityCards = new ArrayList<Card>();

	public static void main(String args[]) {
		int numberGames = 10000;
		TwoPlayerGame game = new TwoPlayerGame();
		for (int i = 0; i < numberGames; i++) {
			game = new TwoPlayerGame();
			int numberRounds = 5;
			game.run(numberRounds);
		}
		System.out.println("P1: " + playerOneWins + ", P2: " + playerTwoWins + ", Ties: " + ties);
	}

	/**
	 * Constructor class.
	 */
	public TwoPlayerGame() {
		gameOver = false;
		players.add(new ConfidentPlayer("Steve"));
		players.add(new ConfidentPlayer("Bruce"));
	}

	/**
	 * Runs the game for n rounds.
	 * 
	 * @param n
	 *            the number of rounds to run the game
	 */
	public void run(int n) {
		for (int i = 0; i < n; i++) {
			// initialize the round with the start phase
			startPhase();

			// set initial bets in the pre-flop phase
			preFlop();

			// reveal three cards and run flop phase
			reveal(3);
			betPhase();

			// reveal one more card and run turn phase
			reveal(1);
			betPhase();

			// reveal one more card and run river phase
			reveal(1);
			betPhase();

			// determine the round winner and distribute the pot
			pot += players.get(0).getBet();
			players.get(0).setBet(0);

			pot += players.get(1).getBet();
			players.get(1).setBet(0);

			currentBet = 0;
			showdown();
		}

		// determine the game winner
		if (!gameOver) {
			endGame();
		}
	}

	/**
	 * Start a new round with a new deck and hands.
	 */
	public void startPhase() {
		deck = new Deck();

		// test for number of bankrupt people
		int numberBankrupt = 0;

		for (int i = 0; i < 2; i++) {
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

		// end game quickly if one player is bankrupt
		if (numberBankrupt == 1 && !gameOver) {
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

		// first active player pays small blind
		while (!smallBlindPaid) {
			players.get(turn % 2).setBet(smallBlind);
			smallBlindPaid = true;
		}

		// second active player pays big blind
		while (!bigBlindPaid) {
			players.get(turn % 2).setBet(bigBlind);
			bigBlindPaid = true;
			currentBet = bigBlind;
			turn++;
		}

		// each active player draws two cards
		for (int i = 0; i < 2; i++) {
			players.get(i).setFirstCard(deck.drawCard());
			players.get(i).setSecondCard(deck.drawCard());
		}

		// additional players pay big blind (or eventually choose to call or
		// fold)
		// TODO: figure out while loop logic
		int nturns = 0;
		while (((players.get(0).getBet() != currentBet && !players.get(0).hasFolded() && players.get(0).getMoney() != 0)
				|| (players.get(1).getBet() != currentBet && !players.get(1).hasFolded()
						&& players.get(1).getMoney() != 0))
				|| nturns < 3) {
			nturns++;

			// if bet has not changed for more than 2 turns
			if (nturns > 2 && currentBet == 0) {
				break;
			} else {
				if (turn % 2 == 0) {
					makeDecision(0, true);

				} else if (turn % 2 == 1) {
					makeDecision(1, true);
				}
				turn++;
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
		players.get(0).setCommunityCards(communityCards);
		players.get(0).updateHand(communityCards);

		players.get(1).setCommunityCards(communityCards);
		players.get(1).updateHand(communityCards);
	}

	/**
	 * Run intermediate bets.
	 */
	public void betPhase() {
		// add bets from previous phase
		pot += players.get(0).getBet();
		players.get(0).setBet(0);

		pot += players.get(1).getBet();
		players.get(1).setBet(0);

		currentBet = 0;
		int nturns = 0;
		// TODO: fix if all players have no money or if some went all in and
		// thus don't match bet; currently stuck when money = 0

		while (((players.get(0).getBet() != currentBet && !players.get(0).hasFolded() && players.get(0).getMoney() != 0)
				|| (players.get(1).getBet() != currentBet && !players.get(1).hasFolded()
						&& players.get(1).getMoney() != 0))
				|| nturns < 3) {
			nturns++;
			if (nturns > 2 && currentBet == 0) {
				break;
			}
			if (!players.get(turn % 2).hasFolded()) {
				if (turn % 2 == 0) {
					makeDecision(0, false);

				} else if (turn % 2 == 1) {
					makeDecision(1, false);
				}
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
		for (int i = 0; i < 2; i++) {
			if (!players.get(i).hasFolded()) {
				if (players.get(i).getHand().getScore() > max) {
					max = players.get(i).getHand().getScore();
				}
			}
		}

		int countMax = 0;
		for (int i = 0; i < 2; i++) {
			if (!players.get(i).hasFolded()) {
				if (players.get(i).getHand().getScore() == max) {
					countMax++;
				}
			}
		}

		for (int i = 0; i < 2; i++) {
			if (!players.get(i).hasFolded()) {
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
		gameOver = true;

		ArrayList<String> winners = new ArrayList<String>();
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < 2; i++) {
			if (players.get(i).getMoney() > max) {
				max = players.get(i).getMoney();
			}
		}

		if (players.get(0).getMoney() == max && players.get(1).getMoney() == max) {
			ties++;
			winners.add(players.get(0).getName());
			winners.add(players.get(1).getName());

		} else if (players.get(0).getMoney() == max) {
			playerOneWins++;
			winners.add(players.get(0).getName());

		} else if (players.get(1).getMoney() == max) {
			playerTwoWins++;
			winners.add(players.get(1).getName());
		}
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

		} else if (players.get(i) instanceof ConfidentPlayer) {
			decision = ((ConfidentPlayer) players.get(i)).getDecision(players, currentBet, isPreflop);

		} else if (players.get(i) instanceof ScaredPlayer) {
			decision = ((ScaredPlayer) players.get(i)).getDecision(players, currentBet, isPreflop);

		} else if (players.get(i) instanceof CallingPlayer) {
			decision = ((CallingPlayer) players.get(i)).getDecision(players, currentBet, isPreflop);

		} else if (players.get(i) instanceof RationalPlayer) {
			decision = ((RationalPlayer) players.get(i)).getDecision(players, currentBet, isPreflop);
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