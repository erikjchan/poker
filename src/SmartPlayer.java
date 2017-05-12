import java.util.ArrayList;

/**
 * Class that represents a smart player of the game.
 * 
 * @author erik
 */
public class SmartPlayer extends ComputerPlayer {
	boolean trained = false;
	private Deck deck;
	private static int postRaiseFolds;
	private static int notPostRaiseFolds;

	private Player opponent;

	/**
	 * Constructor class.
	 * 
	 * @param name
	 *            the name of the computer player
	 */
	public SmartPlayer(String name, boolean trained) {
		super(name);
		this.trained = trained;
	}

	public String getDecision(ArrayList<Player> players, int currentBet, boolean isPreFlop, String currentPhase) {
		opponent = players.get(1);
		if (!getLastDecision().equals("call") && !getLastDecision().equals("fold") && !isPreFlop
				&& !currentPhase.equals("flop")) {
			if (opponent.getLastDecision().equals("fold")) {
				postRaiseFolds++;

			} else {
				notPostRaiseFolds++;
			}
		}

		if (trained && postRaiseFolds > notPostRaiseFolds) {
			// recognize as scared player

			if (isPreFlop) {
				return "call";

			} else if (currentBet == 0) {
				return "raise " + (currentBet + 1);

			} else {
				return "raise " + (int) (currentBet * 1.2);
			}

		} else if (trained) {
			// recognize as confident player
			return null;

		} else if (trained) {
			// recognize as calling player
			return null;

		} else if (isPreFlop) {
			return "call";

		} else {
			if (getHand().getScore() > getAverageScore() + 50) {
				return "raise " + (currentBet + getHand().getScore() - getAverageScore() - 50);

			} else if (getHand().getScore() > getAverageScore() - 50) {
				return "call";

			} else {
				return "fold";
			}
		}
	}

	/**
	 * Gets the average score of your opponent
	 * 
	 * @return the average score of your opponent
	 */
	public int getAverageScore() {
		deck = new Deck();

		// remove own cards from deck
		deck.removeCard(getFirstCard());
		deck.removeCard(getSecondCard());

		ArrayList<Card> temp = getCommunityCards();
		for (int i = 0; i < temp.size(); i++) {
			deck.removeCard(temp.get(i));
		}

		Hand opponentHand;
		int totalScore = 0;
		for (int i = 0; i < deck.getCards().size() - 1; i++) {
			opponentHand = new Hand(deck.getCards().get(i), deck.getCards().get(i), getCommunityCards());
			opponentHand.calculateScore();
			totalScore += opponentHand.getScore();
		}
		return totalScore / ((deck.getCards().size() - 1) * deck.getCards().size());
	}
}
