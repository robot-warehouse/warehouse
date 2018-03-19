package rp.assignments.team.warehouse.server.route.planning;

import java.util.ArrayList;

public class Main {

	public static void main(String args[]) {
		ArrayList<State> l1 = new ArrayList<State>();
		ArrayList<State> l2 = new ArrayList<State>();
		ArrayList<State> l3 = new ArrayList<State>();

		State start1 = new State(2,6);
		State goal1 = new State(0,5);
		State start2 = new State(0,0);
		State goal2 = new State(0,7);
		State start3 = new State(0,3);
		State goal3 = new State(0,1);
		
		l1 = AStar.findPath(start1, goal1, Data.obstacles);
		l2 = TwoRobots.findPath(l1, start2, goal2);
		l3 = ThreeRobots.findPath(l1, l2, start3, goal3);
		
		System.out.println("l1 ----->" + l1);
		System.out.println("l2 ----->" + l2);
		System.out.println("l3------>" + l3);
		
		
	}
}
