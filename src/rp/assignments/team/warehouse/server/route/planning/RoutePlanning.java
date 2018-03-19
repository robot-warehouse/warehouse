package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;

import rp.assignments.team.warehouse.server.Location;

public class RoutePlanning {

	public static ArrayList<State> findPath(Location start, Location goal) {
		return AStar.findPath(start, goal);
	}
	
	public static ArrayList<State> findPath(ArrayList<State> l1, State start, State goal){
		return TwoRobots.findPath(l1, start, goal);
	}
	
	public static  ArrayList<State> findPath(ArrayList<State> l1, ArrayList<State> l2, State start, State goal){
		return ThreeRobots.findPath(l1, l2, start, goal);
	}
	
}
