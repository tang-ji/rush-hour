package solver;

import Exception.*;
import java.util.*;

public class HeuristicCarBlock implements Heuristic {

	public String name = "HeuristicCarBlock";
	
	public String getName(){
		return name;
	}
	
	public int h(String state, State S) throws VehiclesIntersectException, VehiclesInvalidException {
		Vehicle v1 = S.win.getVehicle(1);
		int y = S.win.getUp(v1);
		// A set for storing all the cars who block the red car
		Set<Integer> B = new HashSet<>();
		for(int x = S.win.getRight(v1); x < S.win.width(); x++) {
			if(S.win.getPlan()[x][y] != 0) {
				B.add(S.win.getPlan()[x][y]);
			}
		}
		// A set for storing all the cars who block the cars in the group B
		Set<Integer> b = new HashSet<>();
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
		return B.size();
	}

}
