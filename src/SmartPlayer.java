import java.util.ArrayList;

/**
 * Class that represents a smart player of the game.
 * 
 * @author erik
 */
public class SmartPlayer extends ComputerPlayer {
	boolean trained = false;
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
		// check for priority between three

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
			int opponentScore = getAverageScore();
			if (!opponent.getLastDecision().equals("fold") && !opponent.getLastDecision().equals("call")) {
				opponentScore += 200;

			} else if (opponent.getLastDecision().equals("call")) {
				opponentScore += 25;
			}

			if (getHand().getScore() > opponentScore + 50) {
				return "raise " + (currentBet + getHand().getScore() - opponentScore - 50);

			} else if (getHand().getScore() > opponentScore - 50) {
				return "call";

			} else {
				return "fold";
			}

		} else if (isPreFlop) {
			return "call";

		} else {
			int opponentScore = getAverageScore();
			if (!opponent.getLastDecision().equals("fold") && !opponent.getLastDecision().equals("call")) {
				opponentScore += 200;

			} else if (opponent.getLastDecision().equals("call")) {
				opponentScore += 25;
			}

			if (getHand().getScore() > opponentScore + 50) {
				return "raise " + (currentBet + getHand().getScore() - opponentScore - 50);

			} else if (getHand().getScore() > opponentScore - 50) {
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
		ArrayList<Card> tempCards = new ArrayList<Card>();
		for (Card.Suit suit : Card.Suit.values()) {
			for (int i = 1; i < 14; i++) {
				boolean shouldAdd = true;

				if ((suit == getFirstCard().getSuit() && i == getFirstCard().getRank())
						|| (suit == getSecondCard().getSuit() && i == getSecondCard().getRank())) {
					shouldAdd = false;
				}

				for (int j = 0; j < getCommunityCards().size(); j++) {
					if (suit == getCommunityCards().get(j).getSuit() && i == getCommunityCards().get(j).getRank()) {
						shouldAdd = false;
					}
				}

				if (shouldAdd) {
					tempCards.add(new Card(i, suit));
				}
			}
		}

		Hand opponentHand;
		int totalScore = 0;
		for (int i = 0; i < tempCards.size() - 1; i++) {
			for (int j = i; j < tempCards.size(); j++) {
				opponentHand = new Hand(tempCards.get(i), tempCards.get(j), getCommunityCards());
				opponentHand.calculateScore();
				totalScore += opponentHand.getScore();
			}
		}
		return totalScore / ((tempCards.size() - 1) * tempCards.size());
	}
}
