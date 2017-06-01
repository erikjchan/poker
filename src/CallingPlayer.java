import java.util.ArrayList;

/**
 * 
 * This computer player plays normally, but risks calling in with a hand that
 * CallingPlayer doesn’t think is too good to gain money. If CallingPlayer’s
 * hand is decent then CallingPlayer might stay in knowing that he would be able
 * to gain money if the opponent’s hand is also not as good.
 * 
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
