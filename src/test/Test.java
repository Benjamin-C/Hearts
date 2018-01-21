package test;

import java.util.ArrayList;
import debug.Output;
import game.GameHandler;

public class Test {
	public static void main(String args[])  {
		int repeat = 64;
		ArrayList<Integer> score = new ArrayList<Integer>();
		ArrayList<Integer> hands = new ArrayList<Integer>();
		Output out = new Output();
		long start = System.currentTimeMillis();
		for(int i = 0; i < repeat; i++) {
			out.setNum(i);
			GameHandler g = new GameHandler(out);
			g.start();
			out.println(g.getTotalScore());
			score.add(g.getTotalScore());
			hands.add(g.getHands());
		}
		long time = System.currentTimeMillis() - start;
		
		out.println(score);
		out.println(hands);
		double tot = 0;
		int hds = 0;
		for(int i = 0; i < score.size(); i++) {
			tot = tot + score.get(i);
			hds = hds + hands.get(i);
		}
		out.println("tot " + tot + " | hds " + hds);
		tot = tot / (double) hds;
		out.println("Average hand score: " + tot);
		out.println("Time: " + time);
		double hps = (double) hds / ((double) time * 1000.0);
		out.println("hps: " + hps); 
	}
}
