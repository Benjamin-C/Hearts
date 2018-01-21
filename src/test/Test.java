package test;

import java.util.ArrayList;

import game.GameHandler;
import inputOutput.Output;

public class Test {
	public static void main(String args[])  {
		humanTest();
	}
	
	public static void humanTest() {
		Output out = new Output(false, true);
		GameHandler g = new GameHandler(out, true);
		g.start();
	}
	
	public static void autoTest() {
		int repeat = 1; // Keep less than
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
		out.setNum(-1);
		out.println(score);
		out.println(hands);
		double tot = 0.0;
		int hds = 0;
		for(int i = 0; i < score.size(); i++) {
			tot = tot + score.get(i);
			hds = hds + hands.get(i);
		}
		out.println("tot " + tot + " | hds " + hds + " | tim " + time);
		tot = tot / (double) hds;
		out.println("Average hand score: " + tot);
		out.println("Time: " + time);
		double hps = (double) hds / ((double) time / 1000.0);
		out.println("hps: " + hps); 
	}
}
