package solver;

import java.util.*;

import Exception.*;

public class BFS {
	public HashMap<String, State> log;
	public LinkedList<String> possibleStates;
	public Window w;
	public State state;
	public boolean won = false;

	public BFS(Window win) throws VehiclesIntersectException, VehiclesInvalidException {
		w = win;
		log = new HashMap<>();
		possibleStates = new LinkedList<String>();
		this.state = new State(w, null);
		log.put(state.getLogString(null), this.state);
		getPossibleStates(possibleStates);
	}
	
	public void getPossibleStates(LinkedList<String> States) throws VehiclesIntersectException, VehiclesInvalidException {
		state.updatePossibleMoves(log);
		for(Move m : state.possibleMoves) {
			String stateHash = state.getLogString(m);
			log.put(stateHash, new State(w, m));
			States.add(stateHash);
		}
		state.possibleMoves.clear();
	}
	
	public void updatePossibleStates() throws VehiclesIntersectException, VehiclesInvalidException {
		LinkedList<String> newPossibleStates = new LinkedList<>();
		while(!possibleStates.isEmpty()) {
			String stateHash = possibleStates.pop();
			w.changeState(stateHash);
			state = log.get(stateHash);
			getPossibleStates(newPossibleStates);
			if(w.isWin()) {
				won = true;
				break;
			}
		}
		possibleStates = newPossibleStates;
	}
	
	public LinkedList<Move> solve() throws VehiclesIntersectException, VehiclesInvalidException {
		LinkedList<Move> his = new LinkedList<>();
		while(!won) {
			updatePossibleStates();
		}
		while(state.lastMove != null) {
			his.add(state.lastMove);
			State lastState = log.get(state.getLogString_R(state.lastMove));
			w.changeState(state.getLogString_R(state.lastMove));
			state = lastState;
		}
		Collections.reverse(his);
		return his;
	}

}
