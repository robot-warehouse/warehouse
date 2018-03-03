package rp.assignments.team.warehouse.shared.communications;

/**
 * Type of command being sent to/from robot
 *
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
	 * End of current sequence of messages
	 */
	END;
}
