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
	private int score;

	/**
	 * Constructor class.
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
	 * Get the cards from both the hand and the community.
	 * 
	 * @return the cards from both the hand and the community
	 */
	public ArrayList<Card> getAllCards() {
		return this.allCards;
	}

	/**
	 * Set the cards from both the hand and the community.
	 * 
	 * @param allCards
	 *            the cards from both the hand and the community
	 */
	public void setAllCards(ArrayList<Card> allCards) {
		this.allCards = allCards;
	}

	/**
	 * Sort the cards from both the hand and the community.
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
	}
}