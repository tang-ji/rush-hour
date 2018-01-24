package solver;

import java.io.*;
import java.util.*;

import Exception.*;

public class Window {

	// The hash map that maps the numbers to the vehicles
	private HashMap<Integer, Vehicle> vehicles;
	// The integer matrix of the board
	private int[][] plan;
	// Present the i-th vehicle has moved moved[i] cases
	protected int[] moved;
	protected ArrayList<Move> his;
	private int width;
	private int height;
	
	// Initialization
	public Window(int w, int h) {
		this.width = w;
		this.height = h;
		plan = new int[width][height];
		this.vehicles = new HashMap<Integer, Vehicle>();
		his = new ArrayList<>();
	}
	
	// Initialization by reading the file
	public Window(File f) throws VehiclesIntersectException, VehiclesInvalidException, InvalidFileException {
		List<String> S = FileToStrings(f);
		if(S.size() < 2) throw new InvalidFileException();
		
		this.height = this.width = Integer.parseInt(S.get(0));
		Vehicle.vehicle_number = Integer.parseInt(S.get(1));
		moved = new int[Vehicle.vehicle_number + 1];
		
		plan = new int[width][height];
		this.vehicles = new HashMap<>();
		
		for(int i = 2; i < S.size(); i++) {
			Vehicle temp = new Vehicle(S.get(i));
			add(temp);
		}
		his = new ArrayList<>();
		// Tell if there is any intersection or invalid car
//		System.out.println("Initialized successfully! (No car intersects or out of boundary)");
	}
	
	
	// Change the state hash code to the board state
		public void changeState(String s) throws VehiclesIntersectException, VehiclesInvalidException {
			// The new moved cases
			int[] newMoved = HashToMoved(s);
			List<Integer> l = new LinkedList<>();
			// Find the cars that have changed their movements
			for(int i = 1; i < moved.length; i++) {
				if(moved[i] != newMoved[i - 1]) {
					remove(vehicles.get(i));
					l.add(i);
				}
			}
			// Re-add the cars that are different to the state hash code
			for(int i : l) {
				moved[i] = newMoved[i - 1];
				add(vehicles.get(i));
			}
		}
		
		// Change the hash code to the moved cases array
		public int[] HashToMoved(String s) {
			int[] ret = new int[vehicles.size()];
			int index = 0, j = 0;
			// Separate the hash string by '+' and '-'
			for(int i = 1; i < s.length(); i++) {
				if(s.charAt(i) == '+' || s.charAt(i) == '-') {
					ret[index++] = Integer.parseInt(s.substring(j, i));
					j = i;
				}
			}
			ret[index] = Integer.parseInt(s.substring(j, s.length()));
			return ret;
		}
	
	public int[][] getPlan() {
		return plan;
	}
	
	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public HashMap<Integer, Vehicle> getVehicles() {
		return vehicles;
	}
	
	// Get the vehicle
	public Vehicle getVehicle(int label){
		return vehicles.get(new Integer(label));
	}
	
	// Read the file
	private List<String> FileToStrings(File f) {
		List<String> List = new ArrayList<>();
		BufferedReader reader = null;
		try{
			String tempString;
			reader = new BufferedReader(new FileReader(f));
			while ((tempString = reader.readLine()) != null) {
				List.add(tempString.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return List;
	}
	
	public void setPlan(int[][] plan) {
		this.plan = plan;
	}
	
	// To check out if there is any invalid car
	public boolean isValid(int x, int y) {
		return x >= 0 && x < this.width && y >= 0 && y < this.height;
	}
	
	// To check out if there is any intersection
	public boolean isFree(int x, int y) {
		return plan[x][y] == 0;
	}
	
	protected int getUp(Vehicle V) {
		return V.orientation == 'v' ? moved[V.label] + V.up() : V.up();
	}
	
	protected int getRight(Vehicle V) {
		return V.orientation == 'h' ? moved[V.label] + V.right() : V.right();
	}
	
	protected int getLeft(Vehicle V) {
		return V.orientation == 'h' ? moved[V.label] + V.left() : V.left();
	}
	
	protected int getDown(Vehicle V) {
		return V.orientation == 'v' ? moved[V.label] + V.down() : V.down();
	}
	
	// Add the car to the board with checking its validity and freedom
	public void add(Vehicle V) throws VehiclesIntersectException, VehiclesInvalidException {
		vehicles.put(V.label, V);
		for(int i = getLeft(V); i < getRight(V); i++) {
			for(int j = getUp(V); j < getDown(V); j++) {
				if(isValid(i, j)) {
					if(isFree(i,j)) plan[i][j] = V.label;
					else throw new VehiclesIntersectException();
				}
				else throw new VehiclesInvalidException();
			}
		}
	}
	
	// To remove the car V
	public void remove(Vehicle V) {
		vehicles.remove(V);
		for(int i = getLeft(V); i < getRight(V); i++) {
			for(int j = getUp(V); j < getDown(V); j++) {
				plan[i][j] = 0;
			}
		}
	}
	
	// To check out if the car V could move n cases or not
	public boolean isMovable(Vehicle V, int cases) {
		int x, y;
		if(V.orientation == 'h') {
			y = getUp(V);
			if(cases > 0) {
				x = getRight(V) - 1 + cases;
				if(!isValid(x, y) || !isFree(x, y)) return false;
			}
			if(cases < 0) {
				x = getLeft(V) + cases;
				if(!isValid(x, y) || !isFree(x, y)) return false;
			}
		}
		else {
			x = getLeft(V);
			if(cases > 0) {
				y = getDown(V) - 1 + cases;
				if(!isValid(x, y) || !isFree(x, y)) return false;
			}
			if(cases < 0) {
				y = getUp(V) + cases;
				if(!isValid(x, y) || !isFree(x, y)) return false;
			}
		}
		return true;
	}
	
	// Move the vehicle V n cases (to the direction positive)
	public void move(Vehicle V, int cases) throws VehiclesIntersectException, VehiclesInvalidException {
		remove(V);
		moved[V.label] += cases;
		add(V);
		his.add(new Move(V, cases));
	}
	
	// Check out if the red car could go to the exit
	public boolean isWin() {
		Vehicle v = vehicles.get(new Integer(1));
		return plan[plan.length - 1][getUp(v)] == 1;
	}
	
	// To show this board
	public String toString() {
		StringBuilder S = new StringBuilder();
			for(int j = 0; j < plan[0].length; j++) {
				for(int i = 0; i < plan.length; i++) {
				if(vehicles.size() > 9 && plan[i][j] < 10) S.append(' ');
				S.append(plan[i][j]);
				S.append(' ');
			}
			S.append('\n');
		}
		return S.toString();
	}
	
	
}
