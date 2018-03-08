package rp.assignments.team.warehouse.server.route.execution;

public class Instruction {

    /**
     * Tells the robot to take the left at a junction
     */
    public static final int LEFT = 0;

    /**
     * Tells the robot to take the right at a junction
     */
    public static final int RIGHT = 1;

    /**
     * Tells the robot go forward at a junction
     */
    public static final int FORWARDS = 2;

    /**
     * Tells the robot to turn around and go back the way it came
     */
    public static final int BACKWARDS = 3;

    /**
     * Tells the robot to stop
     */
    public static final int STOP = 4;
}
