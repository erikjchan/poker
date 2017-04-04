import java.util.ArrayList;

/**
 * Class that represents a deck of playing cards.
 * 
 * @author echan
 */
public class Deck {
	private ArrayList<Card> cards = new ArrayList<Card>();

	/**
	 * Constructor class.
	 */
	public Deck() {
		cards.clear();
		for (Card.Suit suit : Card.Suit.values()) {
			for (int i = 1; i < 14; i++) {
				cards.add(new Card(i, suit));
			}
		}
		shuffle();
	}

	/**
	 * Get the cards of the deck.
	 * 
	 * @return the cards of the deck
	 */
	public ArrayList<Card> getCards() {
		return this.cards;
	}

	/**
	 * Set the cards of the deck.
	 * 
	 * @param cards
	 *            the new cards of the deck
	 */
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	/**
	 * Randomly rearrange the cards in the deck.
	 */
	public void shuffle() {
		// Build a new list of cards.
		ArrayList<Card> temp = new ArrayList<Card>();
		for (int i = 0; i < cards.size(); i += 0) {
			int randomIndex = (int) (Math.random() * cards.size());
			temp.add(cards.get(randomIndex));
			cards.remove(randomIndex);
		}
		cards = temp;
	}

	/**
	 * Remove and deal the first card from the deck.
	 * 
	 * @return the first card from the deck
	 */
	public Card drawCard() {
		if (!cards.isEmpty()) {
			return cards.remove(0);

		} else {
			System.out.println("The deck is empty.");
			return null;
		}
	}

	/**
	 * Remove and deal the first n cards from the deck.
	 * 
	 * @param n
	 *            the number of cards to draw from the deck
	 * 
	 * @return the first n cards from the deck
	 */
	public ArrayList<Card> drawCards(int n) {
		ArrayList<Card> drawnCards = new ArrayList<Card>();
		for (int i = 0; i < n; i++) {
			if (!cards.isEmpty()) {
				drawnCards.add(cards.remove(0));

			} else {
				System.out.println("The deck is too small.");
				return null;
			}
		}
		return drawnCards;
	}
}