package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;
import java.util.List;

import rp.assignments.team.warehouse.server.Location;

public class State extends Location {

    /**
     * The total weight of the node.
     *
     * @see #calculateTotalWeight(State, State)
     */
    private float totalWeight;

    /**
     * The distance from the start node.
     *
     * @see #calculateG(State)
     */
    private float distanceFromStart;

    /**
     * The h value for this node
     *
     * @see #calculateH(State)
     */
    private float heuristic;

    /**
     * The parent of this node.
     *
     * @see #setParent(State)
     */
    private State parent;

    /**
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public State(int x, int y) {
        super(x, y);
    }

    /**
     * @param location The location for this node.
     */
    State(Location location) {
        super(location.getX(), location.getY());
    }

    /**
     * Get the distance this node is from the start node.
     *
     * @return The distance from the start node.
     * @see #calculateG(State)
     */
    public float getDistanceFromStart() {
        return this.distanceFromStart;
    }

    /**
     * Get the total weight of this node.
     *
     * @return The total weight of this node
     * @see #setTotalWeight()
     */
    public float getTotalWeight() {
        return this.totalWeight;
    }

    /**
     * Set the total weight of this node.
     *
     * @see #getDistanceFromStart()
     * @see #getHeuristic()
     */
    public void setTotalWeight() {
        this.totalWeight = this.getDistanceFromStart() + this.getHeuristic();
    }

    /**
     * Get the parent of this node.
     *
     * @return The parent of this node.
     * @see #setParent(State)
     */
    public State getParent() {
        return parent;
    }

    /**
     * Set the parent of this node.
     *
     * @param parent The parent of this node.
     */
    public void setParent(State parent) {
        this.parent = parent;
    }

    /**
     * Get a list of this node's neighbours.
     *
     * @param start The start node.
     * @param goal The goal node.
     * @return List of this nodes neighbours.
     */
    public List<State> getNeighbours(Location start, Location goal) {
        List<Location> neighbours = this.getNeighbours();
        List<State> neighbourStates = new ArrayList<>();

        for (Location neighbour : neighbours) {
            State neighbourState = new State(neighbour);
            neighbourState.calculateTotalWeight(start, goal);
            neighbourStates.add(neighbourState);
        }

        return neighbourStates;

    }

    /**
     * Get the distance from this node to another.
     *
     * @param next The node to calculate the distance to.
     * @return The distance from this node to the next node.
     */
    public float getDistance(State next) {
        return (float) Math.sqrt((getX() - next.getX() ^ 2) + (getY() - next.getY() ^ 2));
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof State) {
            State state = (State) object;
            return (getX() == state.getX() && getY() == state.getY());
        }

        return false;
    }

    /**
     * Checks if this node is in a valid location in the warehouse.
     *
     * @return True if this represents a valid location in the warehouse.
     */
    boolean isValidLocation() {
        return Location.isValidLocation(getX(), getY());
    }

    /**
     * Get the h value for this node.
     *
     * @return The h value for this node.
     */
    private float getHeuristic() {
        return this.heuristic;
    }

    /**
     * Calculate the g value for this node.
     *
     * @param start The start node.
     */
    private void calculateG(Location start) {
        this.distanceFromStart = (float) Math.sqrt((getX() - start.getX() ^ 2) + (getY() - start.getY() ^ 2));
    }

    /**
     * Calculate the h value for this node.
     *
     * @param goal The goal node.
     */
    private void calculateH(Location goal) {
        this.heuristic = (float) Math.sqrt((getX() - goal.getX() ^ 2) + (getY() - goal.getY() ^ 2));
    }

    /**
     * Calculate the total weight of this node.
     *
     * @param start The start node.
     * @param goal The goal node.
     * @see #calculateG(State)
     * @see #calculateH(State)
     * @see #setTotalWeight()
     */
    private void calculateTotalWeight(Location start, Location goal) {
        calculateG(start);
        calculateH(goal);
        setTotalWeight();
    }
}
