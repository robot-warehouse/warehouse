package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.server.Location;

public class AStar {

    private final static Logger logger = LogManager.getLogger(AStar.class);

    public static List<Location> findPath(Location start, Location goal) {
        return findPath(start, goal, Data.getObstacles());
    }

    /**
     * Get the path between the start and goal locations.
     * 
     * @param start The start location.
     * @param goal The finish location.
     * @return List of the locations along the computed path.
     */
    public static List<Location> findPath(Location start, Location goal, List<Location> obstacles) {
        logger.info("Entering the findPath method");
        List<State> open = new ArrayList<>(); // Stores nodes available for visiting.
        List<State> closed = new ArrayList<>(); // Stores already visited nodes

        State startState = new State(start);
        State goalState = new State(goal);
        
        // Throw exception if either the start node or the goal node are invalid.
        if (!startState.isValidLocation() || !goalState.isValidLocation()) {
            logger.fatal("Invalid start/goal node passed to findPath");
            throw new IllegalArgumentException("Invalid start/goal node.");
        }
        
        if (obstacles.contains(startState) || obstacles.contains(goalState)) {
            logger.error("Obstacle located in start/goal node passed to findPath");
            return null;
        }

        while (true) {
            State current;

            if (open.size() == 0 && !closed.contains(startState)) {
                current = startState;
            }else if(open.size() == 0 && closed.contains(startState)) {
            	return null;
            }
            else {
                current = findSmallestF(open);
            }

            open.remove(current);
            closed.add(current);

            // If the current node is the goal node we need to stop.
            if (current.equals(goalState)) {
                logger.info("Route found, returning from the method.");
                return getPath(current, startState);
            }

            // Get all (at most four) neighbours of the current node.
            List<State> neighbours = current.getNeighbours(startState, goalState);

            for (State neighbour : neighbours) {
                // If any neighbour is an obstacle or if it has already been visited, move to
                // the next node.
                if (obstacles.contains(neighbour) || closed.contains(neighbour)) {
                    continue;
                }

                // If the node is not in the open list or if the g value is lesser than the
                // current g value
                if (!open.contains(neighbour) || startState.getDistance(neighbour) < neighbour.getDistanceFromStart()) {
                    neighbour.setTotalWeight(); // set the f value
                    neighbour.setParent(current); // set the current node as it's parent

                    if (!open.contains(neighbour)) {
                        open.add(neighbour); // add the node to the open list
                    }
                }
            }
        }
    }

    /**
     * Get the distance of the generated A* path between start and goal.
     *
     * @param start The start location.
     * @param goal The finish location.
     * @return The distance of the path between the two locations.
     */
    public static int findDistance(Location start, Location goal) {
        return findPath(start, goal).size();
    }

    /**
     * Finds the node with the smallest f value ( f=g+h )
     *
     * @param nodes The nodes to search through.
     * @return The node with the minimum f value.
     */
    private static State findSmallestF(List<State> nodes) {
        State result = nodes.get(0);
        for (int i = 0; i < nodes.size() - 1; i++) {
            if (nodes.get(i).getTotalWeight() < nodes.get(i + 1).getTotalWeight()) {
                result = nodes.get(i);
            }
        }
        return result;
    }

    /**
     * After we reach the goal node, we get the path to that node by travelling back to the starting node by traversing
     * to each parent.
     *
     * @param current The current node.
     * @param start The starting node.
     * @return The path to the goal node
     */
    private static List<Location> getPath(State current, State start) {
        List<Location> path = new ArrayList<>();
        State node = current;
        path.add(current);
        while (!node.equals(start)) {
            path.add(node.getParent());

            node = node.getParent();
        }
        Collections.reverse(path);
        logger.info("Route found and reversed successfully");
        return path;
    }
}
