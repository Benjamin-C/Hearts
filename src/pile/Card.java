package pile;

import java.awt.Color;

public class Card implements Comparable<Object>{
	
	Suit suit;
	int value;
	
	public Card() {
		suit = null;
		value = -1;
	}
	public Card(Suit suit, int value) {
		super();
		this.suit = suit;
		this.value = value;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Color getColor() {
		return suit.getColor();
	}
	
	public String getColorName() {
		return suit.getColorName();
	}
	
	@Override
	public String toString() {
		return CardValue.getName(value) + " of " + suit.getName() ;
	}

	@Override
	public int compareTo(Object o) {
		Card other = (Card) o;
		if(suit.getID() > other.getSuit().getID()) {
			return 1;
		} else if (suit.getID() < other.getSuit().getID()) {
			return -1;
		} else if (value > other.getValue()) {
			return 1;
		} else if (value < other.getValue()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	public boolean equals(String text) {
		return text.equals(toString());
	}
	
	public boolean equals(Card c) {
		if(c.getSuit() == suit && c.getValue() == value) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isHigherValueInSuit(Card other) {
		if(other.getSuit().equals(suit)) {
			if(other.getValue() > value) {
				return true;
			}
		}
		return false;
	}
	public boolean isValid() {
		return (value >= 2 && value <= CardValue.ACE && suit != null);
	}
}
