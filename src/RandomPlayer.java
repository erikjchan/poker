import java.util.Random;
import java.util.ArrayList;

/**
 * Class that represents a computer player of the game.
 * 
 * @author qiqi
 */
public class RandomPlayer extends ComputerPlayer {
	
	/**
	 * Constructor class.
	 * 
	 * @param name
	 *            the name of the computer player
	 */
	public RandomPlayer(String name) {
		super(name);
	}
	
	public String getDecision(ArrayList<Player> players, Player name) {
		Random random = new Random();
		int max = 3;
		int min = 1;
		int x = random.nextInt(max - min + 1) + min;
		if (x == 1) {
			return "fold";
		} else if (x == 2) {
			return "call";
		} else {
			int max_raise = name.getMoney();
			int min_raise = 1;
			int raise = random.nextInt(max_raise - min_raise + 1) + min_raise;
			return "raise " + raise;
		}
	}
	
	//if it is start round then all players pay some kind of blind.
	//Start: cannot fold, cannot raise, cannot call
	//pre-fold: fold, raise, call. 
	//fold: fold, raise, call
	//turn: fold, raise, call
	//river: fold, raise, call
	//End: cannot fold, cannot raise, cannot call

}