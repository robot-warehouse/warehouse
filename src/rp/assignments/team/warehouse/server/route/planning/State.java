package rp.assignments.team.warehouse.server.route.planning;

import rp.assignments.team.warehouse.server.Location;

import java.util.ArrayList;

public class State extends Location{

    public float f;

    private float g;
	private float heuristic;

	private State parent;

	public State(int x, int y) {
		super(x, y);
	}

	State(Location location) {
	    super(location.getX(), location.getY());
    }

	public float getG() {
		return this.g;
	}

	private float getH() {
		return this.heuristic;
	}

	public void setF() {
		this.f = this.getG() + this.getH();
	}

	public ArrayList<State> getNeighbours(State current, State start, State goal) {
		ArrayList<State> neighbours = new ArrayList<>();

		State neighbourEast = new State(current.getX() + 1, current.getY());
		if (neighbourEast.isValidLocation()) {
		    neighbourEast.addAsChild(current, start, goal);
            neighbours.add(neighbourEast);
		}

		State neighbourWest = new State(current.getX() - 1, current.getY());
		if (neighbourWest.isValidLocation()) {
			neighbourWest.addAsChild(current, start, goal);
			neighbours.add(neighbourWest);
		}

//		rp.assignments.team.warehouse.server.route.planning.State n3 = new rp.assignments.team.warehouse.server.route.planning.State(getX(current) - 1, getY(current) - 1);
//		if (getX(n3) >= xMin && getX(n3) <= xMax && getY(n3) >= yMin && getY(n3) <= yMax) {
//			n3.calculateG(current, start);
//			n3.calculateH(current, goal);
//			n3.setF();
//			n3.setParent(current);
//			neighbours.add(n3);
//		}

//		rp.assignments.team.warehouse.server.route.planning.State n4 = new rp.assignments.team.warehouse.server.route.planning.State(getX(current) - 1, getY(current) + 1);
//		if (getX(n4) >= xMin && getX(n4) <= xMax && getY(n4) >= yMin && getY(n4) <= yMax) {
//			n4.calculateG(current, start);
//			n4.calculateH(current, goal);
//			n4.setF();
//			n4.setParent(current);
//			neighbours.add(n4);
//		}

		State neighbourSouth = new State(current.getX(), current.getY() - 1);
		if (neighbourSouth.isValidLocation()) {
			neighbourSouth.addAsChild(current, start, goal);
			neighbours.add(neighbourSouth);
		}

		State neighbourNorth = new State(current.getX(), current.getY() + 1);
		if (neighbourNorth.isValidLocation()) {
            neighbourNorth.addAsChild(current, start, goal);
			neighbours.add(neighbourNorth);
		}

//		rp.assignments.team.warehouse.server.route.planning.State n7 = new rp.assignments.team.warehouse.server.route.planning.State(getX(current) + 1, getY(current) - 1);
//		if (getX(n7) >= xMin && getX(n7) <= xMax && getY(n7) >= yMin && getY(n7) <= yMax) {
//			n7.calculateG(current, start);
//			n7.calculateH(current, goal);
//			n7.setF();
//			n7.setParent(current);
//			neighbours.add(n7);
//		}
//
//		rp.assignments.team.warehouse.server.route.planning.State n8 = new rp.assignments.team.warehouse.server.route.planning.State(getX(current) + 1, getY(current) + 1);
//		if (getX(n8) >= xMin && getX(n8) <= xMax && getY(n8) >= yMin && getY(n8) <= yMax) {
//			n8.calculateG(current, start);
//			n8.calculateH(current, goal);
//			n8.setF();
//			n8.setParent(current);
//			neighbours.add(n8);
//		}

		return neighbours;
	}

	public void setParent(State parent) {
		this.parent = parent;
	}

	public boolean check(State s1, State s2) {
		return (s1.getX() == s2.getX() && s1.getY()==s2.getY());
	}

	public float getDistance(State next) {
		return (float) Math.sqrt((this.getX() - next.getX() ^ 2) + (this.getY() - next.getY() ^ 2));
	}

	public State getParent() {
		return parent;
	}

	@Override
	public String toString() {
		String result = "";
		result = result + this.getX() + "," + this.getY();
		return result;
	}

	private void calculateG(State current, State start) {
		this.g = (float) Math.sqrt((current.getX() - start.getX() ^ 2) + (current.getY() - start.getY() ^ 2));
	}

	private void calculateH(State current, State goal) {
		this.heuristic = (float) Math.sqrt((current.getX() - goal.getX() ^ 2) + (current.getY() - goal.getY() ^ 2));
	}

    private void addAsChild(State current, State start, State goal) {
        calculateG(current, start);
        calculateH(current, goal);
        setF();
        setParent(current);
    }
}
