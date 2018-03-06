package rp.assignments.team.warehouse.server;

import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.Pick;
import rp.assignments.team.warehouse.server.job.assignment.Bidder;
import rp.assignments.team.warehouse.server.job.assignment.Picker;
import rp.assignments.team.warehouse.server.route.planning.AStar;

import java.util.ArrayList;

public class Robot extends Thread implements Picker, Bidder {

	private String name;
	private Location location;
	private Pick currentPick;

	// TODO have hotswappable search algorithm
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

	public String getRobotName() {
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

	public void run() {

		// TODO what are we running here
		ArrayList<rp.assignments.team.warehouse.server.route.planning.State> instructions = AStar.findPath(location, currentPick.getPickLocation());
	}
}
