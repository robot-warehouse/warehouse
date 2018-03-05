package rp.assignments.team.warehouse.server.route.planning;

import rp.assignments.team.warehouse.server.Location;

import java.util.ArrayList;
import java.util.Collections;

public class AStar {

	public static ArrayList<State> findPath(Location start, Location goal) {
		ArrayList<State> open = new ArrayList<>();
		ArrayList<State> closed = new ArrayList<>();
		ArrayList<State> path;
		ArrayList<State> neighbours;

		State goalState = new State(goal);
		State startState = new State(start);

		boolean check = validate(startState, goalState);

		if (!check) {
			return null;
		}

		while (true) {
		    State current;
			if (open.size() == 0) {
				current = startState;
			} else
				current = findSmallestF(open);

			open.remove(current);
			closed.add(current);

			if (current.check(current, goalState)) {
			//	System.out.println(current.toString());
				// System.out.println(current.getParent().toString());
				path = getPath(current, startState);
//				for (State each : result)
//					System.out.println(each.toString());
				return path;
			}

			neighbours = current.getNeighbours(current, startState, goalState);

			for (State node1 : neighbours) {
				if (contains(Data.obstacle, node1) || contains(closed, node1))
					continue;
				if (!contains(open, node1) || startState.getDistance(node1) < node1.getG()) {
					node1.setF();
					node1.setParent(current);
					if (!contains(open, node1))
						open.add(node1);
				}
			}
		}
	}

    private static State findSmallestF(ArrayList<State> nodes) {
        State result = nodes.get(0);

        for (int i = 0; i < nodes.size() - 1; i++) {
            if (nodes.get(i).f < nodes.get(i + 1).f) {
                result = nodes.get(i);
            }
        }

        return result;
    }

	private static ArrayList<State> getPath(State current, State start) {
	    ArrayList<State> path = new ArrayList<>();

	    State node = current;

		path.add(current);
		while (!node.check(node, start)) {
			path.add(node.getParent());
			// System.out.println(node.getParent().toString());
			node = node.getParent();
		}

		Collections.reverse(path);

		return path;
	}

    private static boolean validate(State start, State goal) {
        return start.getX() >= State.xMin && start.getX() <= State.xMax && goal.getY() >= State.yMin
                && goal.getY() <= State.yMax && !contains(Data.obstacle, start) && !contains(Data.obstacle, goal);
    }

	private static boolean contains(ArrayList<State> list, State s) {
		// System.out.println(list.size());
		for (State each : list) {
			// System.out.println(each.getX() + "+" + each.getY());
			if (s.check(each, s)) {
				// System.out.println(s.check(each, s));
				return true;
			}
		}

		return false;
	}
}
