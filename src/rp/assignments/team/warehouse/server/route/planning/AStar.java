package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;

import rp.assignments.team.warehouse.server.Location;

public class AStar {

    final static Logger logger = Logger.getLogger(AStar.class);

    public static ArrayList<Location> findPath(Location start, Location goal) {
        logger.info("Entering the findPath method");
        ArrayList<State> open = new ArrayList<>(); // Stores nodes available for visiting.
        ArrayList<State> closed = new ArrayList<>(); // Stores already visited nodes

        State startState = new State(start);
        State goalState = new State(goal);

        // Return if either the start node or the goal node are invalid.
        if (Data.obstacles.contains(startState) || Data.obstacles.contains(goalState) || !startState.isValidLocation()
                || !goalState.isValidLocation()) {
            logger.fatal("Terminating because of an invalid node");
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

            // If the current node is the goal node we need to stop.
            if (current.equals(goalState)) {
                logger.info("Route found, returning from the method.");
                return getPath(current, startState);
            }

            // Get all (at most four) neighbours of the current node.
            ArrayList<State> neighbours = current.getNeighbours(startState, goalState);

            for (State neighbour : neighbours) {
                // If any neighbour is an obstacle or if it has already been visited, move to
                // the next node.
                if (Data.obstacles.contains(neighbour) || closed.contains(neighbour)) {
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
     * Finds the node with the smallest f value ( f=g+h )
     *
     * @param nodes
     * @return
     */
    private static State findSmallestF(ArrayList<State> nodes) {
        State result = nodes.get(0);
        for (int i = 0; i < nodes.size() - 1; i++) {
            if (nodes.get(i).getTotalWeight() < nodes.get(i + 1).getTotalWeight()) {
                result = nodes.get(i);
            }
        }
        return result;
    }

    /**
     * After we reach the goal node, we get the path to that node by traveling back
     * to the starting node by traversing to each parent.
     *
     * @param current
     * @param start
     * @return
     */
    private static ArrayList<Location> getPath(State current, State start) {
        ArrayList<Location> path = new ArrayList<>();
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
