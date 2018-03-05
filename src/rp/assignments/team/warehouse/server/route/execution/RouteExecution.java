package rp.assignments.team.warehouse.server.route.execution;

import java.time.Year;
import java.util.ArrayList;

import rp.assignments.team.warehouse.server.route.planning.AStar;
import rp.assignments.team.warehouse.server.route.planning.Data;
import rp.assignments.team.warehouse.server.route.planning.State;

public class RouteExecution {

	// 0 for left, 1 for right, 2 for forward, 3 for back Works only if we assume
	// that robot starts from certain position. Will explain then meet

	// EasttoWest 4
	// WesttoEast 5
	// NorthtoSouth 6
	// SouthtoNorth 7

	public int direction = 7;

	public static int comparisonY = 1;
	public static int comparisonX = 1;
	public static ArrayList<State> pathForReading;

	public static ArrayList<Integer> xyCoordinates = new ArrayList<Integer>();

	// public static ArrayList<Integer> yCoordinates = new ArrayList<Integer>();

	public static ArrayList<Integer> movementCommands = new ArrayList<Integer>(); // James should read this
	public RouteExecution() {
		// TODO Auto-generated constructor stub
		pathForReading = AStar.result;
		reading();
	}

	public void reading() {
		
		
		for (int i = 0; i < pathForReading.size() - 1; i++) {
			State now = pathForReading.get(i);
			State next = pathForReading.get(i + 1);
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

			}
			
			
			else if (direction == 6) { //NorthToSouth
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
						direction =5;
						
					} else if (now.getX() == next.getX() + 1) {
						
						// right
						movementCommands.add(1);
						movementCommands.add(2);
						direction = 4;
						
					}
				}

			}
			
			else if (direction == 4) { //EasttoWest
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

			}
			
			else if (direction == 7) { //SouthToNorth
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
	}

	public static void main(String[] args) {

		Data.addObstacle();
		State start = new State(2, 2);
		State goal = new State(3,2);
		AStar demo = new AStar(start, goal);
		demo.findPath();
		RouteExecution routeExecution = new RouteExecution();

		for (State x : pathForReading)
			System.out.println(x.toString());
		for (int i : movementCommands) {
			System.out.println(i);
		}
	}

}
