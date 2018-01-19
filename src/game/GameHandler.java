package game;

import pile.*;

import java.util.ArrayList;

import debug.*;

public class GameHandler {
	
	private Output out;
	private ArrayList<Player> users;
	private ArrayList<Player> player;
	private int playerCount;
	private int leader;
	private Deck deck;
	Table table;
	int totalScore;
		
	public GameHandler(Output o) {
		out = o;
		player = new ArrayList<Player>();
		users = new ArrayList<Player>();
		playerCount = 4;
		leader = -1;
		deck = new Deck();
		table = new Table();
		deck.shuffle();
	}
	
	public int getTotalScore() {
		return player.get(0).getScore() + player.get(1).getScore() + player.get(2).getScore() + player.get(3).getScore();
	}

	public void start() {
		out.println("Begining game");
		for(int i = 0; i < playerCount; i++) {
			users.add(new Player(i));
			player.add(users.get(i));
		}
		
		int maxScore = 0; // Adding playing until 100 points
		int dir = 0;
		int pass = 0;
		while(maxScore < 100) {
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
			out.println("Pass 1");
			whoFirst();
			orderPlayers();
			showPlayerHands(player);
			playGame();
			scoreGame();
			
			out.println("Scores: | 0: " + player.get(0).getScore() + "| 1: " + player.get(1).getScore() + "| 2: " + player.get(2).getScore() + "| 3: " + player.get(3).getScore());
			totalScore = player.get(0).getScore() + player.get(1).getScore() + player.get(2).getScore() + player.get(3).getScore();
			//out.println("Total: " + totalScore + " | Per player game " + (totalScore / 1)); // Show the average points per game
			
			for(int i = 0; i < player.size(); i++) {
				maxScore = Math.max(maxScore, player.get(i).getScore());
			}
		}
		
		out.println("Numbers: | 0: " + player.get(0).getId() + "| 1: " + player.get(1).getId() + "| 2: " + player.get(2).getId() + "| 3: " + player.get(3).getId());
		out.println("Sorted: 
		
		Player minScore = users.get(0);
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getScore() < minScore.getScore()) {
				minScore = users.get(i);
			}
		}
		
		out.println("Winner is " + minScore.getId());
	}
	
	private String runningScoreString() {
		String out = "";
		for(int i = 0; i < player.size(); i++) {
			out = out + " | " + i + "-" + player.get(i).getId() + ": " + player.get(i).getRunninScore();
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
		out.println("Start hand");
		table.clear();
		table.add(player.get(0).getHand().remove(new Card(Suit.SPADE, 2)));
		for(int i = 1; i < player.size(); i++) {
			table.add(player.get(i).playCard(table));
		}
		leader = pickWinner(table);
		out.println("Table: " + table);
		out.println(leader + " won");
		player.get(leader).addTricks(table);
		int score = score();
		out.println(leader + " got " + score + " points and has " + player.get(leader).getHandScore());
		showPlayerHands(player);
		out.println("Running: | 0: " + users.get(0).getRunninScore() + "| 1: " + users.get(1).getRunninScore() + "| 2: " + users.get(2).getRunninScore() + "| 3: " + users.get(3).getRunninScore());

		while(!player.get(0).getHand().isEmpty()) {
			orderPlayers();
			out.println("Start round");
			table.clear();
			for(int i = 0; i < player.size(); i++) {
				table.add(player.get(i).playCard(table));
			}
			leader = pickWinner(table);
			out.println("Table: " + table);
			out.println(leader + " won");
			score = score();
			out.println(leader + " got " + score + " points and has " + player.get(leader).getHandScore());
			player.get(leader).addTricks(table);
			showPlayerHands(player);
			out.println("Running: | 0: " + users.get(0).getRunninScore()+ "| 1: " + users.get(1).getRunninScore() + "| 2: " + users.get(2).getRunninScore() + "| 3: " + users.get(3).getRunninScore());
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
			out.println(i + ": " + player.get(i).getHand().toString());
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
		Hand pass[] = new Hand[player.size()];
		for(int i = 0; i < player.size(); i++) {
			Hand temp = player.get(i).getHand();
			Card c = null;
			int passes = 0;
			temp.sort();
			int num = 0;
			int val = CardValue.ACE;
			pass[i] = new Hand();
			
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
					pass[i].add(player.get(i).getHand().remove(c));
					passes++;
				}
				num++;
			}
			out.println(i + " is Passing " + pass[i]);
		}
		
		out.println("dir " + dir);
		for(int i = 0; i < player.size(); i++) {
			int loc = i;
			if(i + dir < 0) {
				loc = player.size() - i - 1;
			} else if(i + dir > player.size() - 1) {
				loc = i - player.size() + dir;
			} else {
				loc = i + dir;
			}
			player.get(loc).getHand().addAll(pass[i]);
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
	}

}
