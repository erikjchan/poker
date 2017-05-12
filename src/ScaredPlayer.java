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

	public String getDecision(ArrayList<Player> players, int currentBet, boolean isPreFlop) {
		Player opponent = players.get(0);

		if (isPreFlop) {
			return "call";

		} else {
			if (!opponent.getLastDecision().equals("call") && !opponent.getLastDecision().equals("fold")) {
				return "fold";

			} else {
				return "call";
			}
		}
	}

}
