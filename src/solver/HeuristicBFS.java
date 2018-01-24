package solver;

import Exception.*;

public class HeuristicBFS implements Heuristic {
	
	public String name = "BFS";
	
	public String getName(){
		return name;
	}
	
	public int h(String state, State S) throws VehiclesIntersectException, VehiclesInvalidException {
		return 1;
	}
	
}
