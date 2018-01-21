package pile;

import java.util.ArrayList;
import java.util.Random;

import sort.*;

public class Pile extends ArrayList<Card> {
	
	// Constructor
	public Pile() {
	}
	
	// Get card
	public Card draw() {
		return remove(0);
	}
	
	public ArrayList<Card> draw(int n) {
		ArrayList<Card> l = new ArrayList<Card>();
		for(int i = 0; i < n; i++) {
			l.add(remove(0));
		}
		return l;
	}
	
	// Remove card
	public Card remove(Card c) {
		int loc = find(c);
		if(loc != -1) {
			return remove(loc);
		}
		return null;
	}
	public Pile remove(int start, int end) {
		if(start < end) {
			if(start < 0) { start = 0; }
			if(end > size()) { end = size(); }
			Pile out = new Pile();
			for(int i = start; i < end; i++) {
				out.add(remove(start));
			}
			return out;
		}
		return null;
	}
	
	// Find card
	protected int find(Card c) {
		for(int i = 0; i < size(); i++) {
			if(get(i).equals(c)) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean contains(Card c) {
		for(int i = 0; i < size(); i++) {
			if(get(i).equals(c)) {
				return true;
			}
		}
		return false;
	}
	
	public int howMany(Suit s) {
		int count = 0;
		for(int i = 0; i < size(); i++) {
			if(get(i).getSuit() == s) {
				count++;
			}
		}
		return count;
	}
	
	public boolean containsSuit(Suit s) {
		for(int i = 0; i < size(); i++) {
			if(get(i).getSuit().equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsValue(int v) {
		for(int i = 0; i < size(); i++) {
			if(get(i).getValue() == v) {
				return true;
			}
		}
		return false;
	}
	
	public Card getValue(int v) {
		if(containsValue(v)) {
			Pile p = (Pile) clone();
			p.sort();
			for(int i = 0; i < p.size(); i++) {
				if(p.get(i).getValue() == v) {
					return p.get(i);
				}
			}
		}
		return null;
	}
	
	// Sort and shuffle
	public void sort() { // Bubble or quick sort depending on legnth
		ArrayList<Sortable> list = new ArrayList<Sortable>();
		for(int i = 0; i < size(); i++) {
			int index = (get(i).suit.getID() * 15) + get(i).value;
			list.add(new Sortable(get(i), index));
		}
		if(size() > 48) {
			list = QuickSort.sort(list);
		} else {
			list = BubbleSort.sort(list);
		}
		clear();
		for(int i = 0; i < list.size(); i++) {
			add((Card) list.get(i).getObject());
		}
	}
	
	public void shuffle() { // Fisher-Yates shuffle
		Random r = new Random();
		ArrayList<Card> hat = new ArrayList<Card>();
		hat.addAll(this);
		clear();
		while(hat.size() > 0) {
			add(hat.remove(r.nextInt(hat.size())));
		}
	}
	
	// toString
	// Keeping normal toString
	public String[] toStringArray() {
		String out[] = new String[size()];
		for(int i = 0; i < size(); i++) {
			out[i] = get(i).toString();
		}
		return out;
	}
	
	// isEmpty
	public boolean isEmpty() {
		return !(size() > 0);
	}
}
