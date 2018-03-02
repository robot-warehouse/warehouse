package RobotMotionControl;

import RobotMotionControl.LineFollow;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.robotics.navigation.DifferentialPilot;
import rp.systems.RobotProgrammingDemo;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class MotionControl extends RobotProgrammingDemo implements SensorPortListener {

	private static DifferentialPilot DP;
		
	public MotionControl() {
		
	}

	@Override
	public void run() {
		
	}

	@Override
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
		
	}
	
	public static void main (String[] args) throws InterruptedException {
		Button.waitForAnyPress();
		DP = new DifferentialPilot(5.5f, 12.4f, Motor.A, Motor.B);
		DP.setTravelSpeed(10);
		Behavior lineFollow = new LineFollow(DP, SensorPort.S1, SensorPort.S2, SensorPort.S3);
		Behavior [] behaviorArray = {lineFollow};
		Arbitrator arb = new Arbitrator(behaviorArray);
		arb.start();
	}
}