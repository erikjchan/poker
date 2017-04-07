import java.util.ArrayList;

/**
 * Abstract class that represents a player of the game.
 * 
 * @author echan
 */
public abstract class Player {
	private String name;
	private int money = 300;
	private int bet;
	private Card firstCard;
	private Card secondCard;
	private ArrayList<Card> communityCards;
	private Hand hand;
	private boolean isBankrupt;
	private boolean hasFolded;

	/**
	 * Constructor class.
	 * 
	 * @param name
	 *            the name of the player
	 */
	public Player(String name) {
		this.name = name;
	}

	/**
	 * Get the name of the player.
	 * 
	 * @return the name of the player
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set the name of the player.
	 * 
	 * @param name
	 *            the new name of the player
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the money of the player.
	 * 
	 * @return the money of the player
	 */
	public int getMoney() {
		return this.money;
	}

	/**
	 * Set the money of the player.
	 * 
	 * @param money
	 *            the new money of the player
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * Get the best of the player.
	 * 
	 * @return the bet of the player
	 */
	public int getBet() {
		return this.bet;
	}

	/**
	 * Set the bet of the player.
	 * 
	 * @param bet
	 *            the new bet of the player
	 */
	public void setBet(int bet) {
		if (bet < this.money) {
			if (bet >= this.bet) {
				int tempBet = this.bet;
				this.bet = bet;
				money -= (bet - tempBet);
			} else {
				this.bet = bet;
				money -= bet;
			}

		} else {
			this.bet = money;
			money = 0;
		}
	}

	/**
	 * Get the first card of the player.
	 * 
	 * @return the first card of the player
	 */
	public Card getFirstCard() {
		return this.firstCard;
	}

	/**
	 * Set the first card of the player.
	 * 
	 * @param firstCard
	 *            the new first card of the player
	 */
	public void setFirstCard(Card firstCard) {
		this.firstCard = firstCard;
	}

	/**
	 * Get the second card of the player.
	 * 
	 * @return the second card of the player
	 */
	public Card getSecondCard() {
		return this.secondCard;
	}

	/**
	 * Set the second card of the player.
	 * 
	 * @param secondCard
	 *            the new second card of the player
	 */
	public void setSecondCard(Card secondCard) {
		this.secondCard = secondCard;
	}

	/**
	 * Get the community cards of the player.
	 * 
	 * @return the community cards of the player
	 */
	public ArrayList<Card> getCommunityCards() {
		return this.communityCards;
	}

	/**
	 * Set the community cards of the player.
	 * 
	 * @param communityCards
	 *            the new community cards of the player
	 */
	public void setCommunityCards(ArrayList<Card> communityCards) {
		this.communityCards = communityCards;
	}

	/**
	 * Get the hand of the player.
	 * 
	 * @return the hand of the player
	 */
	public Hand getHand() {
		return this.hand;
	}

	/**
	 * Set the hand of the player.
	 * 
	 * @param hand
	 *            the new hand of the player
	 */
	public void setHand(Hand hand) {
		this.hand = hand;
	}

	/**
	 * Update the hand of the player.
	 * 
	 * @param communityCards
	 *            the new community cards of the player
	 */
	public void updateHand(ArrayList<Card> communityCards) {
		if (hand == null && !communityCards.isEmpty()) {
			hand = new Hand(firstCard, secondCard, communityCards);
			hand.calculateScore();

		} else if (!communityCards.isEmpty()) {
			hand.setCommunityCards(communityCards);
			hand.calculateScore();
		}
	}

	/**
	 * Get whether the player is bankrupt.
	 * 
	 * @return whether the player is bankrupt
	 */
	public boolean isBankrupt() {
		return this.isBankrupt;
	}

	/**
	 * Set whether the player is bankrupt.
	 * 
	 * @param isBankrupt
	 *            whether the player is bankrupt
	 */
	public void setBankrupt(boolean isBankrupt) {
		this.isBankrupt = isBankrupt;
	}

	/**
	 * Get whether the player has folded.
	 * 
	 * @return whether the player has folded
	 */
	public boolean hasFolded() {
		return this.hasFolded;
	}

	/**
	 * Set whether the player has folded.
	 * 
	 * @param hasFolded
	 *            whether the player has folded
	 */
	public void setFolded(boolean hasFolded) {
		this.hasFolded = hasFolded;
	}
}