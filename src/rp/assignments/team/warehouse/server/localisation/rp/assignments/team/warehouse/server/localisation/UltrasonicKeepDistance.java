import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.config.RobotConfigs;
import rp.robotics.DifferentialDriveRobot;
// use ultrasonic sensor to maintain a distance away from something in front of robot.
public class UltrasonicKeepDistance {
	
	DifferentialDriveRobot robot = new DifferentialDriveRobot(RobotConfigs.EXPRESS_BOT);
	DifferentialPilot pilot = robot.getDifferentialPilot();;
	UltrasonicSensor ultra = new UltrasonicSensor (SensorPort.S1);
	
	public static void main(String[] args) {
		new UltrasonicKeepDistance();
		
	}
     public UltrasonicKeepDistance() {
    	   pilot = new DifferentialPilot(1.5f, 6, Motor.A, Motor.B);
    	   pilot.forward();
  	   setTravelSpeed(1.5f); 
    	   while (true) {
    		   Delay.msDelay(20);
    		   
    		   float distance = ultra.getDistance();
    		   if (distance < 0.3) {
    			   pilot.backward();
    		   }
    		   else if (distance > 0.4) {
    			   pilot.forward();
    		   }
    		   
    	   }
    		   
    		   
    	   }
	private void setTravelSpeed(float f) {
		// TODO Auto-generated method stub
		
	}
     }