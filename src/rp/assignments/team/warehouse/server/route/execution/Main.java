package RobotMotionControl;

public static void main(String[] args){
		
		RobotProgrammingDemo rp = new RobotMotionControl(RobotConfigs.EXPRESS_BOT);
		rp.run();
		Button.waitForAnyPress();
	}
