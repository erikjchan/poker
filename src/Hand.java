import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that represents a hand of playing cards.
 * 
 * @author echan
 */
public class Hand {
	private Card firstCard;
	private Card secondCard;
	private ArrayList<Card> communityCards;
	private ArrayList<Card> allCards;
	private ArrayList<Integer> ranks;
	private int score;
	private boolean scoreCalculated;

	/**
	 * Constructor class.
	 * 
	 * @param firstCard
	 *            the first card of the hand
	 * @param secondCard
	 *            the second card of the hand
	 * @param communityCards
	 *            the community cards of the hand
	 */
	public Hand(Card firstCard, Card secondCard, ArrayList<Card> communityCards) {
		this.firstCard = firstCard;
		this.secondCard = secondCard;
		this.communityCards = communityCards;
		allCards.add(firstCard);
		allCards.add(secondCard);
		allCards.addAll(communityCards);
	}

	/**
	 * Get the first card of the hand.
	 * 
	 * @return the first card of the hand
	 */
	public Card getFirstCard() {
		return this.firstCard;
	}

	/**
	 * Set the first card of the hand.
	 * 
	 * @param firstCard
	 *            the new first card of the hand
	 */
	public void setFirstCard(Card firstCard) {
		this.firstCard = firstCard;
	}

	/**
	 * Get the second card of the hand.
	 * 
	 * @return the second card of the hand
	 */
	public Card getSecondCard() {
		return this.secondCard;
	}

	/**
	 * Set the second card of the hand.
	 * 
	 * @param secondCard
	 *            the new second card of the hand
	 */
	public void setSecondCard(Card secondCard) {
		this.secondCard = secondCard;
	}

	/**
	 * Get the community cards of the hand.
	 * 
	 * @return the community cards of the hand
	 */
	public ArrayList<Card> getCommunityCards() {
		return this.communityCards;
	}

	/**
	 * Set the community cards of the hand.
	 * 
	 * @param communityCards
	 *            the new community cards of the hand
	 */
	public void setCommunityCards(ArrayList<Card> communityCards) {
		this.communityCards = communityCards;
	}

	/**
	 * Get the set of cards from both the hand and the community.
	 * 
	 * @return the set of cards from both the hand and the community
	 */
	public ArrayList<Card> getAllCards() {
		return this.allCards;
	}

	/**
	 * Set the set of cards from both the hand and the community.
	 * 
	 * @param allCards
	 *            the set of cards from both the hand and the community
	 */
	public void setAllCards(ArrayList<Card> allCards) {
		this.allCards = allCards;
	}

	/**
	 * Sort the set of cards from both the hand and the community.
	 * 
	 * @return the sorted cards from both the hand and the community
	 */
	public ArrayList<Card> sortCards() {
		Collections.sort(allCards);
		return allCards;
	}

	/**
	 * Get the score of the hand.
	 * 
	 * @return the score of the hand
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Set the score of the hand.
	 * 
	 * @param score
	 *            the new score of the hand
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Calculate the highest score of the hand.
	 * 
	 * @param score
	 *            the highest score of the hand
	 */
	public void calculateScore() {
		this.score = 0;
		if (!scoreCalculated) {
			straightFlush();
		}
		if (!scoreCalculated) {
			fourOfAKind();
		}
		if (!scoreCalculated) {
			fullHouse();
		}
		if (!scoreCalculated) {
			flush();
		}
		if (!scoreCalculated) {
			straight();
		}
		if (!scoreCalculated) {
			threeKind();
		}
		if (!scoreCalculated) {
			twoPair();
		}
		if (!scoreCalculated) {
			onePair();
		}
		scoreCalculated = true;
		highCard();
		scoreCalculated = false;
	}

	/**
	 * Searches for a straight flush in the set of cards from both the hand and
	 * the community.
	 */
	public void straightFlush() {
		for (int i = 0; i < allCards.size() - 4; i++) {
			int rank = allCards.get(i).getRank();
			// Checks for straight
			if (allCards.get(i + 1).getRank() == rank - 1 && allCards.get(i + 2).getRank() == rank - 2
					&& allCards.get(i + 3).getRank() == rank - 3 && allCards.get(i + 4).getRank() == rank - 4) {
				// Checks for flush
				if (allCards.get(i + 1).getSuit() == allCards.get(i + 2).getSuit()
						&& allCards.get(i + 3).getSuit() == allCards.get(i + 4).getSuit()
						&& allCards.get(i + 1).getSuit() == allCards.get(i + 4).getSuit()) {
					score = 900;
					ranks.add(rank);
					ranks.add(rank - 1);
					ranks.add(rank - 2);
					ranks.add(rank - 3);
					ranks.add(rank - 4);
					scoreCalculated = true;
				}
			}
		}
	}

	/**
	 * Searches for a four of a kind in the set of cards from both the hand and
	 * the community.
	 */
	public void fourOfAKind() {
		for (int i = 0; i < allCards.size() - 3; i++) {
			int rank = allCards.get(i).getRank();
			// Checks if four cards have similar number
			if (allCards.get(i + 1).getRank() == rank && allCards.get(i + 2).getRank() == rank
					&& allCards.get(i + 3).getRank() == rank) {
				score = 800;
				ranks.add(rank);
				ranks.add(rank);
				ranks.add(rank);
				ranks.add(rank);
				ranks.add(rank);
				scoreCalculated = true;
			}
		}
	}

}