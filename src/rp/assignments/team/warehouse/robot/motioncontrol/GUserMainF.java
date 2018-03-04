package rp.assignments.team.warehouse.robot.motioncontrol;

import java.util.Queue;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import rp.config.RobotConfigs;

public class GUserMainF {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Queue<String> path = new Queue<String>();
		System.out.println("Select path");

		Button.ESCAPE.addButtonListener(new ButtonListener() {

			@Override
			public void buttonReleased(Button _b) {

			}

			@Override
			public void buttonPressed(Button _b) {

				LightSensor lightSensor1 = new LightSensor(SensorPort.S1);
				LightSensor lightSensor2 = new LightSensor(SensorPort.S4);
				LightSensor lightSensor3 = new LightSensor(SensorPort.S2);
				int white, black, white2, black2, white3, black3 = 0;
				Button.waitForAnyPress();
				System.out.println("Light colour");
				Button.waitForAnyPress();
				white = lightSensor1.getNormalizedLightValue();
				white2 = lightSensor2.getNormalizedLightValue();
				white3 = lightSensor2.getNormalizedLightValue();
				int averageWhite = (white + white2 + white3) / 3;
				System.out.println("White colour: " + averageWhite);
				System.out.println("Dark colour");
				Button.waitForAnyPress();
				lightSensor1.calibrateLow();
				lightSensor2.calibrateLow();
				black = lightSensor1.getNormalizedLightValue();
				black2 = lightSensor2.getNormalizedLightValue();
				black3 = lightSensor2.getNormalizedLightValue();
				int averageBlack = (black + black2+ black3) / 3;
				System.out.println("Black colour: " + averageBlack);

				int average = (averageBlack + averageWhite) / 2;
				Button.waitForAnyPress();

//				Behavior lineFollower = new GForwardLine(RobotConfigs.EXPRESS_BOT, average);
//				Behavior rotateC = new GRotateMotorC(RobotConfigs.EXPRESS_BOT, average);
//				Behavior rotateA = new GRotateMotorA(RobotConfigs.EXPRESS_BOT, average);
//				Behavior conjunction = new GUserConjunct(RobotConfigs.EXPRESS_BOT, average, path);
//				Behavior[] bArray = {conjunction, lineFollower,rotateC, rotateA};
//				Arbitrator arby = new Arbitrator(bArray);
//				arby.start();  add this to new Motion control class

			}
		});

		Button.LEFT.addButtonListener(new ButtonListener() {

			@Override
			public void buttonReleased(Button _b) {

			}

			@Override
			public void buttonPressed(Button _b) {
				path.addElement("LEFT");
				System.out.println("Added Left");

			}
		});

		Button.RIGHT.addButtonListener(new ButtonListener() {

			@Override
			public void buttonReleased(Button _b) {

			}

			@Override
			public void buttonPressed(Button _b) {
				path.addElement("RIGHT");
				System.out.println("Added Right");

			}
		});

		while (true) {
			Delay.msDelay(100);
		}
	}

}