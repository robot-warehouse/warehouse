package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;

public class State {
	private int x;
	private int y;
	private float g;
	private float h;
	float f;
	final static int xMin = 0;
	final static int xMax = 11;
	final static int yMin = 0;
	final static int yMax = 7;
	private State parent;

	public State(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public float getG() {
		return this.g;
	}

	public float getH() {
		return this.h;
	}

	public float getF() {
		return this.f;
	}

	public void setG(float g) {
		this.g = g;
	}

	public void setH(float h) {
		this.h = h;
	}

	public void setF() {
		this.f = this.getG() + this.getH();
	}

	public void setF(float f) {
		this.f = f;
	}

	public ArrayList<State> getNeighbour(State current, State start, State goal) {
		ArrayList<State> neighbours = new ArrayList<>();

		State n1 = new State(current.getX() + 1, current.getY());
		if (n1.getX() >= xMin && n1.getX() <= xMax && n1.getY() >= yMin && n1.getY() <= yMax) {
			n1.calculateG(current, start);
			n1.calculateH(current, goal);
			n1.setF();
			n1.setParent(current);
			neighbours.add(n1);
		}

		State n2 = new State(current.getX() - 1, current.getY());
		if (n2.getX() >= xMin && n2.getX() <= xMax && n2.getY() >= yMin && n2.getY() <= yMax) {
			n2.calculateG(current, start);
			n2.calculateH(current, goal);
			n2.setF();
			n2.setParent(current);
			neighbours.add(n2);
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

		State n5 = new State(current.getX(), current.getY() - 1);
		if (n5.getX() >= xMin && n5.getX() <= xMax && n5.getY() >= yMin && n5.getY() <= yMax) {
			n5.calculateG(current, start);
			n5.calculateH(current, goal);
			n5.setF();
			n5.setParent(current);
			neighbours.add(n5);
		}

		State n6 = new State(current.getX(), current.getY() + 1);
		if (n6.getX() >= xMin && n6.getX() <= xMax && n6.getY() >= yMin && n6.getY() <= yMax) {
			n6.calculateG(current, start);
			n6.calculateH(current, goal);
			n6.setF();
			n6.setParent(current);
			neighbours.add(n6);
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

	public void calculateG(State current, State start) {
		this.g = (float) Math.sqrt((current.getX() - start.getX() ^ 2) + (current.getY() - start.getY() ^ 2));

	}

	public void calculateH(State current, State goal) {
		this.h = (float) Math.sqrt((current.getX() - goal.getX() ^ 2) + (current.getY() - goal.getY() ^ 2));
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
	
	public String toString() {
		String result = "";
		result = result + this.getX() + "," + this.getY();
		return result;
	}
}
