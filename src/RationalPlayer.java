import java.util.ArrayList;

/**
 * Class that represents a rational player of the game.
 * 
 * @author erik
 */
public class RationalPlayer extends ComputerPlayer {
	private Deck deck;

	/**
	 * Constructor class.
	 * 
	 * @param name
	 *            the name of the computer player
	 */
	public RationalPlayer(String name) {
		super(name);
	}

	public String getDecision(ArrayList<Player> players, int currentBet, boolean isPreFlop) {

		if (isPreFlop) {
			return "call";

		} else {
			if (getHand().getScore() > getAverageScore() + 100) {
				return "raise " + (getHand().getScore() - getAverageScore() - 100);

			} else if (getHand().getScore() > getAverageScore() - 100) {
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
