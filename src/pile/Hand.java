package pile;

@SuppressWarnings("serial")
public class Hand extends Pile{

	public Hand() {
		clear();
	}
	
	public void addHand(Hand h) {
		addAll(h);
	}
}
