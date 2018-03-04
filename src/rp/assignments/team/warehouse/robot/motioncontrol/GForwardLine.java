import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;

public class GForwardLine implements Behavior {

	private WheeledRobotSystem robot;
	private DifferentialPilot pilot;
	protected int average;

	protected LightSensor lightSensor1;
	protected LightSensor lightSensor2;

	protected final double MAXSPEED;

	public GForwardLine(WheeledRobotConfiguration robot, int average) {
		// TODO Auto-generated constructor stub
		this.robot = new WheeledRobotSystem(robot);
		pilot = this.robot.getPilot();
		MAXSPEED = pilot.getMaxTravelSpeed();
		pilot.setTravelSpeed(MAXSPEED * 0.6);
		lightSensor1 = new LightSensor(SensorPort.S1);
		lightSensor2 = new LightSensor(SensorPort.S4);
		this.average = average;
	}

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub

		if (lightSensor1.getNormalizedLightValue() >= average && lightSensor2.getNormalizedLightValue() >= average) {
			pilot.setTravelSpeed(MAXSPEED * 0.6);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

		pilot.forward();
		System.out.println(lightSensor1.getNormalizedLightValue() + "  " + lightSensor2.getNormalizedLightValue()
				+ "    " + average);
		Delay.msDelay(50);

	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		pilot.stop();
	}

}
