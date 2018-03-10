package rp.assignments.team.warehouse.server.route.execution;

public enum Instruction {

    /**
     * Tells the robot to take the left at a junction
     */
    LEFT(0),

    /**
     * Tells the robot to take the right at a junction
     */
    RIGHT(1),

    /**
     * Tells the robot go forward at a junction
     */
    FORWARDS(2),

    /**
     * Tells the robot to turn around and go back the way it came
     */
    BACKWARDS(3),

    /**
     * Tells the robot to stop
     */
    STOP(4);
    
    private final Integer value;
    
    private Instruction(int value) {
        this.value = value;
    }
    
    public int intValue() {
        return this.value;
    }
    
    public String toString() {
        return this.value.toString();
    }
}
