package test;

import java.util.ArrayList;
import debug.Output;
import game.GameHandler;

public class Test {
	public static void main(String args[])  {
		int repeat = 1;
		ArrayList<Integer> score = new ArrayList<Integer>();
		Output out = new Output();
		for(int i = 0; i < repeat; i++) {
			GameHandler g = new GameHandler(out);
			g.start();
			out.println(g.getTotalScore());
			score.add(g.getTotalScore());
		}
		out.println(score);
		double tot = 0;
		for(int i = 0; i < score.size(); i++) {
			tot = tot + score.get(i);
		}
		tot = tot / (score.size() * 4);
		out.println("Average hand score: " + tot);
	}
}
