package RobotMotionControl;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import rp.systems.RobotProgrammingDemo;

public class  AStar implements MotionControl{
	public Main(State start, State goal) {
		super(start, goal);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Data.addObstacle();
		State start = new State(0, 0);
		State goal = new State(9, 7);
		// System.out.println("heyy");
		AStar demo = new AStar(start, goal);
		demo.findPath();
		// System.out.println(contains(Data.obstacle, goal));
		private static DifferentialPilot DP;

		Button.waitForAnyPress();
		DP = new DifferentialPilot(5.5f, 12.4f, Motor.A, Motor.B);
		RobotProgrammingDemo lf = new LineFollow(DP, SensorPort.S1, SensorPort.S2, SensorPort.S3);
		lf.run();
		AStar A=new AStar();
		A.run();
	}


}

