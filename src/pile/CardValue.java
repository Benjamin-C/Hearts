package pile;

public class CardValue {
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	public static final int ACE = 14;
	
	public static String getName(int num) {
		switch(num) {
		case JACK: { return "Jack"; }
		case QUEEN: { return "Queen"; }
		case KING: { return "King"; }
		case ACE: { return "Ace"; }
		default: { return Integer.toString(num); }
		}
	}
}
