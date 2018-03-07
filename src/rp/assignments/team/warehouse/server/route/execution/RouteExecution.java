package rp.assignments.team.warehouse.server.route.execution;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rp.assignments.team.warehouse.server.Location;

import java.util.ArrayList;

public class RouteExecution {

    // TODO Change directions into Enum
    // 0 for left, 1 for right, 2 for forward, 3 for back Works only if we assume
    // that robot starts from certain position. Will explain then meet

    // EasttoWest 4
    // WesttoEast 5
    // NorthtoSouth 6
    // SouthtoNorth 7

    private final static Logger logger = LogManager.getLogger(RouteExecution.class);

    public static int comparisonY = 1;
    public static int comparisonX = 1;
    public static ArrayList<Location> pathForReading;
    public static ArrayList<Integer> xyCoordinates = new ArrayList<Integer>();

    // James should read this because this ArrayList will return coordinates
    public ArrayList<Integer> movementCommands = new ArrayList<Integer>();

    public int direction = 5;

    public RouteExecution() {
        reading();
    }

    public ArrayList<Integer> getMovements() {
        return movementCommands;
    }

//	public static void main(String[] args) {
//
//		Location start = new Location(1, 3);
//		Location goal = new Location(11, 4);
//		pathForReading = AStar.findPath(start, goal);
//		if(pathForReading == null) {
//			logger.fatal("No path, no conversion is available");
//		}
//		assert (pathForReading != null);
//		RouteExecution rExecution = new RouteExecution();
//
//		for (Location x : pathForReading) {
//			System.out.println(x.toString());
//		}
//		for (int i : movementCommands) {
//			System.out.println(i);
//		}
//	}

    public void reading() {

        logger.info(" Start Convert coordinates into numerated actions");

        if (pathForReading != null) {

            for (int i = 0; i < pathForReading.size() - 1; i++) {
                Location now = pathForReading.get(i);
                Location next = pathForReading.get(i + 1);
                if (direction == 5) {
                    if (now.getX() == next.getX()) {
                        if (now.getY() == next.getY() - 1) {
                            // left
                            movementCommands.add(0);
                            movementCommands.add(2);
                            direction = 7;
                        } else if (now.getY() == next.getY() + 1) {
                            // right
                            movementCommands.add(1);
                            movementCommands.add(2);
                            direction = 6;
                        }
                    } else if (now.getY() == next.getY()) {
                        if (now.getX() == next.getX() - 1) {
                            // forward
                            movementCommands.add(2);

                        } else if (now.getX() == next.getX() + 1) {
                            // back
                            movementCommands.add(3);
                            direction = 4;

                        }
                    }

                } else if (direction == 6) { // NorthToSouth
                    if (now.getX() == next.getX()) {
                        if (now.getY() == next.getY() - 1) {
                            // backward
                            movementCommands.add(3);

                            direction = 7;
                        } else if (now.getY() == next.getY() + 1) {
                            // forward
                            movementCommands.add(2);
                            direction = 6;
                        }
                    } else if (now.getY() == next.getY()) {
                        if (now.getX() == next.getX() - 1) {
                            // left
                            movementCommands.add(0);
                            movementCommands.add(2);
                            direction = 5;

                        } else if (now.getX() == next.getX() + 1) {

                            // right
                            movementCommands.add(1);
                            movementCommands.add(2);
                            direction = 4;

                        }
                    }

                } else if (direction == 4) { // EasttoWest
                    if (now.getX() == next.getX()) {
                        if (now.getY() == next.getY() - 1) {
                            // right
                            movementCommands.add(1);
                            movementCommands.add(2);
                            direction = 7;
                        } else if (now.getY() == next.getY() + 1) {
                            // left
                            movementCommands.add(0);
                            movementCommands.add(2);
                            direction = 6;
                        }
                    } else if (now.getY() == next.getY()) {
                        if (now.getX() == next.getX() - 1) {
                            // back
                            movementCommands.add(3);
                            direction = 5;

                        } else if (now.getX() == next.getX() + 1) {
                            // forward
                            movementCommands.add(2);

                        }
                    }

                } else if (direction == 7) { // SouthToNorth
                    if (now.getX() == next.getX()) {
                        if (now.getY() == next.getY() - 1) {
                            // forward
                            movementCommands.add(2);
                        } else if (now.getY() == next.getY() + 1) {
                            // back
                            movementCommands.add(3);

                            direction = 6;
                        }
                    } else if (now.getY() == next.getY()) {
                        if (now.getX() == next.getX() - 1) {
                            // right
                            movementCommands.add(1);
                            movementCommands.add(2);
                            direction = 5;

                        } else if (now.getX() == next.getX() + 1) {
                            // left
                            movementCommands.add(0);
                            movementCommands.add(2);
                            direction = 4;

                        }
                    }
                }
            }
            logger.info("Finished converting");
        } else {
            logger.info("no path is available");
        }


    }
}
