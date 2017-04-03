/**
 * Class that represents a playing card.
 * 
 * @author echan
 */
public class Card implements Comparable<Card> {

	public enum Suit {
		CLUBS, DIAMONDS, HEARTS, SPADES
	}

	private int rank;
	private Suit suit;

	/**
	 * Constructor class.
	 */
	public Card(int rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	/**
	 * Get the rank of the card.
	 */
	public int getRank() {
		return this.rank;
	}

	/**
	 * Get the suit of the card.
	 */
	public Suit getSuit() {
		return this.suit;
	}

	/**
	 * Set the rank of the card.
	 * 
	 * @param rank
	 *            the new rank of the card
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * Set the suit of the card.
	 * 
	 * @param suit
	 *            the new suit of the card
	 */
	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	@Override
	public int compareTo(Card c) {
		if (this.getRank() > c.getRank()) {
			return -1;

		} else if (this.getRank() < c.getRank()) {
			return 1;

		} else {
			return 0;
		}
	}
}