package rp.assignments.team.warehouse.server.route.planning;

<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> 0dcc01cb4788b17555f4df6aef1f9d71118a99c9
import java.util.List;

import rp.assignments.team.warehouse.server.Location;

public class Main {

<<<<<<< HEAD
	public static void main(String args[]) {
		System.out.println("here1");
		List<Location> l1 = new ArrayList<>();
		List<Location> l2 = new ArrayList<>();
		List<Location> l3 = new ArrayList<>();

		Location start1 = new State(2,6);
		Location goal1 = new State(0,5);
		Location start2 = new State(0,0);
		Location goal2 = new State(0,7);
		Location start3 = new State(0,5);
		Location goal3 = new State(0,1);
		
		l1 = Windowed.findPath(start1, goal1, Data.getObstacles());
		System.out.println("l1 ----->" + l1);
		l2 = Windowed.findPath(l1, start2, goal2, Data.getObstacles());
		System.out.println("l2 ----->" + l2);
		l3 = Windowed.findPath(l1, l2, start3, goal3, Data.getObstacles());		
		System.out.println("l3------>" + l3);
		
		
	}
=======
    public static void main(String args[]) {

        Location start1 = new State(2, 6);
        Location goal1 = new State(0, 5);
        Location start2 = new State(0, 0);
        Location goal2 = new State(0, 7);
        Location start3 = new State(0, 3);
        Location goal3 = new State(0, 1);

        List<Location> l1 = AStar.findPath(start1, goal1, Data.getObstacles());
        List<Location> l2 = TwoRobots.findPath(l1, start2, goal2, Data.getObstacles());
        List<Location> l3 = ThreeRobots.findPath(l1, l2, start3, goal3, Data.getObstacles());

        System.out.println("l1 ----->" + l1);
        System.out.println("l2 ----->" + l2);
        System.out.println("l3------>" + l3);
    }
>>>>>>> 0dcc01cb4788b17555f4df6aef1f9d71118a99c9
}
