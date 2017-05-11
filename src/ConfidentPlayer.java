import java.util.ArrayList;

/**
 * Class that represents a computer player of the game.
 * 
 * @author qiqi
 */
public class ConfidentPlayer extends ComputerPlayer {
	
	/**
	 * Constructor class.
	 * 
	 * @param name
	 *            the name of the computer player
	 */
	public ConfidentPlayer(String name) {
		super(name);
	}
	
	public String getDecision(ArrayList<Player> players, int currentBet, boolean isPreFlop) {
		if (isPreFlop) {
			return "call";
		} else {
			if (getHand().getScore() > 300) {
				return "raise " + (currentBet + getHand().getScore()*0.10);  
			} else {
				return "call";
			}
		}
	}
}