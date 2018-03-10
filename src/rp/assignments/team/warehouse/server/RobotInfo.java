package rp.assignments.team.warehouse.server;

public enum RobotInfo {

	/** The robot you can't see */
	JOHNCENA("John Cena", "00165308E5A7"),

	/** The robot named TriHard */
	TRIHARD("TriHard", "0016530A631F"),

	/** The robot named */
	NAMELESS("Nameless", "0016530A9AD1");

    private final String name;
	private final String address;

	private RobotInfo(String name, String address) {
		this.name = name;
		this.address = address;
	}

	/**
	 * Get the name of the robot.
	 * 
	 * @return The name of the robot.
	 */
    public String getName() {
        return this.name;
    }

    /**
     * Get the address of the robot for bluetooth communications.
     * 
     * @return The bluetooth address of the robot.
     */
    public String getAddress() {
        return this.address;
    }
}
