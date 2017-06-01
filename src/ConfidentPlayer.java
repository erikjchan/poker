import java.util.ArrayList;

/**
 * This computer player plays relatively safely with bad hands and knows when to
 * fold. With better hands, ConfidentPlayer plays with more confidence and goes
 * more on the offense to win more money. Depending on how good its hands is,
 * ConfidentPlayer plays more confidently and aggressively.
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
			if (shouldRaisePF() && (getLastDecision() != "raise")) {
				return "raise " + 20;
			} else {
				return "call";
			}
		} else {
			if (getHand().getScore() > 900) {
				return "raise " + (currentBet + (int) (getHand().getScore() * 0.90));
			} else if (getHand().getScore() > 800) {
				return "raise " + (currentBet + (int) (getHand().getScore() * 0.70));
			} else if (getHand().getScore() > 600) {
				return "raise " + (currentBet + (int) (getHand().getScore() * 0.60));
			} else if (getHand().getScore() > 500) {
				return "raise " + (currentBet + (int) (getHand().getScore() * 0.40));
			} else if (getHand().getScore() > 400) {
				return "raise " + (currentBet + (int) (getHand().getScore() * 0.20));
			} else if (getHand().getScore() > 300) {
				return "raise " + (currentBet + (int) (getHand().getScore() * 0.10));
			} else if (getHand().getScore() > 200) {
				return "call";
			} else {
				return "fold";
			}
		}
	}

	public Boolean shouldRaisePF() {
		Card first = getFirstCard();
		Card second = getSecondCard();
		int rank1 = first.getRank();
		int rank2 = second.getRank();
		if ((rank1 == 1 && rank2 == 1) || (rank1 == 10 && rank2 == 10) || (rank1 == 11 && rank2 == 11)
				|| (rank1 == 12 && rank2 == 12) || (rank1 == 13 && rank2 == 13)) {
			return true;
		} else if (rank1 == 1) {
			if (rank2 == 13 || rank2 == 12 || rank2 == 11) {
				return true;
			} else {
				return false;
			}
		} else if (rank2 == 1) {
			if (rank1 == 13 || rank1 == 12 || rank1 == 11) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}