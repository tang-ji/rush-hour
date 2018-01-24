package main;

import java.io.File;
import java.util.*;

import solver.*;
import Exception.*;

public class Main {
	
	protected static File file(String S) {
		// Get the file path
		String dir = System.getProperty("user.dir");
		return new File(dir + "/src/resources/" + S);
	}
	
	protected static void test(Window win, Heuristic h) throws VehiclesIntersectException, VehiclesInvalidException, InvalidFileException {
		// Test the code for the given window board
		Solver b = new Solver(win, h);
		// Solve the problem without getting the solution
		b.solve_test();
	}
	
	protected static void test(String fileName, Heuristic h) throws VehiclesIntersectException, VehiclesInvalidException, InvalidFileException {
		// Initialize the window board
		Window win = new Window(file(fileName));
		System.out.println(win);
		
		// Time counter
		long startTime = System.currentTimeMillis();
		Solver b = new Solver(win, h);
		// Get the solution
		LinkedList<Move> l = b.solve();
		long endTime = System.currentTimeMillis();
		long Time = endTime - startTime;
		
		// Tell the solution and the running time
		System.out.print("We need " + l.size() + " steps to solve " + fileName + ": \n");
		System.out.println(l);
		System.out.printf("Elapsed time: %d ms by using " + h.getName() + ".\n\n", Time);
	}

	public static void main(String[] args) throws VehiclesIntersectException, VehiclesInvalidException, InvalidFileException {
		// The definitions for the heuristics
		Heuristic[] h = new Heuristic[5];
		h[0] = new HeuristicBFS();
		h[1] = new HeuristicCarBetween();
		h[2] = new HeuristicPlaceBetween();
		h[3] = new HeuristicCarBlock();
		h[4] = new HeuristicCombine();
		
		for(int j = 0; j < 5; j++) {
			String S = "";
			for(int i = 1; i <= 40; i++) {
				Window win;
				if(i <= 9) {
					win = new Window(file("GameP0" + i + ".txt"));
				}
				else {
					win = new Window(file("GameP" + i + ".txt"));
				}
				long startTime = System.currentTimeMillis();
				int k = 1000;
				while(k-- > 0) test(win, h[0]);
				long endTime = System.currentTimeMillis();
				long Time = endTime - startTime;
				S += " " + (int)Time;
			}
			System.out.println("The running time by " + h[j].getName() + " with 40 examples 1000 times are respectively: " + S + " ms.");
		}
		
		// Test the code with printing the solution in the end
		test("GameP01.txt", h[0]);
	}

}
