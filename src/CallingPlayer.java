import java.util.ArrayList;

/**
 * Class that represents a computer player of the game.
 * 
 * @author qiqi
 */
public class CallingPlayer extends ComputerPlayer {
	
	/**
	 * Constructor class.
	 * 
	 * @param name
	 *            the name of the computer player
	 */
	public CallingPlayer(String name) {
		super(name);
	}
	
	public String getDecision(ArrayList<Player> players, int currentBet, boolean isPreFlop) {
		
		if (isPreFlop) {
			return "call";
		} else {
			return "call";
		}
	}
}
