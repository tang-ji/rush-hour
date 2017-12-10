package main;

import java.io.File;

import solver.*;
import Exception.*;

public class Main {
	
	protected static File file(String S) {
		String dir = System.getProperty("user.dir");
		return new File(dir + "/src/resources/" + S);
	}

	public static void main(String[] args) throws VehiclesIntersectException, VehiclesInvalidException, InvalidFileException {
		Window win = new Window(file("RushHour1.txt"));
		State s = new State(win);
		System.out.println(win);
		while(!s.won) s.updateWindows();
	}

}
