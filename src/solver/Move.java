package solver;

import Exception.*;

public class Move {
	private Vehicle vehicle;
	private int cases;
	
	public Move(Vehicle vehicle, int cases) {
		this.vehicle = vehicle;
		this.cases = cases;
	}
	
	public String getLogString(Window w) {
		StringBuilder s = new StringBuilder();
		for(Vehicle v : w.getVehicles().values()) {
			int movedCases = w.moved[v.label];
			if(v == vehicle) movedCases += cases;
			s.append(movedCases >= 0 ? '+' : '-');
			s.append(movedCases);
		}
		return s.toString();
	}
	
	public void execute(Window w) throws VehiclesIntersectException, VehiclesInvalidException {
		w.move(vehicle, cases);
	}
	
	public void unexecute(Window w) throws VehiclesIntersectException, VehiclesInvalidException {
		w.move(vehicle, -cases);
	}
	
	public String toString() {
		String ret = "Move the car " + vehicle.label() + " to " + Math.abs(cases) + " cases to the ";
		if(vehicle.orientation == 'h') {
			if(cases > 0) ret += "right";
			else ret += "left";
		}
		else {
			if(cases > 0) ret += "down";
			else ret += "up";
		}
		return ret;
	}
}
