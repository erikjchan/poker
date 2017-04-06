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
	 * 
	 * @return the rank of the card
	 */
	public int getRank() {
		return this.rank;
	}

	/**
	 * Get the suit of the card.
	 * 
	 * @return the suit of the card
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

	/**
	 * Get the path of the card.
	 * 
	 * @return the path of the card
	 */
	public String getPath() {
		String rankSymbol = "";
		String suitSymbol = "";
		if (rank > 1 && rank < 11) {
			rankSymbol = Integer.toString(rank);
		} else if (rank == 1) {
			rankSymbol = "a";
		} else if (rank == 11) {
			rankSymbol = "j";
		} else if (rank == 12) {
			rankSymbol = "q";
		} else if (rank == 13) {
			rankSymbol = "k";
		}

		switch (suit) {
		case CLUBS:
			suitSymbol = "clubs";
			break;
		case DIAMONDS:
			suitSymbol = "diamonds";
			break;
		case HEARTS:
			suitSymbol = "hearts";
			break;
		case SPADES:
			suitSymbol = "spades";
			break;
		}
		return "img/" + rankSymbol + "-" + suitSymbol + ".png";
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