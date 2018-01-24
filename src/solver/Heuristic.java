package solver;

import Exception.*;

public interface Heuristic {
	
	// The heuristic's name
	public String name = "Heuristic";
	
	// Get its name
	public String getName();
	
	// Get the h value of a state
	public abstract int h(String state, State S) throws VehiclesIntersectException, VehiclesInvalidException ;
	
}
