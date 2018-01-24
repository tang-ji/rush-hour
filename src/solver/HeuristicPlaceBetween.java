package solver;

import Exception.*;

public class HeuristicPlaceBetween implements Heuristic {

	public String name = "HeuristicPlaceBetween";
	
	public String getName(){
		return name;
	}
	
	public int h(String state, State S) throws VehiclesIntersectException, VehiclesInvalidException {
		Vehicle v1 = S.win.getVehicle(1);
		int y = S.win.getUp(v1), sum = 0;
		boolean blank = false;
		for(int x = S.win.getRight(v1); x < S.win.width(); x++) {
			if(S.win.getPlan()[x][y] != 0) sum++;
			else blank = true;
			}
		// According to the regular in the report
		if(!blank) sum--;
		return sum;
	}

}