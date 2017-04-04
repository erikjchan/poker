/**
 * Abstract class that represents a player of the game.
 * 
 * @author echan
 */
public abstract class Player {
	private String name;
	private Hand hand;
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
}