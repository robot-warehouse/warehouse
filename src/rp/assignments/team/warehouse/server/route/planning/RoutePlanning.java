package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.Robot;
import rp.assignments.team.warehouse.server.Warehouse;

public class RoutePlanning {

    public static final int NUMBER_OF_STEPS = 4;

    private Warehouse warehouse;

    public RoutePlanning(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public synchronized List<Location> findPath(String robotName, Location start, Location goal) {
        List<Location> obstacles = Data.getObstacles();
        Set<Robot> robots = this.warehouse.getRobots();
        
        robots.removeIf(robot -> robot.getName().equals(robotName));

        List<List<Location>> currentRoutes = new ArrayList<>();
        robots.forEach(robot -> {
            if (!robot.getCurrentRoute().isEmpty()) {
                currentRoutes.add(robot.getCurrentRoute());
            } else {
                obstacles.add(robot.getCurrentLocation());
            }
        });

        switch (currentRoutes.size()) {
            case 0:
                return Windowed.findPath(start, goal, obstacles);
            case 1:
                return Windowed.findPath(currentRoutes.get(0), start, goal,
                                          obstacles);
            case 2:
                return Windowed.findPath(currentRoutes.get(0), currentRoutes.get(1), start, goal, obstacles);
        }

        return null;
    }

}
