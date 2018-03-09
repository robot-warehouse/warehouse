package rp.assignments.team.warehouse.server;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.server.communications.CommunicationsManager;
import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.Pick;
import rp.assignments.team.warehouse.server.job.assignment.Bidder;
import rp.assignments.team.warehouse.server.job.assignment.IPickAssigner;
import rp.assignments.team.warehouse.server.job.assignment.Picker;
import rp.assignments.team.warehouse.server.route.execution.RouteExecution;
import rp.assignments.team.warehouse.server.route.planning.AStar;

public class Robot extends Thread implements Picker, Bidder {

    private String name;
    private String address;
    private Location currentLocation;
    private Facing currentFacingDirection;
    private Pick currentPick;
    private boolean isAwaitingInstructions;
    
    private CommunicationsManager communicationsManager;
    
    private static Logger logger = LogManager.getLogger(Robot.class);

    public Robot(String name, String address, Location currentLocation) {
        this.name = name;
        this.address = address;
        this.currentLocation = currentLocation;
        this.currentPick = null;
    }

    public String getRobotName() {
        return name;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
    
    public Facing getCurrentFacingDirection() {
    	return currentFacingDirection;
    }
    
    public void setCurrentFacingDirection(Facing currentFacingDirection) {
    	this.currentFacingDirection = currentFacingDirection;
    }
    
    public void isAwaitingInstructions() {
    	this.isAwaitingInstructions = true;
    }
    
    public void isNoLongerAwaitingInstructions() {
    	this.isAwaitingInstructions = false;
    }

    public Job getCurrentJob() {
        if (this.currentPick == null) {
            return null;
        }

        return currentPick.getJob();
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

    @Override
    public void run() {
    	communicationsManager = new CommunicationsManager(name, address);
		communicationsManager.startServer();
		
    	while (communicationsManager.isConnected()) {
    		if (isAwaitingInstructions) {
    			ArrayList<Location> path = AStar.findPath(currentLocation, currentPick.getPickLocation());
    			ArrayList<Integer> instructions = RouteExecution.convertCoordinatesToInstructions(currentFacingDirection, path);
    			
    			communicationsManager.sendOrders(instructions);
    			
    			isNoLongerAwaitingInstructions();
    		}
    	}
    	
    	
    }
}
