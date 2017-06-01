import java.util.ArrayList;

/**
 * This computer player plays safe for the entire game without taking too many
 * risks and doesn’t try to play too aggressively.
 * 
 * @author qiqi
 */
public class DefensivePlayer extends ComputerPlayer {

	/**
	 * Constructor class.
	 * 
	 * @param name
	 *            the name of the computer player
	 */
	public DefensivePlayer(String name) {
		super(name);
	}

	public String getDecision(ArrayList<Player> players, int currentBet, boolean isPreFlop) {
		int pmoney = getMoney();
		int nplayers = getNumPlayers(players);
		if (isPreFlop) {
			if (shouldFold()) {
				return "fold";
			} else {
				return "call";
			}
		} else {
			double probWin = (double) (getHand().getScore() / 100 - (nplayers - 1) * 0.10);
			if (probWin > 0.90) {
				int betmoney = currentBet + (int) (0.50 * pmoney);
				return "raise " + betmoney;
			} else if (probWin > 0.60) {
				int betmoney = currentBet + (int) (0.10 * pmoney);
				return "raise " + betmoney;
			} else if (probWin > 0.50) {
				return "call";
				// amt should be current_bet - name.getBet()
			} else {
				if (getNumPlayers(players) > 1) {
					return "fold";
				} else {
					return "fold";
				}
			}
		}
	}

	public Boolean shouldFold() {
		Card first = getFirstCard();
		Card second = getSecondCard();
		int rank1 = first.getRank();
		int rank2 = second.getRank();
		// sort first and second in terms of rank,
		// http://www.rakebackpros.net/texas-holdem-starting-hands/
		if ((rank1 == 1 && (rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2)) || (rank1 == 13
				&& (rank2 == 8 || rank2 == 7 || rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2))
				|| (rank1 == 12 && (rank2 == 8 || rank2 == 7 || rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3
						|| rank2 == 2))
				|| (rank1 == 11 && (rank2 == 7 || rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2))
				|| (rank1 == 10 && (rank2 == 7 || rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2))
				|| (rank1 == 9 && (rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2))
				|| (rank1 == 8 && (rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2))
				|| (rank1 == 7 && (rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2))
				|| (rank1 == 6 && (rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2))
				|| (rank1 == 5 && (rank2 == 4 || rank2 == 3 || rank2 == 2))
				|| (rank1 == 4 && (rank2 == 3 || rank2 == 2)) || (rank1 == 3 && rank2 == 2)) {
			return true;
		} else if ((rank2 == 1 && (rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2)) || (rank2 == 13
				&& (rank1 == 8 || rank1 == 7 || rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2))
				|| (rank2 == 12 && (rank1 == 8 || rank1 == 7 || rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3
						|| rank1 == 2))
				|| (rank2 == 11 && (rank1 == 7 || rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2))
				|| (rank2 == 10 && (rank1 == 7 || rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2))
				|| (rank2 == 9 && (rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2))
				|| (rank2 == 8 && (rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2))
				|| (rank2 == 7 && (rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2))
				|| (rank2 == 6 && (rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2))
				|| (rank2 == 5 && (rank1 == 4 || rank1 == 3 || rank1 == 2))
				|| (rank2 == 4 && (rank1 == 3 || rank1 == 2)) || (rank2 == 3 && rank1 == 2)) {
			return true;
		} else {
			return false;
		}
	}

	// if it is start round then all players pay some kind of blind.
	// Start: cannot fold, cannot raise, cannot call
	// pre-fold: fold, raise, call.
	// fold: fold, raise, call
	// turn: fold, raise, call
	// river: fold, raise, call
	// End: cannot fold, cannot raise, cannot call

}