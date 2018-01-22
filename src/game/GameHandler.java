package game;

import pile.*;
import java.util.ArrayList;

import inputOutput.*;

public class GameHandler {
	
	private Output out;
	private ArrayList<Player> users;
	private ArrayList<Player> player;
	private int playerCount;
	private int leader;
	private Deck deck;
	private Table table;
	//private int totalScore;
	private int hands;
	private boolean hasHuman;
		
	public GameHandler(Output o, boolean human) {
		out = o;
		player = new ArrayList<Player>();
		users = new ArrayList<Player>();
		playerCount = 4;
		leader = -1;
		deck = new Deck();
		table = new Table();
		deck.shuffle();
		hands = 0;
		hasHuman = human;
	}
	public GameHandler(Output o) {
		this(o, true);
	}
	
	public int getTotalScore() {
		return player.get(0).getScore() + player.get(1).getScore() + player.get(2).getScore() + player.get(3).getScore();
	}
	public int getHands() {
		return hands;
	}

	public void start() {
		HumanReadable humanReadable = new HumanReadable();
		out.println("Beginning game " + humanReadable.date(System.currentTimeMillis()));
		
		for(int i = 0; i < playerCount; i++) {
			if(i == 0 && hasHuman == true) {
				users.add(new Player(i, true));
			} else {
				users.add(new Player(i));
			}
			player.add(users.get(i));
		}
		
		int maxScore = 0; // Adding playing until 100 points
		int dir = -1;
		int pass = 0;
		String directionString = "";
		do {
			dir++;
			switch(dir%4) {
			case 0: pass = 1; break;
			case 1: pass = -1; break;
			case 2: pass = 2; break;
			case 3: pass = 0; break;
			}
			setupGame();
			showPlayerHands(player);
			pass(pass);
			showPlayerHands(player);
			out.println("Pass " + pass);
			whoFirst();
			orderPlayers();
			showPlayerHands(player);
			playGame();
			scoreGame();
			
			out.println("|) /\\ | | |\\ | |\\   /\\ \\  / |- |)");
			out.println("|\\ \\/ \\_/ | \\| |/   \\/  \\/  |- |\\");
			out.println("Hand:\t" + handScoreString());
			out.println("Scores:\t" + scoreString());
			
			if(hasHuman) {
				String txt = "";
				for(int i = 0; i < users.size(); i++) {
					if(i != 0) {
						txt = txt + " | ";
					}
					txt = txt + users.get(i).getName() + " : " + users.get(i).getScore();
				}
				System.out.println("Scores: " + txt);
				System.out.println("_  _   _   _  _ ___     _____   _____ ___ ");
				System.out.println("| || | /_\\ | \\| |   \\   / _ \\ \\ / / __| _ \\");
				System.out.println("| __ |/ _ \\| .\' | |) | | (_) \\ V /| _||   /");
				System.out.println("|_||_/_/ \\_\\_|\\_|___/   \\___/ \\_/ |___|_|_\\");
				System.out.println(line());
				                                             
			}
			//totalScore = player.get(0).getScore() + player.get(1).getScore() + player.get(2).getScore() + player.get(3).getScore();
			//out.println("Total: " + totalScore + " | Per player game " + (totalScore / 1)); // Show the average points per game
			
			for(int i = 0; i < player.size(); i++) {
				maxScore = Math.max(maxScore, player.get(i).getScore());
			}
			hands++;
		} while(maxScore < 100);
		
		out.println("Numbers:| 0: " + player.get(0).getId() + "| 1: " + player.get(1).getId() + "| 2: " + player.get(2).getId() + "| 3: " + player.get(3).getId());
		out.println("Sorted:\t| 0: " + users.get(0).getScore() + "| 1: " + users.get(1).getScore() + "| 2: " + users.get(2).getScore() + "| 3: " + users.get(3).getScore());
		
		Player minScore = users.get(0);
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getScore() < minScore.getScore()) {
				minScore = users.get(i);
			}
		}
		
		out.println("Winner is " + minScore.getId());
		System.out.println("Game over");
		System.out.println(minScore.getName() + " won");
	}
	
	private String runningScoreString() {
		String out = "";
		for(int i = 0; i < player.size(); i++) {
			out = out + " | " + i + "-" + player.get(i).getId() + ": " + player.get(i).getRunninScore();
		}
		return out;
	}
	private String scoreString() {
		String out = "";
		for(int i = 0; i < player.size(); i++) {
			out = out + " | " + i + "-" + player.get(i).getId() + ": " + player.get(i).getScore();
		}
		return out;
	}
	private String handScoreString() {
		String out = "";
		for(int i = 0; i < player.size(); i++) {
			out = out + " | " + i + "-" + player.get(i).getId() + ": " + player.get(i).getHandScore();
		}
		return out;
	}
	
	private void scoreGame() {
		int shotMoon = -1;
		for(int i = 0; i < player.size(); i++) {
			if(player.get(i).shotMoon()) {
				shotMoon = i;
			}
		}
		for(int i = 0; i < player.size(); i++) {
			if(shotMoon == -1) {
				player.get(i).addScore(player.get(i).getHandScore());
			} else if (i != shotMoon) {
				player.get(i).addScore(26);
			}
		}
		if(hasHuman) {
			if(shotMoon != -1) {
				System.out.println(users.get(player.get(shotMoon).getId()).getName() + "shot the moon!");
			}
		}
	}

	private void whoFirst() {
		for(int i = 0; i < player.size(); i++) {
			if(player.get(i).getHand().contains(new Card(Suit.SPADE, 2))) {
				leader = i;
			}
		}
		out.println(leader + " is going first");
	}
	
	private void setupGame() {
		table.setHeartsBroken(false);
		deck = new Deck();
		deck.shuffle();
		int pl = 0;
		while(deck.isEmpty() == false) {
			player.get(pl).getHand().add(deck.draw());
			pl++;
			if(pl >= player.size()) {
				pl = 0;
			}
		}
		for(int i = 0; i < player.size(); i++) {
			player.get(i).setTricks(new Pile());
		}
	}
	
	private void playGame() {
		playCards(true);
		while(!player.get(0).getHand().isEmpty()) {
			playCards(false);
		}
	}
	private void playCards(boolean isFirst) {
		if(isFirst) {
			for(int i = 0; i < player.size(); i++) {
				if(player.get(i).getHand().contains(new Card(Suit.CLUB, 2))) {
					leader = i;
					break;
				}
			}
		}
		orderPlayers();
		out.println("Start round");
		table.clear();
		for(int i = 0; i < player.size(); i++) {
			if(i == 0 && isFirst == true) {
				table.add(player.get(0).getHand().remove(new Card(Suit.CLUB, 2)));
				if(player.get(0).isHuman()) {
					System.out.println("You played the 2 of Clubs because it is always first");
				}
			} else {
				table.add(player.get(i).playCard(table));
			}
		}
		leader = pickWinner(table);
		out.println("Table: " + table);
		out.println(leader + "-" + player.get(leader).getId() + " won");
		int score = score();
		player.get(leader).addTricks(table);
		out.println(leader + "-" + player.get(leader).getId() + " got " + score + " points and now has " + player.get(leader).getHandScore());
		showPlayerHands(player);
		out.println("Running:" + runningScoreString());
		if(hasHuman == true) {
			System.out.println("Table: " + table);
			System.out.println(users.get(player.get(leader).getId()).getName() + " took the trick and got " + score + " points");
			String txt = "";
			for(int i = 0; i < users.size(); i++) {
				if(i != 0) {
					txt = txt + " | ";
				}
				txt = txt + users.get(i).getName() + " : " + users.get(i).getScore();
			}
			System.out.println("Here are the scores from last hand: " + txt);
			System.out.println(line());
		}
	}
	
	
	
	private int score() {
		out.println("Scoreing...");
		int score = 0;
		score = score + table.howMany(Suit.HEART);
		if(table.contains(new Card(Suit.SPADE, CardValue.QUEEN))) {
			out.println("Hearts: " + table.howMany(Suit.HEART)+ " | Queen: Yes");
			score = score + 13;
		} else {
			out.println("Hearts: " + table.howMany(Suit.HEART)+ " | Queen: No");
		}
		return score;
	}
	
	private void orderPlayers() {
		for(int i = 0; i < leader; i++) {
			player.add(player.remove(0));
		}
	}
	private void showPlayerHands(ArrayList<Player> player) {
		for(int i = 0; i < player.size(); i++) {
			player.get(i).getHand().sort();
			out.println(i + "-" + player.get(i).getId() + ": " + player.get(i).getHand().toString());
		}
	}
	
	private int pickWinner(Pile t) {
		Suit lead = t.get(0).getSuit();
		int max = t.get(0).getValue();
		int loc = 0;
		for(int i = 0; i < t.size(); i++) {
			if((t.get(i).getValue() > max) && (t.get(i).getSuit() == lead)) {
				max = t.get(i).getValue();
				loc = i;
			}
		}
		return loc;
	}
	private void pass(int dir) {
		if(dir == 0) {
			if(hasHuman) {
				System.out.println("No passing this round");
			}
		} else {
			if(hasHuman) {
				System.out.println("You are passing to " + users.get(toLoc(dir, 0, users.size())).getName());
			}
			Hand pass[] = new Hand[player.size()];
			for(int i = 0; i < player.size(); i++) {
				Hand temp = player.get(i).passCards(out);
				out.println(player.get(i).getId() + " is Passing " + temp);
				pass[i] = temp;
			}
			
			out.println("dir " + dir);
			for(int i = 0; i < player.size(); i++) {
				int loc = toLoc(dir, i, player.size());
				player.get(loc).getHand().addAll(pass[i]);
				if(player.get(loc).isHuman()) {
					System.out.println("You were passed " + pass[i]);
				}
			}
		}
	}
	
	private int toLoc(int to, int from, int size) {
		int loc = from;
		if(from + to < 0) {
			loc = size - from - 1;
		} else if(from + to > size - 1) {
			loc = from - size + to;
		} else {
			loc = from + to;
		}
		return loc;
	}
	private String line() {
			return "--------------------------------------";
		}
}

//		for(int i = 0; i < player.size(); i++) {
//			if(i == (player.size() - dir)) {
//				player.get(i).getHand().addAll(pass[1 - dir]);
//			} else if(i == 0){
//				player.get(i).getHand().addAll(pass[i + dir]);
//			}
//			player.get(i).getHand().sort();
//			if(player.get(i).getHand().contains(new Card(Suit.SPADE, 2))) {
//				leader = i;
//			}
//		}