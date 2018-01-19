package debug;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Output {
	
	FileWriter fileWriter;
    PrintWriter printWriter;
    
    public Output() {
		try {
			fileWriter = new FileWriter("log.txt");
			printWriter = new PrintWriter(fileWriter);
		} catch (IOException e) {}
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {  
                printWriter.close();
           } 
		});
	}
	
	public void println(Object msg) {
		String out = stackTrace(0, 1);
		if(out.length() < 52) {
			out = out + "\t";
		}
		out = out + "\t";
		System.out.println(out + msg);
		printWriter.println(out + msg);
	}
	
	public String stackTrace() {
		return stackTrace(0, 1);
	}
	public String stackTrace(int rel) {
		return stackTrace(rel, 1);
	}
	public String stackTrace(int rel, int step) {
		StackTraceElement loc = Thread.currentThread().getStackTrace()[2 + step];
		return loc.getClassName() + "." + loc.getMethodName() + "(" + loc.getFileName() + ":" + (loc.getLineNumber() + rel) + ")";
	}
}
