import java.util.ArrayList;

/**
 * Class that represents a computer player of the game.
 * 
 * @author echan
 */
public class ComputerPlayer extends Player {

	/**
	 * Constructor class.
	 * 
	 * @param name
	 *            the name of the computer player
	 */
	public ComputerPlayer(String name) {
		super(name);
	}

	public String getDecision() {
		return "meep";
	}
	
	/**
	 * gets the current highest bet
	 * @param players
	 * @return
	 */
	public Integer getMaxBet(ArrayList<Player> players) {
		int max = 0;
		for (int i = 0; i < players.size() - 1; i++) {
			if (max < players.get(i).getBet()) {
				max = players.get(i).getBet();
			}
		}
		return max;
	}
	
	/**
	 * gets the current number of players still in the game.
	 * @param players
	 * @return
	 */
	public Integer getNumPlayers(ArrayList<Player> players) {
		int nplayers = 0;
		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).hasFolded() && !players.get(i).isBankrupt()) {
				nplayers++;
			}
		}
		return nplayers;
	}
	// getFirstCard
	// getSecondCard
	
	
	// Thor - God at Poker
	// Captain America - Really defensive, will fold unless high % of win.
	// Ironman - you
	// Hulk - random 
	
	//if player is 
	// genetic algorithms
	
	/* if (phase = start) {
	 * 	pay the blind
	 * 	
	 * }
	 * 
	 * if (phase = preflop) {
	 * 	let x = this.getFirstCard;
	 *  let y = this.getSecondCard;
	 *  let hole_cards = (x,y);
	 *  hole_cards.sort(
	 *
	 * }
	 *  then look at your hand
	 * 
	 * 
	 * 
	 */
	
}