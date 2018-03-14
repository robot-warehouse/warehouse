package rp.assignments.team.warehouse.shared.communications;

/**
 * Type of command being sent to/from robot
 */
public enum Command {
    /**
     * Tell the robot to start reading orders
     */
    SEND_ORDERS,
    /**
     * Cancel the current set of orders
     */
    CANCEL,
    /**
     * Send the position of the robot to the pc
     */
    SEND_POSITION,
    /**
     * Current job has been completed.
     */
    FINISHED_JOB,
    /**
	 * Send the number of the picks
	 */
	SEND_PICKS,
    /**
     * End of current sequence of messages
     */
    END,
    /**
     * Disconnect the robot from the server
     */
    DISCONNECT,
    /**
     * Attempt to reconnect with a robot.
     */
    RECONNECT;

    /**
     * Convert a string into a {@link Command}.
     *
     * @param s The string to convert from.
     * @return The command the string represents.
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static Command strToCommand(String s) throws NullPointerException, IllegalArgumentException {
        for (Command c : Command.values()) {
            if (c.toString().equals(s)) {
                return c;
            }
        }

        throw new IllegalArgumentException("Not a value of this enum");
    }
}
