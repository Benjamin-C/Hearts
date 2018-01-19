package game;

import pile.Card;
import pile.CardValue;
import pile.Hand;
import pile.Pile;
import pile.Suit;

public class Player {
	
	private Hand h;
	private int score;
	private int id;
	private Pile tricks;
	
	
	public Player() {
		h = new Hand();
		score = 0;
		id = -1;
		tricks = new Pile();
	}
	
	public Player(int id) {
		h = new Hand();
		score = 0;
		this.id = id;
		tricks = new Pile();
	}
	public Player(Hand h, int score) {
		super();
		this.h = h;
		this.score = score;
		id = -1;
		tricks = new Pile();
	}
	public Player(Hand h, int score, int id) {
		super();
		this.h = h;
		this.score = score;
		this.id = id;
		tricks = new Pile();
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
	
	public Card playCard(Pile table) {
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
