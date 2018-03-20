package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.Robot;
import rp.assignments.team.warehouse.server.Warehouse;

public class RoutePlanning {
    
    private Warehouse warehouse;
    
    public RoutePlanning(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

	public List<Location> findPath(Location start, Location goal) {
		Set<Robot> robots = this.warehouse.getRobots();
		
		List<List<Location>> currentRoutes = new ArrayList<>();
		List<Location> staticRobotsToAvoid = new ArrayList<>();
		robots.forEach(robot -> {
		    if (!robot.getRoute().isEmpty()) {
		        currentRoutes.add(robot.getRoute());
		    } else {
		        staticRobotsToAvoid.add((State) robot.getCurrentLocation());
		    }
		});
		
		switch (currentRoutes.size()) {
		    case 0: return AStar.findPath(start, goal, staticRobotsToAvoid);
		    case 1: return TwoRobots.findPath(currentRoutes.get(0), start, goal, staticRobotsToAvoid);
		    case 2: return ThreeRobots.findPath(currentRoutes.get(0), currentRoutes.get(1), start, goal);
		}
		
		return null;
	}
	
	public static ArrayList<State> findPath(ArrayList<State> l1, State start, State goal){
		return TwoRobots.findPath(l1, start, goal);
	}
	
	public static  ArrayList<State> findPath(ArrayList<State> l1, ArrayList<State> l2, State start, State goal){
		return ThreeRobots.findPath(l1, l2, start, goal);
	}
	
}
