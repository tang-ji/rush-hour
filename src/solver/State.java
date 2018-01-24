package solver;

import java.util.*;

public class State {
	// Record the last move to this state 
	protected Move lastMove;
	// The window for executions
	protected Window win;
	// Contain the possible moves in this state
	protected LinkedList<Move> possibleMoves;
	
	// Intialization
	public State(Window w, Move m) {
		win = w;
		lastMove = m;
		possibleMoves = new LinkedList<>();
	}
	
	// Get all the possible moves for vehicle v and return a LinkedList of Moves
	public LinkedList<Move> getPossibleMoves(Vehicle v, HashMap<String, State> log) {
		LinkedList<Move> L = new LinkedList<Move>();
		// For the positive direction
		int i = 1;
		while(true) {
			if(win.isMovable(v, i) && !isInLog(getLogString(win, v, i), log)) L.add(new Move(v, i));
			else break;
			i++;
		}
		// For the negative direction
		int j = -1;
		while(true) {
			if(win.isMovable(v, j) && !isInLog(getLogString(win, v, j), log)) L.add(new Move(v, j));
			else break;
			j--;
		}
		return L;
	}
	
	// Update all the possible moves for all the cars in this state
	public void updatePossibleMoves(HashMap<String, State> log) {
		possibleMoves.clear();
		for(Vehicle v : win.getVehicles().values()) {
			possibleMoves.addAll(getPossibleMoves(v, log));
		}
	}
	
	// Get the hash code of the state after this movement for this state
	public String getLogString(Move m) {
		if(m == null) return getLogString(win, null, 0);
		return getLogString(win, m.vehicle, m.cases);
	}
	
	// Get the hash code of the state by reversing this movement for this state
	public String getLogString_R(Move m) {
		return getLogString(win, m.vehicle, -m.cases);
	}
	
	// Get the hash code of the state after vehicle V going cases for this state
	public String getLogString(Window w, Vehicle V, int cases) {
		StringBuilder s = new StringBuilder();
		for(Vehicle v : w.getVehicles().values()) {
			int movedCases = w.moved[v.label];
			if(V != null && v == V) movedCases += cases;
			s.append(movedCases >= 0 ? '+' : '-');
			s.append(Math.abs(movedCases));
		}
		return s.toString();
	}
	
	// Check out if this state is in the history
	public boolean isInLog(String s, HashMap<String, State> log) {
		return log.containsKey(s);
	}
	
}
