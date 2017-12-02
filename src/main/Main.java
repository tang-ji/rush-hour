package main;

import java.io.File;

import view.InvalidFileException;
import view.VehiclesIntersectException;
import view.VehiclesInvalidException;
import view.Window;

public class Main {
	
	protected static File file(String S) {
		String dir = System.getProperty("user.dir");
		return new File(dir + "/src/resources/" + S);
	}

	public static void main(String[] args) throws VehiclesIntersectException, VehiclesInvalidException, InvalidFileException {
		Window win = new Window(file("RushHour1.txt"));
		System.out.println(win);
	}

}
