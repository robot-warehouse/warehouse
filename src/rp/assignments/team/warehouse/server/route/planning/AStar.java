package rp.assignments.team.warehouse.server.route.planning;

import rp.assignments.team.warehouse.server.Location;

import java.util.ArrayList;
import java.util.Collections;

public class AStar {

	public static ArrayList<Location> findPath(Location start, Location goal) {

        ArrayList<State> open = new ArrayList<>();      //Stores nodes available for visiting.
        ArrayList<State> closed = new ArrayList<>();    //Stores already visited nodes
        ArrayList<State> neighbours;                    //Stores the neighbouring nodes of the current node.

        State startState = new State(start);
        State goalState = new State(goal);

        //Return if either the start node or the goal node are invalid.
        if (!contains(Data.obstacle, startState) && !contains(Data.obstacle, goalState)) {
            return null;
        }

		while (true) {
            State current;

            if (open.size() == 0) {
				current = startState;
			} else {
                current = findSmallestF(open);
            }

			open.remove(current);
			closed.add(current);

			//If the current node is the goal node we need to stop.
			if (current.check(current, goalState)) {
				 return getPath(current, startState);
			}

			//Get all (at most four) neighbours of the current node. 
			neighbours = current.getNeighbours(current, startState, goalState);

			for (State neighbour : neighbours) {
				//If any neighbour is an obstacle or if it has already been visited, move to the next node. 
				if (contains(Data.obstacle, neighbour) || contains(closed, neighbour)) {
                    continue;
                }

				//If the node is not in the open list or if the g value is lesser than the current g value
				if (!contains(open, neighbour) || startState.getDistance(neighbour) < neighbour.getG()) {
					neighbour.setF();               //set the f value
					neighbour.setParent(current);   //set the current node as it's parent

					if (!contains(open, neighbour)) {
                        open.add(neighbour);        //add the node to the open list
                    }
				}
			}
		}
	}

    /**
     * Finds the node with the smallest f value ( f=g+h )
     *
     * @param nodes
     * @return
     */
    private static State findSmallestF(ArrayList<State> nodes) {

        State result = nodes.get(0);

        for (int i = 0; i < nodes.size() - 1; i++) {
            if (nodes.get(i).f < nodes.get(i + 1).f) {
                result = nodes.get(i);
            }
        }

        return result;
    }

    /**
     * After we reach the goal node, we get the path to that node by traveling back to the starting node by traversing to each parent.
     *
     * @param current
     * @param start
     * @return
     */
	private static ArrayList<Location> getPath(State current, State start) {

	    ArrayList<Location> path = new ArrayList<>();

		State node = current;

		path.add(current);

		while (!node.check(node, start)) {
			path.add(node.getParent());

			node = node.getParent();
		}
		Collections.reverse(path);

		return path;
	}

    /**
     * Checks if a node is in the array list only by considering its x-y coordinates and not f, g, h values
     *
     * @param list
     * @param s
     * @return
     */
	private static boolean contains(ArrayList<State> list, State s) {
		
		for (int i = 0; i < list.size(); i++) {
			State each = list.get(i);
			// System.out.println(each.getX() + "+" + each.getY());
			if (s.check(each, s)) {
				// System.out.println(s.check(each, s));
				return true;
			}
		}

		return false;
	}
}
