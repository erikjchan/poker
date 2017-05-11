import java.util.ArrayList;

/**
 * Class that represents a smart player of the game.
 * 
 * @author erik
 */
public class SmartPlayer extends ComputerPlayer {
	private Deck deck;
	private Player opponent;

	/**
	 * Constructor class.
	 * 
	 * @param name
	 *            the name of the computer player
	 */
	public SmartPlayer(String name) {
		super(name);
	}

	public String getDecision(ArrayList<Player> players, int currentBet) {
		this.opponent = players.get(1);

		if (getHand().getScore() > getAverageScore() + 100) {
			return "raise" + (getHand().getScore() - getHand().getScore() - 100);

		} else if (getHand().getScore() > getAverageScore() - 100) {
			return "call";

		} else {
			return "fold";
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
		deck.removeCards(getCommunityCards());

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
