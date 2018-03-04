import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;

import java.util.Queue;

public class GUserConjunct implements Behavior {

	String decision;
	private WheeledRobotSystem robot;
	private DifferentialPilot pilot;
	protected int average;

	private int firstRotation = 64;
	private int secondRotation = -64;

	private Queue<String> path;

	protected LightSensor lightSensor1;
	protected LightSensor lightSensor2;

	protected final double MAXSPEED;

	public GUserConjunct(WheeledRobotConfiguration robot, int average, Queue<String> q) {
		// TODO Auto-generated constructor stub
		this.robot = new WheeledRobotSystem(robot);
		pilot = this.robot.getPilot();
		MAXSPEED = pilot.getMaxTravelSpeed();
		pilot.setTravelSpeed(MAXSPEED * 0.5);
		lightSensor1 = new LightSensor(SensorPort.S1);
		lightSensor2 = new LightSensor(SensorPort.S4);
		this.average = average;
		this.path = q;
	}

	@Override
	public boolean takeControl() {

		if (lightSensor1.getNormalizedLightValue() <= average && lightSensor2.getNormalizedLightValue() <= average || (lightSensor1.getNormalizedLightValue() <= average&& lightSensor2.getNormalizedLightValue() >= average +29) || (lightSensor2.getNormalizedLightValue() <= average&& lightSensor1.getNormalizedLightValue() >= average +29) ) {
			return true;
		}

		return false;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		System.out.println("Conjuction User " + path.size());
		pilot.stop();
		if (!path.isEmpty()) {
			decision = (String) path.pop();
			pilot.travel(0.06);

			if (decision.equals("LEFT"))
				pilot.rotate(secondRotation);
			else if (decision.equals("RIGHT"))
				pilot.rotate(firstRotation);
			Delay.msDelay(50);
		} else {
			pilot.stop();
		}

	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
