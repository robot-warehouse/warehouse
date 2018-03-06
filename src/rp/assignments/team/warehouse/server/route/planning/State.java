package rp.assignments.team.warehouse.server.route.planning;

import rp.assignments.team.warehouse.server.Location;

import java.util.ArrayList;

public class State extends Location{

    private float totalWeight;
    private float distanceFromStart;
	private float heuristic;

	private State parent;

    /**
     *
     * @param x
     * @param y
     */
	public State(int x, int y) {
		super(x, y);
	}

    /**
     *
     * @param location
     */
	State(Location location) {
	    super(location.getX(), location.getY());
    }

    /**
     *
     * @return
     */
	public float getDistanceFromStart() {
		return this.distanceFromStart;
	}

    /**
     *
     * @return
     */
	public float getTotalWeight() {
	    return this.totalWeight;
    }

    /**
     *
     */
	public void setTotalWeight() {
		this.totalWeight = this.getDistanceFromStart() + this.getHeuristic();
	}

    /**
     *
     * @return
     */
    public State getParent() {
        return parent;
    }

    /**
     *
     * @param parent
     */
    public void setParent(State parent) {
        this.parent = parent;
    }

    /**
     *
     *
     * @param start
     * @param goal
     * @return
     */
	public ArrayList<State> getNeighbours(State start, State goal) {
		ArrayList<State> neighbours = new ArrayList<>();

		
		if (isValidLocation(getX() + 1, getY())) {
			State neighbourEast = new State(getX() + 1, getY());
		    neighbourEast.calculateTotalWeight(start, goal);
            neighbours.add(neighbourEast);
		}

		
		if (isValidLocation(getX() - 1, getY())) {
			State neighbourWest = new State(getX() - 1, getY());
			neighbourWest.calculateTotalWeight(start, goal);
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

		
		if (isValidLocation(getX(), getY() - 1)) {
			State neighbourSouth = new State(getX(), getY() - 1);
			neighbourSouth.calculateTotalWeight(start, goal);
			neighbours.add(neighbourSouth);
		}

		
		if (isValidLocation(getX(), getY() + 1)) {
			State neighbourNorth = new State(getX(), getY() + 1);
            neighbourNorth.calculateTotalWeight(start, goal);
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

    /**
     *
     *
     * @param next
     * @return
     */
	public float getDistance(State next) {
		return (float) Math.sqrt((getX() - next.getX() ^ 2) + (getY() - next.getY() ^ 2));
	}

    /**
     *
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof State) {
            State state = (State) object;
            return (getX() == state.getX() && getY()== state.getY());
        }

        return false;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public String toString() {
        String result = "";
        result = result + getX() + "," + getY();
        return result;
    }

    /**
     *
     *
     * @return
     */
    boolean isValidLocation() {
        return Location.isValidLocation(getX(), getY());
    }

    /**
     *
     *
     * @return
     */
    private float getHeuristic() {
        return this.heuristic;
    }

    /**
     *
     *
     * @param start
     */
    private void calculateG(State start) {
        this.distanceFromStart = (float) Math.sqrt((getX() - start.getX() ^ 2) + (getY() - start.getY() ^ 2));
    }

    /**
     *
     *
     * @param goal
     */
    private void calculateH(State goal) {
        this.heuristic = (float) Math.sqrt((getX() - goal.getX() ^ 2) + (getY() - goal.getY() ^ 2));
    }

    /**
     *
     *
     * @param start
     * @param goal
     */
    private void calculateTotalWeight(State start, State goal) {
        calculateG(start);
        calculateH(goal);
        setTotalWeight();
    }
}
