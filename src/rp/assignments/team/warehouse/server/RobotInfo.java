package rp.assignments.team.warehouse.server;

public enum RobotInfo {

	/**
	 * The robot you can't see 
	 */
	JOHNCENA("John Cena", "00165308E5A7"),
	
	/**
	 * The robot named TriHard
	 */
	TRIHARD("TriHard", "0016530A631F"),
	
	/**
	 * The robot named  
	 */
	NAMELESS("Nameless", "0016530A9AD1");
	
	public String name;
	public String address;
	
	private RobotInfo(String name, String address) {
		this.name = name;
		this.address = address;
	}
}
