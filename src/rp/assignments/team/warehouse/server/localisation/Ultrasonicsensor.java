import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.config.RobotConfigs;
import rp.robotics.DifferentialDriveRobot;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
public class Ultrasonicsensor {
	DifferentialDriveRobot robot = new DifferentialDriveRobot(RobotConfigs.EXPRESS_BOT);
	DifferentialPilot pilot = robot.getDifferentialPilot();;
	UltrasonicSensor ultra = new UltrasonicSensor (SensorPort.S1);
	float closestAngle = -1.0f;
	float closestDistance = 1000.0f;

   
	public static void main(String[] args) {
		new Ultrasonicsensor();
}

	 public Ultrasonicsensor() {
		 pilot = new DifferentialPilot(1.5f, 6, Motor.A, Motor.B);
		 setupUltrasonic();
		 runProgram();
	 }
	 
	 public void setupUltrasonic() {
		 UltrasonicSensor ultra = new UltrasonicSensor (SensorPort.S1);
		 
		 
	 }
	 
	 public void runProgram() {
		 pilot.reset();
		 pilot.setRotateSpeed(45);
		 pilot.rotate(360, true);
		 float distance = 0.0f;
		 while (pilot.isMoving()) {
			 Delay.msDelay(10);
			  distance = ultra.getDistance();
			 if (distance < closestDistance) {
				 closestDistance = distance;
				 closestAngle = pilot.getAngleIncrement();
			 }
		 }
		  pilot.setRotateSpeed(180);
	      pilot.rotate(closestAngle-360);
	      pilot.forward();
	      distance = ultra.getDistance();
	      while (distance > 0.1f) {
	    	  Delay.msDelay(20);
	    	  distance = ultra.getDistance();
	    	  
	      }
	      pilot.stop();
	 }   
}	 