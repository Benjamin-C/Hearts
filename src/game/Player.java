package game;

import inputOutput.Input;
import inputOutput.Output;
import pile.Card;
import pile.CardValue;
import pile.Hand;
import pile.Pile;
import pile.Suit;
import pile.Table;

public class Player {
	
	private Hand h;
	private int score;
	private int id;
	private Pile tricks;
	private boolean isHuman;
	private Input in;
	
	public Player(int id) {
		this(new Hand(), 0, id, false);
	}
	public Player(int id, boolean human) {
		this(new Hand(), 0, id, human);
	}
	public Player(Hand h, int score, int id, boolean isHuman) {
		super();
		this.h = h;
		this.score = score;
		this.id = id;
		this.isHuman = isHuman;
		tricks = new Pile();
		in = new Input();
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Hand getHand() {
		return h;
	}
	public void setHand(Hand h) {
		this.h = h;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void addScore(int add) {
		score = score + add;
	}
	public Pile getTricks() {
		return tricks;
	}
	public void setTricks(Pile t) {
		tricks = t;
	}
	public void addTricks(Pile t) {
		tricks.addAll(t);
	}
	public boolean isHuman() {
		return isHuman;
	}
	
	public boolean shotMoon() {
		if(tricks.contains(new Card(Suit.SPADE, CardValue.QUEEN)) && tricks.howMany(Suit.HEART) == 13) {
			return true;
		}
		return false;
	}
	public int getHandScore() {
		int tempScore = tricks.howMany(Suit.HEART);
		if(tricks.contains(new Card(Suit.SPADE, CardValue.QUEEN))) {
			tempScore = tempScore + 13;
		}
		return tempScore;
	}
	public int getRunninScore() {
		return score + getHandScore();
	}
	
	public Card playCard(Table table) {
		if(isHuman == true) { // If this is a human-controlled player
			System.out.println("It is your turn to play a card");
			System.out.println("Table: " + table);
			Card c = new Card();
			while(!c.isValid()) {
				boolean failed = false;
				c = getCard();
				if(table.size() > 0) {
					if(h.containsSuit(table.get(0).getSuit()) && c.getSuit() != table.get(0).getSuit()) {
						System.out.println("You must follow suit (" + table.get(0).getSuit().toString() + ")");
						failed = true;
					}
				} else {
					if(c.getSuit() == Suit.HEART) {
						if(!table.isHeartsBroken()) {
							if(h.containsSuit(Suit.CLUB) || h.containsSuit(Suit.DIAMOND) || h.containsSuit(Suit.SPADE)) {
								System.out.println("You may not lead hearts until they have been broken or you can't lead anything else");
								failed = true;
							}
						}
					}
				}
				if(failed == true) {
					c = new Card();
				}
			}
			System.out.println("You played the " + c.toString());
			return h.remove(c);
		} else { // If this player is using the AI
			if(table.isEmpty()) {
				for(int i = 0; i < h.size(); i++) {
					if(h.get(i).getSuit() != Suit.HEART) {
						return h.remove(i);
					}
				}
				return h.remove(0);
			} else {
				for(int i = 0; i < h.size(); i++) {
					if(h.get(i).getSuit() == table.get(0).getSuit()) {
						return h.remove(i);
					}
				}
				if(h.contains(new Card(Suit.SPADE, CardValue.QUEEN))) {
					return h.remove(new Card(Suit.SPADE, CardValue.QUEEN));
				} else {
					return h.remove(0);
				}
			}
		}
	}
	
	public Hand passCards(Output out) {
		
		Hand pass = new Hand();
		if(isHuman == true) {
			for(int i = 0; i < 3; i++) {
				System.out.println("Pick a card to pass (" + (i + 1) + "/3)");
				pass.add(h.remove(getCard()));
			}
			return pass;
		} else {
			Hand temp = h;
			Card c = null;
			int passes = 0;
			temp.sort();
			int num = 0;
			int val = CardValue.ACE;
			while(passes < 3) {
				// Pass specific cards
				c = null;
				switch(num) {
				case 0: {
					c = new Card(Suit.CLUB, CardValue.KING);
				} break;
				case 1: {
					c = new Card(Suit.CLUB, CardValue.ACE);
				} break;
				default: {
					do {
						if(temp.containsValue(val)) {
							c = temp.getValue(val);
							break;
						}
						val--;
						if(val < 0) {
							System.err.println("Value is below 0\n\tat " + out.stackTrace(-2));
							System.exit(0);
						}
					} while(c == null);
				} break;
				}
	
				if(temp.contains(c)) {
					pass.add(h.remove(c));
					passes++;
				}
				num++;
			}
			out.println(id + " is Passing " + pass);
			return pass;
		}
	}
	
	private Card getCard() {
		System.out.println("Your hand: " + h);
		System.out.println("What do you want to pick? (Type ? for help)");
		Card c = new Card();
		String ins = "";
		while(!c.isValid()) {
			boolean failed = false;
			ins = in.getLine().toUpperCase();
			c = new Card();
			switch(ins.charAt(0)) {
			case '?': {
				System.out.println("You will type in what card you want to play");
				System.out.println("The first character is the suit, the rest is the value");
				System.out.println("Suits: h = heart, d = diamond, s = spades, c = clubs");
				System.out.println("For 2-10, type the number. Jack = " + CardValue.JACK + ", Queen = " + CardValue.QUEEN + ", King = " + CardValue.KING + ", Ace = " + CardValue.ACE);
				System.out.println("What card do you want to play?");
				failed = true;
			} break;
			case 'H': {
				c.setSuit(Suit.HEART);
			} break;
			case 'D': {
				c.setSuit(Suit.DIAMOND);
			} break;
			case 'S': {
				c.setSuit(Suit.SPADE);
			} break;
			case 'C': {
				c.setSuit(Suit.CLUB);
			} break;
			default: {
				failed = true;
				System.out.println("Sorry, I couldn't understand you. Please try again. (Type ? for help)");
			} break;
			}
			if(failed == false) { // Get number
				String num = ins.substring(1);
				try {
					c.setValue(Integer.parseInt(num));
				} catch(NumberFormatException e) {
					System.out.println("Please try again and give me a number for the card value");
					failed = true;
				}
				if(c.getValue() < 2) {
					System.out.println("Please give a number that is at least 2");
					failed = true;
				}
				if(c.getValue() > CardValue.ACE) {
					System.out.println("Please give me a number that is at most " + CardValue.ACE);
					failed = true;
				}
			}
			if(!h.contains(c)) {
				System.out.println("You cant pick the " + c.toString() + " because you don't have it");
				failed = true;
			}
			if(failed == true) {
				c = new Card();
			}
		}
		System.out.println("You picked the " + c.toString());
		return c;
	}
}
