package pile;

public class Table extends Pile {
	// Constructor
	private boolean heartsBroken;
	public Table() {
		//heartsBroken = false;
	}

	@Override
	public boolean add(Card c) {
		if(c.getSuit() == Suit.HEART) {
			heartsBroken = true;
		}
		return super.add(c);
	}
	
	@Override
	public void clear() {
		//heartsBroken = false;
		super.clear();
	}

	public boolean isHeartsBroken() {
		return heartsBroken;
	}
	
	public void setHeartsBroken(Boolean b) {
		heartsBroken = b;
	}
	
}
