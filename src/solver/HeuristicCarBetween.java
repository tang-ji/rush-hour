package solver;

import Exception.*;

public class HeuristicCarBetween implements Heuristic {

	public String name = "HeuristicCarBetween";
	
	public String getName(){
		return name;
	}
	
	public int h(String state, State S) throws VehiclesIntersectException, VehiclesInvalidException {
		Vehicle v1 = S.win.getVehicle(1);
		int y = S.win.getUp(v1), sum = 0;
		// Get the cars between the red car and the exit
		for(int x = S.win.getRight(v1); x < S.win.width(); x++) {
			if(S.win.getPlan()[x][y] != 0) sum++;
			}
		return sum;
	}

}
