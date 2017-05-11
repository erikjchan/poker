import java.util.ArrayList;

/**
 * Class that represents a smart player of the game.
 * 
 * @author erik
 */
public class SmartPlayer extends ComputerPlayer {
	private Deck deck;
	private ArrayList<Player> players;

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
		this.players = players;

		int pmoney = getMoney();
		int nplayers = getNumPlayers(players);

		double probWin = (double) (getHand().getScore() / 100 - (nplayers - 1) * 0.10);
		if (probWin > 0.90) {
			int betmoney = currentBet + (int) (0.50 * pmoney);
			return "raise " + betmoney;
		} else if (probWin > 0.60) {
			int betmoney = currentBet + (int) (0.10 * pmoney);
			return "raise " + betmoney;
		} else if (probWin > 0.50) {
			return "call";
			// amt should be current_bet - name.getBet()
		} else {
			if (getNumPlayers(players) > 1) {
				return "fold";
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
