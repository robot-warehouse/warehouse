package rp.assignments.team.warehouse.server.job.assignment;

import rp.assignments.team.warehouse.server.job.Pick;

public interface Picker {
	
	/**
	 * Is the picker available to be assigned a pick.
	 * 
	 * @return True if the picker can take a new pick.
	 */
	public boolean isAvailable();
	
	/**
	 * Assign a new pick to the picker.
	 */
	public void assignPick(Pick pick);
	
}
