package solver;

import java.io.*;
import java.util.*;

import Exception.*;

public class Window {

	private HashMap<Integer, Vehicle> vehicles;
	private int[][] plan;
	protected int[] moved;
	protected ArrayList<Move> his;
	private int width;
	private int height;
	
	public Window(int w, int h) {
		this.width = w;
		this.height = h;
		plan = new int[width][height];
		this.vehicles = new HashMap<Integer, Vehicle>();
		his = new ArrayList<>();
	}
	
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
		System.out.println("Initialized successfully!");
	}
	
	public int[][] getPlan() {
		return plan;
	}
	
	public int width() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int height() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public HashMap<Integer, Vehicle> getVehicles() {
		return vehicles;
	}
	
	public void setVehicles(HashMap<Integer, Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
	public Vehicle getVehicle(int label){
		return vehicles.get(new Integer(label));
	}
	
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
	
	
	public boolean isValid(int x, int y) {
		return x >= 0 && x < this.width && y >= 0 && y < this.height;
	}
	
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
	
	public void remove(Vehicle V) {
		vehicles.remove(V);
		for(int i = getLeft(V); i < getRight(V); i++) {
			for(int j = getUp(V); j < getDown(V); j++) {
				plan[i][j] = 0;
			}
		}
	}
	
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
	
	public void move(Vehicle V, int cases) throws VehiclesIntersectException, VehiclesInvalidException {
		remove(V);
		moved[V.label] += cases;
		add(V);
		his.add(new Move(V, cases));
	}
	
	public boolean isWin() {
		Vehicle v = vehicles.get(new Integer(1));
		for(int i = getRight(v); i < width; i++) {
			if(plan[i][getUp(v)] != 0) return false;
		}
		return true;
	}
	
	public String toString() {
		StringBuilder S = new StringBuilder();
			for(int j = 0; j < plan[0].length; j++) {
				for(int i = 0; i < plan.length; i++) {
				S.append(plan[i][j]);
				S.append(' ');
			}
			S.append('\n');
		}
		return S.toString();
	}
	
	
}
