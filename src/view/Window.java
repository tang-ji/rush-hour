package view;

import java.io.*;
import java.util.*;

import main.Vehicle;

public class Window {

	private ArrayList<Vehicle> vehicles;
	private Vehicle[][] plan;
	private int width;
	private int height;
	
//	public Window(int w, int h) {
//		this.width = w;
//		this.height = h;
//		plan = new Vehicle[width][];
//		for(int i = 0; i < width; i++)
//			plan[i] = new Vehicle[height];
//		this.vehicles = new ArrayList<Vehicle>();
//	}
	
	public Window(File f) throws VehiclesIntersectException, VehiclesInvalidException, InvalidFileException {
		List<String> S = FileToStrings(f);
		if(S.size() < 2) throw new InvalidFileException();
		
		this.height = this.width = Integer.parseInt(S.get(0));
		Vehicle.vehicle_number = Integer.parseInt(S.get(1));
		plan = new Vehicle[width][];
		for(int i = 0; i < width; i++)
			plan[i] = new Vehicle[height];
		this.vehicles = new ArrayList<Vehicle>();
		
		for(int i = 2; i < S.size(); i++) {
			Vehicle temp = new Vehicle(S.get(i));
			add(temp);
		}
		System.out.println("Initialized successfully!");
	}
	
	public Vehicle[][] getPlan() {
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
	
	
	public boolean isValid(int x, int y) {
		return x >= 0 && x < this.width && y >= 0 && y < this.height;
	}
	
	public boolean isFree(int x, int y) {
		return plan[x][y] == null;
	}
	
	protected void add(Vehicle V) throws VehiclesIntersectException, VehiclesInvalidException {
		vehicles.add(V);
		for(int i = V.left(); i < V.right(); i++) {
			for(int j = V.up(); j < V.down(); j++) {
				if(isValid(i, j)) {
					if(isFree(i,j)) plan[i][j] = V;
					else throw new VehiclesIntersectException();
				}
				else throw new VehiclesInvalidException();
			}
		}
	}
	
	public String toString() {
		StringBuilder S = new StringBuilder();
			for(int j = 0; j < plan[0].length; j++) {
				for(int i = 0; i < plan.length; i++) {
				if(plan[i][j] != null) S.append(plan[i][j]);
				else S.append(0);
				S.append(' ');
			}
			S.append('\n');
		}
		return S.toString();
	}
	
}
