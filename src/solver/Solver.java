package solver;

import java.util.*;
import Exception.*;

public class Solver {
	// A hash map for recording all the logs
	public HashMap<String, State> log;
	// A priority queue for recording all the possible states waiting to be searched
	public PriorityQueue<String> possibleStates;
	// A board window
	public Window w;
	protected Heuristic heuristic;
	// Present the current state
	public State state;
	// The sign of win
	public boolean won = false;

	// Initialization of the class solver
	public Solver(Window win, Heuristic h) throws VehiclesIntersectException, VehiclesInvalidException {
		w = win;
		log = new HashMap<>();
		heuristic = h;
		this.state = new State(w, null);
		// The priority queue for recording the possible states by its h value
		possibleStates = new PriorityQueue<String>(1, new Comparator<String>(){
			public int compare(String s1, String s2) {
				try {
					return heuristic.h(s1, state) - heuristic.h(s2, state);
				} catch (VehiclesIntersectException | VehiclesInvalidException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
		// Record the initial state
		log.put(state.getLogString(null), this.state);
		// Get all the possible states for the initial state
		getPossibleStates(possibleStates);
	}
	
	public void getPossibleStates(PriorityQueue<String> States) throws VehiclesIntersectException, VehiclesInvalidException {
		state.updatePossibleMoves(log);
		// Execute every possible move to get the possible state's hash code
		for(Move m : state.possibleMoves) {
			String stateHash = state.getLogString(m);
			log.put(stateHash, new State(w, m));
			States.add(stateHash);
			
		}
		state.possibleMoves.clear();
	}
	
	public void updatePossibleStates() throws VehiclesIntersectException, VehiclesInvalidException {
		PriorityQueue<String> newPossibleStates = new PriorityQueue<String>(1, new Comparator<String>(){
			public int compare(String s1, String s2) {
				try {
					return heuristic.h(s1, state) - heuristic.h(s2, state);
				} catch (VehiclesIntersectException | VehiclesInvalidException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
		while(!possibleStates.isEmpty()) {
			// Poll the lowest h value state
			String stateHash = possibleStates.poll();
			// Change the window to the state (stateHash)
			w.changeState(stateHash);
			state = log.get(stateHash);
			if(w.isWin()) {
				won = true;
				break;
			}
			// Get the state corresponded to the state hash code
			getPossibleStates(newPossibleStates);
			// To check out if it's win
		}
		possibleStates = newPossibleStates;
	}
	
	public LinkedList<Move> solve() throws VehiclesIntersectException, VehiclesInvalidException {
		LinkedList<Move> his = new LinkedList<>();
		// To solve the problem
		while(!won) {
			updatePossibleStates();
		}
		// To get all the move logs from the last win state
		while(state.lastMove != null) {
			his.add(state.lastMove);
			State lastState = log.get(state.getLogString_R(state.lastMove));
			w.changeState(state.getLogString_R(state.lastMove));
			state = lastState;
		}
		// Reverse the movement to get the solving process
		Collections.reverse(his);
		return his;
	}
	
	public void solve_test() throws VehiclesIntersectException, VehiclesInvalidException {
		// Only solve the state without telling the process
		while(!won) {
			updatePossibleStates();
		}
	}

}
