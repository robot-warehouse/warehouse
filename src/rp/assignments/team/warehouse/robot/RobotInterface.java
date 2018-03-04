package rp.assignments.team.warehouse.robot;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class RobotInterface {

	public RobotInterface() {
		// TODO Auto-generated constructor stub
	}
	
	public void pickItem(int numberOfItems){
		int i=0;
		System.out.println("Select one item to pick");
		while(!Button.ESCAPE.isDown())
		Button.waitForAnyPress();
		System.out.println("You selected one more item");
		i++;
		if(i > numberOfItems){
			i--;
			System.out.println("Too many items are selected");
		}
		
	}
	
	public void dromItem(){
		System.out.println("Press to drop all the items");
		Button.waitForAnyPress();
		System.out.println("All items are delivered");
	}
	
	
	public void cancellation(){
		System.out.println("Task was cancelled");
	}

	
	public void calibration(){
		LightSensor lightSensor1 = new LightSensor(SensorPort.S1);
		LightSensor lightSensor2 = new LightSensor(SensorPort.S4);
		LightSensor lightSensor3 = new LightSensor(SensorPort.S2);
		int white, black, white2, black2, white3, black3 = 0;
		Button.waitForAnyPress();
		System.out.println("Light colour");
		Button.waitForAnyPress();
		white = lightSensor1.getNormalizedLightValue();
		white2 = lightSensor2.getNormalizedLightValue();
		white3 = lightSensor3.getNormalizedLightValue();
		int averageWhite = (white + white2 + white3) / 3;
		System.out.println("White colour: " + averageWhite);
		System.out.println("Dark colour");
		Button.waitForAnyPress();
		lightSensor1.calibrateLow();
		lightSensor2.calibrateLow();
		black = lightSensor1.getNormalizedLightValue();
		black2 = lightSensor2.getNormalizedLightValue();
		black3 = lightSensor3.getNormalizedLightValue();
		int averageBlack = (black + black2+ black3) / 3;
		System.out.println("Black colour: " + averageBlack);

		int average = (averageBlack + averageWhite) / 2;
		Button.waitForAnyPress();
	}
}
