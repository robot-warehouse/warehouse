

import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.config.RobotConfigs;
import rp.config.WheeledRobotConfiguration;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;


public class LineDetection extends RobotProgrammingDemo {
	private WheeledRobotSystem robot;
	private Random random = new Random();
	// Fixes the junction error problem
	private int junctionCounter = 0;
	
	public LineDetection(WheeledRobotConfiguration expressBot){
		this.robot = new WheeledRobotSystem(expressBot);
	}

	@Override
	public void run() {
		DifferentialPilot pilot = robot.getPilot();
		// TODO Auto-generated method stub
		LightSensor leftsensor = new LightSensor (SensorPort.S1);
		LightSensor rightsensor = new LightSensor (SensorPort.S2);
		
		int initialLeft = leftsensor.getLightValue();
		int initialRight = rightsensor.getLightValue();

	    pilot.setTravelSpeed(0.05f);
		while(true){
			
			
			pilot.forward();
			int LeftSensorMoving = leftsensor.getLightValue(); // current value
			int RightSensorMoving = rightsensor.getLightValue();
			/*System.out.println("Left sensor moving: ");
			System.out.println(LeftSensorMoving);
			System.out.println("Left sensor moving: ");
			System.out.println(RightSensorMoving);
			*/
			if(junctionCounter == 0){
				junctionCounter += 1;
			}else{
				if (Math.abs(initialLeft - LeftSensorMoving )< 8 && Math.abs(initialRight- RightSensorMoving) < 8){
			         
				     System.out.println("Junction found");
				     
				     pilot.travel(7, m_run);
				     Delay.msDelay(1000);
				     boolean direction=random.nextBoolean();
				     if(direction==true){
				    	 System.out.println("turning 90");
				     pilot.rotate(90);
				     }
				     else{
				    	 System.out.println("turning -90");
				    	 pilot.rotate(-90);
				     }
				     pilot.forward();
				
			}
			  
;			     //System.out.println("About to rotate");
			    // Delay.msDelay(1000);
			     //pilot.forward();
			    
				 //System.out.println("About to stop again");
				 //System.out.println("Moving forward");
			  }
			  
			 // System.out.println("If statement processed");
			  //*/
		}
	}
public static void main(String[] args){
		
		RobotProgrammingDemo rp = new LineDetection(RobotConfigs.EXPRESS_BOT);
		rp.run();
		Button.waitForAnyPress();
	}
}