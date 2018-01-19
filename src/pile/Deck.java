package pile;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Deck extends Pile {
	
	// Constructor
	public Deck() {
		initDeck();
	}
	
	// Init
	protected void initDeck() {
		addAll(createSuit(Suit.CLUB));
		addAll(createSuit(Suit.DIAMOND));
		addAll(createSuit(Suit.HEART));
		addAll(createSuit(Suit.SPADE));
	}
	
	protected ArrayList<Card> createSuit(Suit suit) {
		ArrayList<Card> l = new ArrayList<Card>();
		for(int i = 2; i < 15; i++) {
			l.add(new Card(suit, i));
		}
		return l;
	}
}
