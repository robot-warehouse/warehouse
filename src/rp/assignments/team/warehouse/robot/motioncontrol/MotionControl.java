package rp.assignments.team.warehouse.robot.motioncontrol;

import java.util.Queue;

import rp.config.RobotConfigs;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class MotionControl {

	public MotionControl(int average) {
		Queue<String> path = new Queue<String>();
		Behavior lineFollower = new GForwardLine(RobotConfigs.EXPRESS_BOT, average);
		Behavior rotateC = new GRotateMotorC(RobotConfigs.EXPRESS_BOT, average);
		Behavior rotateA = new GRotateMotorA(RobotConfigs.EXPRESS_BOT, average);
		Behavior conjunction = new GUserConjunct(RobotConfigs.EXPRESS_BOT, average, path);
		Behavior[] bArray = {conjunction, lineFollower,rotateC, rotateA};
		Arbitrator arby = new Arbitrator(bArray);
		arby.start();
	}
}
