package solver;

import Exception.*;

public class Vehicle {
	// Set the car label automatically from '1'
	public static int vehicle_number = 1;
	
	protected int label, length, X, Y;
	protected char orientation;
	
	public Vehicle(int x, int y, int len, char orientation) {
		this.X = x;
		this.Y = y;
		this.length = len;
		this.orientation = orientation;
		label = ++vehicle_number;
	}
	
	public Vehicle(int x, int y, int len, char orientation, int label) {
		this.X = x;
		this.Y = y;
		this.length = len;
		this.orientation = orientation;
		this.label = label;
	}
	
	public Vehicle(String s) throws InvalidFileException {
		String[] split = s.split(" ");
		if(split.length != 5) throw new InvalidFileException();
		
		this.label = Integer.parseInt(split[0]);
		if(label > vehicle_number || label < 1) throw new InvalidFileException();
		
		this.orientation = split[1].charAt(0);
		if(this.orientation != 'v' && this.orientation != 'h') throw new InvalidFileException();

		this.length = Integer.parseInt(split[2]);
		if(this.length < 1) throw new InvalidFileException();
		
		this.X = Integer.parseInt(split[3]);
		if(this.X < 0) throw new InvalidFileException();

		this.Y = Integer.parseInt(split[4]);
		if(this.Y < 0) throw new InvalidFileException();
		
	}
	
	public int label() {
		return label;
	}
	
	public int length() {
		return length;
	}
	
	public char orientation() {
		return orientation;
	}
	
//	public int X() {
//		return X;
//	}
//	
//	public int Y() {
//		return Y;
//	}
	
	public int up() {
		return Y - 1;
	}
	
	public int down() {
		return orientation == 'v' ? Y - 1 + length : Y;
	}
	
	public int left() {
		return X - 1;
	}
	
	public int right() {
		return orientation == 'h' ? X - 1 + length : X;
	}
	
	public String toString() {
		return label + "";
	}
}
