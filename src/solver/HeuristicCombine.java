package solver;

import java.util.*;

import Exception.*;

public class HeuristicCombine implements Heuristic {

	public String name = "HeuristicCombine";
	
	public String getName(){
		return name;
	}
	
	public int h(String state, State S) throws VehiclesIntersectException, VehiclesInvalidException {
		Vehicle v1 = S.win.getVehicle(1);
		int y = S.win.getUp(v1);
		Set<Integer> B = new HashSet<>();
		boolean blank = false;
		// A set for storing all the cars who block the red car
		for(int x = S.win.getRight(v1); x < S.win.width(); x++) {
			if(S.win.getPlan()[x][y] != 0) {
				B.add(S.win.getPlan()[x][y]);
			}
			else blank = true;
		}
		Set<Integer> b = new HashSet<>();
		// A set for storing all the cars who block the cars in the group B
		for(int i : B) {
			Vehicle v = S.win.getVehicle(i);
			int up = S.win.getUp(v1);
			if(up >= v.length && S.win.height() - up > v.length) continue;
			if(up >= v.length) {
				for(int l = up - 1; l >= up - v.length; l--) {
					if(S.win.getPlan()[S.win.getLeft(v)][l] != 0) b.add(S.win.getPlan()[S.win.getLeft(v)][l]);
				}
			}
			else {
				for(int l = up + 1; l <= up + v.length; l++) {
					if(S.win.getPlan()[S.win.getLeft(v)][l] != 0) b.add(S.win.getPlan()[S.win.getLeft(v)][l]);
				}
			}
		}
		// Remove the duplicate cars
		B.addAll(b);
		return blank ? B.size() : B.size() - 1;
	}

}
