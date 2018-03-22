package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;
import java.util.List;

import rp.assignments.team.warehouse.server.Location;

public class Main {



	public static void main(String args[]) {

		List<Location> l1 = new ArrayList<>();
		List<Location> l2 = new ArrayList<>();
		List<Location> l3 = new ArrayList<>();


		Location start1 = new State(0,0);
		Location goal1 = new State(4,5);
		Location start2 = new State(0,0);
		Location goal2 = new State(0,2);
		Location start3 = new State(6,3);
		Location goal3 = new State(11,3);
		
//		System.out.println(Windowed.findPath(start1, goal1, Data.getObstacles()));
		

		l1 = Windowed.findPath(start1, goal1, Data.getObstacles());
		System.out.println("l1 ----->" + l1);
		l2 = Windowed.findPath(l1, start2, goal2, Data.getObstacles());
		System.out.println("l2 ----->" + l2);
		l3 = Windowed.findPath(l1, l2, start3, goal3, Data.getObstacles());
		System.out.println("l3------>" + l3);

	}


}
