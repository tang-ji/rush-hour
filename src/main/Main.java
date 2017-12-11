package main;

import java.io.File;
import java.util.*;

import solver.*;
import Exception.*;

public class Main {
	
	protected static File file(String S) {
		String dir = System.getProperty("user.dir");
		return new File(dir + "/src/resources/" + S);
	}

	public static void main(String[] args) throws VehiclesIntersectException, VehiclesInvalidException, InvalidFileException {
		Window win = new Window(file("RushHour1.txt"));
		System.out.println(win);
		
		long startTime = System.currentTimeMillis();
		BFS b = new BFS(win);
		LinkedList<Move> l = b.solve();
		long endTime = System.currentTimeMillis();
		
		long Time = endTime - startTime;
		System.out.println(l);
		System.out.print("We need " + l.size() + " steps. ");
		System.out.printf("(Elapsed time: %d ms.)", Time);
	}

}
