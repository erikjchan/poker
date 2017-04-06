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
	private ArrayList<Integer> ranks = new ArrayList<Integer>();
	private String handType;
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
		allCards = new ArrayList<Card>();
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
		allCards = new ArrayList<Card>();
		allCards.add(firstCard);
		allCards.add(secondCard);
		allCards.addAll(communityCards);
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
	 * Get the type of the hand.
	 * 
	 * @return the type of the hand
	 */
	public String getHandType() {
		return this.handType;
	}

	/**
	 * Set the type of the hand.
	 * 
	 * @param handType
	 *            the new type of the hand
	 */
	public void setHandType(String handType) {
		this.handType = handType;
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
		handType = "";
		score = 0;
		ranks = new ArrayList<Integer>();
		sortCards();
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
			threeOfAKind();
		}
		if (!scoreCalculated) {
			twoPair();
		}
		if (!scoreCalculated) {
			onePair();
		}
		if (!scoreCalculated) {
			handType = "High Card";
		}
		scoreCalculated = true;
		updateScore();
		scoreCalculated = false;
	}

	/**
	 * Searches for a straight flush (five cards of sequential rank, all of the
	 * same suit) in the set of cards from both the hand and the community.
	 */
	// TODO: fails for case like 6, 5, 5, 4, 3, 2
	public void straightFlush() {
		for (int i = 0; i < allCards.size() - 4; i++) {
			int rank = allCards.get(i).getRank();
			// checks if hand is straight
			if (allCards.get(i + 1).getRank() == rank - 1 && allCards.get(i + 2).getRank() == rank - 2
					&& allCards.get(i + 3).getRank() == rank - 3 && allCards.get(i + 4).getRank() == rank - 4) {
				// checks if hand is flush
				if (allCards.get(i + 1).getSuit() == allCards.get(i + 2).getSuit()
						&& allCards.get(i + 3).getSuit() == allCards.get(i + 4).getSuit()
						&& allCards.get(i + 1).getSuit() == allCards.get(i + 4).getSuit()) {
					score = 900;
					ranks.add(rank);
					ranks.add(rank - 1);
					ranks.add(rank - 2);
					ranks.add(rank - 3);
					ranks.add(rank - 4);
					handType = "Straight Flush";
					scoreCalculated = true;
					break;
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
			// checks if hand has four cards of same rank
			if (allCards.get(i + 1).getRank() == rank && allCards.get(i + 2).getRank() == rank
					&& allCards.get(i + 3).getRank() == rank) {
				score = 800;
				ranks.add(rank);
				ranks.add(rank);
				ranks.add(rank);
				ranks.add(rank);
				handType = "Four of a Kind";
				scoreCalculated = true;
				break;
			}
		}
	}

	/**
	 * Searches for a full house (three cards of one rank and two cards of
	 * another rank) in the set of cards from both the hand and the community.
	 */
	public void fullHouse() {
		// checks if hand has three of a kind
		boolean hasThreeOfAKind = false;
		int firstRank = 0;
		for (int i = 0; i < allCards.size() - 2; i++) {
			firstRank = allCards.get(i).getRank();
			if (allCards.get(i + 1).getRank() == firstRank && allCards.get(i + 2).getRank() == firstRank) {
				hasThreeOfAKind = true;
				break;
			}
		}

		// checks if hand has pair
		boolean hasPair = false;
		int secondRank = 0;
		for (int j = 0; j < allCards.size() - 1; j++) {
			secondRank = allCards.get(j).getRank();
			if ((allCards.get(j + 1).getRank() == secondRank) && (secondRank != firstRank)) {
				hasPair = true;
				break;
			}
		}

		// checks if hand has both three of a kind and pair
		if (hasThreeOfAKind && hasPair) {
			score = 700;
			ranks.add(firstRank);
			ranks.add(firstRank);
			ranks.add(firstRank);
			ranks.add(secondRank);
			ranks.add(secondRank);
			handType = "Full House";
			scoreCalculated = true;
		}
	}

	/**
	 * Searches for a flush (five cards all of the same suit, not all of
	 * sequential rank) in the set of cards from both the hand and the
	 * community.
	 */
	public void flush() {
		ArrayList<Integer> matchRanks = new ArrayList<Integer>();
		matchRanks.add(0);
		matchRanks.add(0);
		matchRanks.add(0);
		matchRanks.add(0);
		matchRanks.add(0);
		for (Card.Suit suit : Card.Suit.values()) {
			int numberMatches = 0;
			for (int i = 0; i < allCards.size(); i++) {
				if (allCards.get(i).getSuit() == suit) {
					matchRanks.set(numberMatches, allCards.get(i).getRank());
					numberMatches++;
				}
				if (numberMatches > 4) {
					score = 600;
					ranks = matchRanks;
					handType = "Flush";
					scoreCalculated = true;
					break;
				}
			}
		}
	}

	/**
	 * Searches for a straight (five cards of sequential rank, not all of the
	 * same suit) in the set of cards from both the hand and the community.
	 * 
	 */
	// TODO: fails for case like 6, 5, 5, 4, 3, 2
	public void straight() {
		for (int i = 0; i < allCards.size() - 4; i++) {
			int rank = allCards.get(i).getRank();
			if (allCards.get(i + 1).getRank() == rank - 1 && allCards.get(i + 2).getRank() == rank - 2
					&& allCards.get(i + 3).getRank() == rank - 3 && allCards.get(i + 4).getRank() == rank - 4) {
				score = 500;
				ranks.add(rank);
				ranks.add(rank - 1);
				ranks.add(rank - 2);
				ranks.add(rank - 3);
				ranks.add(rank - 4);
				handType = "Straight";
				scoreCalculated = true;
				break;
			}
		}
	}

	/**
	 * Searches for a three of a kind in the set of cards from both the hand and
	 * the community.
	 */
	public void threeOfAKind() {
		for (int i = 0; i < allCards.size() - 2; i++) {
			int rank = allCards.get(i).getRank();
			if (allCards.get(i + 1).getRank() == rank && allCards.get(i + 2).getRank() == rank) {
				score = 400;
				ranks.add(rank);
				ranks.add(rank);
				ranks.add(rank);
				handType = "Three of a Kind";
				scoreCalculated = true;
				break;
			}
		}
	}

	/**
	 * Searches for a two pair in the set of cards from both the hand and the
	 * community.
	 */
	public void twoPair() {
		// checks if hand has pair
		outerloop:
		for (int i = 0; i < allCards.size() - 3; i++) {
			int firstRank = allCards.get(i).getRank();
			if (allCards.get(i + 1).getRank() == firstRank) {
				// checks if hand has second pair
				for (int j = i + 2; j < allCards.size() - 1; j++) {
					int secondRank = allCards.get(j).getRank();
					if (allCards.get(j + 1).getRank() == secondRank) {
						score = 300;
						ranks.add(firstRank);
						System.out.println(firstRank);
						ranks.add(firstRank);
						ranks.add(secondRank);
						System.out.println(secondRank);
						ranks.add(secondRank);
						handType = "Two Pair";
						scoreCalculated = true;
						break outerloop;
					}
				}
			}
		}
	}

	/**
	 * Searches for a one pair in the set of cards from both the hand and the
	 * community.
	 */
	public void onePair() {
		for (int i = 0; i < allCards.size() - 1; i++) {
			int rank = allCards.get(i).getRank();
			if (allCards.get(i + 1).getRank() == rank) {
				score = 200;
				ranks.add(rank);
				ranks.add(rank);
				handType = "One Pair";
				scoreCalculated = true;
				break;
			}
		}
	}

	/**
	 * Updates the score based on the card ranks.
	 */
	public void updateScore() {
		if (ranks.isEmpty()) {
			for (int i = 0; i < 5; i++) {
				ranks.add(allCards.get(i).getRank());
			}
		}

		for (int i = 0; i < ranks.size(); i++) {
			if (ranks.get(i) == 1) {
				ranks.set(i, 14);
			}
			score += ranks.get(i);
		}
	}

}