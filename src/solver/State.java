package solver;

import java.util.*;

import Exception.*;

public class State {
	protected Window win;
	protected HashSet<String> log;
	public ArrayList<Window> windows;
	public ArrayList<Window> possibleWindows;
	protected ArrayList<Move> possibleMoves;
	public boolean won = false;
	
	public State(Window w) {
		windows = new ArrayList<>();
		windows.add(w);
		possibleWindows = new ArrayList<>();
		possibleMoves = new ArrayList<>();
		
		log = new HashSet<String>();
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < Vehicle.vehicle_number; i++) s.append("+0");
		log.add(s.toString());
	}
	
	public String getLogString(Window w, Vehicle V, int cases) {
		StringBuilder s = new StringBuilder();
		for(Vehicle v : w.getVehicles().values()) {
			int movedCases = w.moved[v.label];
			if(v == V) movedCases += cases;
			s.append(movedCases >= 0 ? '+' : '-');
			s.append(movedCases);
		}
		return s.toString();
	}
	
	public boolean isInLog(String s) {
		return log.contains(s);
	}
	
	public ArrayList<Move> getPossibleMoves(Vehicle v) {
		ArrayList<Move> L = new ArrayList<Move>();
		int i = 1;
		while(true) {
			if(win.isMovable(v, i) && !isInLog(getLogString(win, v, i))) L.add(new Move(v, i));
			else break;
			i++;
		}
		int j = -1;
		while(true) {
			if(win.isMovable(v, j) && !isInLog(getLogString(win, v, j))) L.add(new Move(v, j));
			else break;
			j--;
		}
		return L;
	}
	
	public void updatePossibleMoves() {
		possibleMoves.clear();
		for(Vehicle v : win.getVehicles().values()) {
			possibleMoves.addAll(getPossibleMoves(v));
		}
	}

	public Window clone(Window w) {
		Window wtemp = new Window(w.width(), w.height());
		wtemp.setVehicles(w.getVehicles());
		int[][] plantemp = new int[w.width()][w.height()];
		for(int i = 0; i < w.width(); i++) {
			plantemp[i] = w.getPlan()[i].clone();
		}
		wtemp.setPlan(plantemp);
		wtemp.his = new ArrayList<Move>(w.his);
		wtemp.moved = w.moved.clone();
		return wtemp;
	}
	
	public void updateWindows() throws VehiclesIntersectException, VehiclesInvalidException {
		for(Window w : windows) {
			win = w;
			if(win.isWin()) {
				won = true;
				System.out.println("Game win!");
				System.out.println(win);
				System.out.println("With " + win.his.size() + " steps:");
				for(Move m : win.his) System.out.println(m);
				return;
			}
			updatePossibleMoves();
			for(Move m : possibleMoves) {
				Window wtemp = clone(win);
				log.add(m.getLogString(wtemp));
				m.execute(wtemp);
				possibleWindows.add(wtemp);
			}
		}
		windows = (ArrayList<Window>) possibleWindows.clone();
		possibleWindows.clear();
	}
	
	public String tellPossibleMoves() {
		String ret = "";
		for(Move m : possibleMoves) ret += m + "\n";
		return ret;
	}
}
