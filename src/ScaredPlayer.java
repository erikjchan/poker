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
			if (shouldRaisePF() && (getLastDecision() != "raise")) {
				return "raise " + 10;
			}
			return "call";

		} else {
			if (!opponent.getLastDecision().equals("call") && !opponent.getLastDecision().equals("fold")) {
				return "fold";

			} else {
				if (getHand().getScore() > 200 && (getLastDecision() != "raise")) {
					return "raise " + 50;
				} else {
					return "call";
				}
			}
		}
	}
	
	//Big pocket pairs: AA-TT
	//Big suited connectors: AK, AQ, AJ, KQ
	//Big connectors: AK, AQ, AJ, KQ
	
	public Boolean shouldRaisePF() {
		Card first = getFirstCard();
		Card second = getSecondCard();
		int rank1 = first.getRank();
		int rank2 = second.getRank();
		if ((rank1 == 1 && rank2 == 1) || (rank1 == 10 && rank2 == 10) || (rank1 == 11 && rank2 == 11) || (rank1 == 12 && rank2 == 12) || (rank1 == 13 && rank2 == 13)) {
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
		//sort first and second in terms of rank, 
		//http://www.rakebackpros.net/texas-holdem-starting-hands/
//		if ((rank1 == 1 &&  (rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2)) ||
//			(rank1 == 13 && (rank2 == 8 || rank2 == 7 || rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2)) ||
//			(rank1 == 12 && (rank2 == 8 || rank2 == 7 || rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2)) ||
//			(rank1 == 11 && (rank2 == 7 || rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2)) ||
//			(rank1 == 10 && (rank2 == 7 || rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2)) ||
//			(rank1 == 9  && (rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2)) ||
//			(rank1 == 8  && (rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2)) ||
//			(rank1 == 7  && (rank2 == 6 || rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2)) ||
//			(rank1 == 6  && (rank2 == 5 || rank2 == 4 || rank2 == 3 || rank2 == 2)) ||
//			(rank1 == 5  && (rank2 == 4 || rank2 == 3 || rank2 == 2)) ||
//			(rank1 == 4  && (rank2 == 3 || rank2 == 2)) ||
//			(rank1 == 3  && rank2 == 2)) {
//			return true;
//		} else if ((rank2 == 1 &&  (rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2)) ||
//				  (rank2 == 13 && (rank1 == 8 || rank1 == 7 || rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2)) ||
//				  (rank2 == 12 && (rank1 == 8 || rank1 == 7 || rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2)) ||
//				  (rank2 == 11 && (rank1 == 7 || rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2)) ||
//				  (rank2 == 10 && (rank1 == 7 || rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2)) ||
//				  (rank2 == 9  && (rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2)) ||
//				  (rank2 == 8  && (rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2)) ||
//				  (rank2 == 7  && (rank1 == 6 || rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2)) ||
//				  (rank2 == 6  && (rank1 == 5 || rank1 == 4 || rank1 == 3 || rank1 == 2)) ||
//				  (rank2 == 5  && (rank1 == 4 || rank1 == 3 || rank1 == 2)) ||
//				  (rank2 == 4  && (rank1 == 3 || rank1 == 2)) ||
//				  (rank2 == 3  && rank1 == 2)) {
//			return true;
//		} else {
//			return false;
//		}
		}
}
