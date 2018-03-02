package RobotMotionControl;

import java.util.List;
import java.util.ListIterator;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class LineFollow implements Behavior {
	
	private DifferentialPilot DP;
	private LightSensor light1;
	private LightSensor light2;
	private LightSensor light3;
	private final int Kp = 20;
	//private final int Ki = 0;
	//private final int Kd = 0;
	private int offset = 34;
	private int targetPower = 70;
	private int lightValue;
	private int errorSignal;
	private int turnValue;
	private int powerA;
	private int powerB;
	private float infinity = Float.POSITIVE_INFINITY;
	private boolean moveForward;
	private Path path = new Path();
	private ListIterator<Integer> listIterate;

	
	public LineFollow(DifferentialPilot DP, SensorPort port1, SensorPort port2, SensorPort port3) {
		this.DP = DP;
		light1 = new LightSensor(port1);
		light2 = new LightSensor(port2);
		light3 = new LightSensor(port3);
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void suppress() {

	}
	
	public boolean stopMoving() {
		moveForward = false;
		return moveForward;
	}
	
	public boolean junctionReached() {
		if (light1.readValue() <= 30 && light3.readValue() <= 30) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	public void getAction(int currentAction) {
		if (currentAction == 0) {
			DP.rotate(90);
		}
		
		else if (currentAction == 1) {
			DP.rotate(-90);
		}
		
		else if (currentAction == 2) {
			DP.rotate(180);
		}
		
		else if (currentAction == 3) {
			//Keep going
		}
		
		else if (currentAction == 4) {
			DP.stop();
		}
		
		else {
			
		}
	}
	
	public void setPowerA (int powerA) {
		if (powerA > 0) {
			MotorPort.A.controlMotor(powerA, 1);
		}
		
		else {
			powerA = powerA * -1;
			MotorPort.A.controlMotor(powerA, 2);
		}
	}
	
	public void setPowerB (int powerB) {
		if (powerB > 0) {
			MotorPort.A.controlMotor(powerB, 1);
		}
		
		else {
			powerB = powerB * -1;
			MotorPort.A.controlMotor(powerB, 2);
		}
	}
	
	@Override
	public void action() {
		
		List<Integer> instructionSet = path.getPathList();
		listIterate = instructionSet.listIterator();
		int currentAction = 0;
		
		DP.travel(infinity, true);
		moveForward = true;
		
		while(moveForward) {
			lightValue = light2.readValue();
			System.out.println("VALUE: " + lightValue);
			errorSignal = lightValue - offset;
			
			turnValue = Kp * errorSignal;
			
			powerA = targetPower - turnValue;
			powerB = targetPower + turnValue;
			
			setPowerA(powerA);
			setPowerB(powerB);
			
			/*Boolean check = junctionReached();
			
			if(check) {
				DP.stop();
				DP.rotate(90);
				DP.stop();
				if (listIterate.hasNext()) {
					currentAction = listIterate.next();
					getAction(currentAction);
					continue;
				}
				
				else {
					continue; //Start while loop again
				}
			}
			
			else {
				//errorSignal = 0;
			}*/
		}
	}
}