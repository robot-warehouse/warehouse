package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;

public class AStar {

	private State start;
	private State goal;
	private State current;
	private ArrayList<State> result = new ArrayList<>();

	public AStar(State start, State goal) {
		this.start = start;
		this.goal = goal;
	}

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
		ArrayList<State> open = new ArrayList<>();
		ArrayList<State> closed = new ArrayList<>();
		ArrayList<State> neighbours;
		ArrayList<State> path;
		boolean check = validate(start, goal);
		// System.out.println(check);
		if (!check) {
			//System.out.println("I work");
			return;
		}

		while (true) {
			if (open.size() == 0) {
				current = start;
			} else
				current = findSmallestF(open);

			open.remove(current);
			closed.add(current);

			if (current.check(current, goal)) {
				System.out.println(current.toString());
				// System.out.println(current.getParent().toString());
				path = getPath(current, start);
				for (State each : result)
					System.out.println(each.toString());
				return;
			}

			neighbours = current.getNeighbour(current, start, goal);

			for (State node1 : neighbours) {
				if (contains(Data.obstacle, node1) || contains(closed, node1))
					continue;
				if (!contains(open, node1) || start.getDistance(node1) < node1.getG()) {
					node1.setF();
					node1.setParent(current);
					if (!contains(open, node1))
						open.add(node1);
				}

			}

		}

	}

	public ArrayList<State> getPath(State current, State start) {
		State node = current;
		result.add(current);
		while (!node.check(node, start)) {
			result.add(node.getParent());
			// System.out.println(node.getParent().toString());
			node = node.getParent();
		}
		return result;
	}

	public static boolean contains(ArrayList<State> list, State s) {
		// System.out.println(list.size());
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

	public boolean validate(State start, State goal) {
		return start.getX() >= State.xMin && start.getX() <= State.xMax && goal.getY() >= State.yMin
				&& goal.getY() <= State.yMax && !contains(Data.obstacle, start) && !contains(Data.obstacle, goal);
	}

	public static void main(String[] args) {
		Data.addObstacle();
		State start = new State(0, 0);
		State goal = new State(9, 7);
		// System.out.println("heyy");
		AStar demo = new AStar(start, goal);
		demo.findPath();
		// System.out.println(contains(rp.assignments.team.warehouse.server.route.planning.Data.obstacle, goal));

	}

}
