package solver;

import java.util.*;

public class State {
	protected Move lastMove;
	protected Window win;
	protected LinkedList<Move> possibleMoves;
	
	public State(Window w, Move m) {
		win = w;
		lastMove = m;
		possibleMoves = new LinkedList<>();
	}
	
	public LinkedList<Move> getPossibleMoves(Vehicle v, HashMap<String, State> log) {
		LinkedList<Move> L = new LinkedList<Move>();
		int i = 1;
		while(true) {
			if(win.isMovable(v, i) && !isInLog(getLogString(win, v, i), log)) L.add(new Move(v, i));
			else break;
			i++;
		}
		int j = -1;
		while(true) {
			if(win.isMovable(v, j) && !isInLog(getLogString(win, v, j), log)) L.add(new Move(v, j));
			else break;
			j--;
		}
		return L;
	}
	
	public void updatePossibleMoves(HashMap<String, State> log) {
		possibleMoves.clear();
		for(Vehicle v : win.getVehicles().values()) {
			possibleMoves.addAll(getPossibleMoves(v, log));
		}
	}
	
	public String getLogString(Move m) {
		if(m == null) return getLogString(win, null, 0);
		return getLogString(win, m.vehicle, m.cases);
	}
	
	public String getLogString_R(Move m) {
		return getLogString(win, m.vehicle, -m.cases);
	}
	
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
	
	public boolean isInLog(String s, HashMap<String, State> log) {
		return log.containsKey(s);
	}
	
	public String tellPossibleMoves() {
		String ret = "";
		for(Move m : possibleMoves) ret += m + "\n";
		return ret;
	}
}
