package rp.assignments.team.warehouse.server;

import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.Location;
import rp.assignments.team.warehouse.server.job.Pick;
import rp.assignments.team.warehouse.server.job.assignment.Bidder;
import rp.assignments.team.warehouse.server.job.assignment.Picker;

public class Robot implements Picker, Bidder {

	private String name;
	private Location location;
	private Pick currentPick;

	public Robot(String name, Location location) {
		this.name = name;
		this.location = location;
		this.currentPick = null;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Job getCurrentJob() {
		if (this.currentPick == null) {
			return null;
		}

		return currentPick.getJob();
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean isAvailable() {
		return this.currentPick != null;
	}

	@Override
	public void assignPick(Pick pick) {
		assert this.currentPick == null;

		this.currentPick = pick;
	}

	@Override
	public int getBid(Pick pick) {
		// TODO Auto-generated method stub
		return 0;
	}

}
