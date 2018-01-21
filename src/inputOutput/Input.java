package inputOutput;

import java.util.Scanner;

public class Input {
	
	private Scanner scan;
	
	public Input() {
		scan = new Scanner(System.in);
	}
	
	public String getLine() {
		return scan.nextLine();
	}
}
