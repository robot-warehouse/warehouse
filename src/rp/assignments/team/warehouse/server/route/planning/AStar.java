package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;
import java.util.Collections;

public class AStar {

	private State start;
	private State goal;
	private State current;
	public static ArrayList<State> result = new ArrayList<>(); //stores the path

	public AStar(State start, State goal) {
		this.start = start;
		this.goal = goal;
	}
	//Finds the node with the smallest f value ( f=g+h )
	public State findSmallestF(ArrayList<State> nodes) {
		State result = nodes.get(0);
		for (int i = 0; i < nodes.size() - 1; i++) {
			if (nodes.get(i).f < nodes.get(i + 1).f) {
				result = nodes.get(i);
			}
		}
		return result;
	}

	public void findPath() {
		//Stores nodes available for visiting.
				ArrayList<State> open = new ArrayList<>();
				//Stores already visited nodes
				ArrayList<State> closed = new ArrayList<>();
				//Stores the neighbouring nodes of the current node.
				ArrayList<State> neighbours;
				
				//Ensures that both the start node and goal node are in the grid.
				boolean check = validate(start, goal);
				//Return if either the start node or the goal node are invalid.
				if (!check) {
					return;
				}

		while (true) {
			if (open.size() == 0) {
				current = start;
			} else
				current = findSmallestF(open);

			open.remove(current);
			closed.add(current);
			//If the current node is the goal node we need to stop.
			if (current.check(current, goal)) {
			
				result = getPath(current, start);
				for (State each : result)
					System.out.println(each.toString());
				return;
			}
			//Get all (at most four) neighbours of the current node. 
			neighbours = current.getNeighbour(current, start, goal);

			for (State node1 : neighbours) {
				//If any neighbour is an obstacle or if it has already been visited, move to the next node. 
				if (contains(Data.obstacle, node1) || contains(closed, node1))
					continue;
				//If the node is not in the open list or if the g value is lesser than the current g value
				if (!contains(open, node1) || start.getDistance(node1) < node1.getG()) {
					node1.setF();//set the f value
					node1.setParent(current);//set the current node as it's parent
					if (!contains(open, node1))
						open.add(node1);//add the node to the open list
				}

			}

		}

	}
	//after we reach the goal node, we get the path to that node by traveling back to the starting node by traversing to each parent.
	public ArrayList<State> getPath(State current, State start) {
		State node = current;
		result.add(current);
		while (!node.check(node, start)) {
			result.add(node.getParent());
			// System.out.println(node.getParent().toString());
			node = node.getParent();
		}
		Collections.reverse(result);
		return result;
	}
	//checks if a node is in the arraylist only by considering its x-y coordinates and not f, g, h values
	public static boolean contains(ArrayList<State> list, State s) {
		
		for (int i = 0; i < list.size(); i++) {
			State each = list.get(i);
			if (s.check(each, s)) {
				return true;
			}
		}
		return false;
	}

	public boolean validate(State start, State goal) {
		return start.getX() >= State.xMin && start.getX() <= State.xMax && goal.getY() >= State.yMin
				&& goal.getY() <= State.yMax && !contains(Data.obstacle, start) && !contains(Data.obstacle, goal);
	}

	public static void main(String[] args) {
		Data.addObstacle();
		State start = new State(0, 0);
		State goal = new State(9, 7);
		//check if the start and goal nodes are within the grid and are not an obstacle.
		AStar demo = new AStar(start, goal);
		demo.findPath();
		
	}

}
