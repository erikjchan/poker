import java.util.ArrayList;

/**
 * Class that represents a computer player of the game.
 * 
 * @author qiqi
 */
public class ScaredPlayer extends ComputerPlayer {
	
	/**
	 * Constructor class.
	 * 
	 * @param name
	 *            the name of the computer player
	 */
	public ScaredPlayer(String name) {
		super(name);
	}
	
	public String getDecision(ArrayList<Player> players, int currentBet, boolean isPreFlop, int pot) {
		int pmoney = getMoney();
		int nplayers = getNumPlayers(players);
		if (isPreFlop) {
			return "call";
		} else {
			if (currentBet > 1.2 * pot) {
				return "fold";
			} else {
				return "call";
			}
		}
	}
	
}
