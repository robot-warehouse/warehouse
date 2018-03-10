package rp.assignments.team.warehouse.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.server.communications.CommunicationsManager;
import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.Pick;
import rp.assignments.team.warehouse.server.job.assignment.Bidder;
import rp.assignments.team.warehouse.server.job.assignment.Picker;

public class Robot implements Picker, Bidder {

    private RobotInfo robotInfo;
    private Location currentLocation;
    private Facing currentFacingDirection;
    private Pick currentPick;
    private boolean hasComputedInstructionsForPick;
    
    private static Logger logger = LogManager.getLogger(Robot.class);

    public Robot(RobotInfo robotInfo, Location currentLocation, Facing currentFacingDirection) {
    	this.robotInfo = robotInfo;
        this.currentLocation = currentLocation;
        this.currentFacingDirection = currentFacingDirection;
        this.currentPick = null;
    }

    public String getName() {
        return robotInfo.name;
    }
    
    public String getAddress() {
    	return robotInfo.address;
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
    
    public boolean hasComputedInstructionsForPick() {
    	return hasComputedInstructionsForPick;
    }
    
    public void setHasComputedInstructionsForPick(boolean hasIt) {
    	hasComputedInstructionsForPick = hasIt;
    } 

    public Job getCurrentJob() {
        if (this.currentPick == null) {
            return null;
        }

        return currentPick.getJob();
    }
    
    public Pick getCurrentPick() {
    	return currentPick;
    }
    
    public boolean connect() {
    	CommunicationsManager commsManager = new CommunicationsManager(getName(), getAddress());
    	commsManager.startServer();
    	
    	if (commsManager.isConnected()) {
    		(new RobotThread(this, commsManager)).start();
    		return true;
    	}
    	
    	return false;
    }

    @Override
    public boolean isAvailable() {
        return this.currentPick != null;
    }

    @Override
    public void assignPick(Pick pick) {
        assert this.currentPick == null;

        this.currentPick = pick;
        setHasComputedInstructionsForPick(false); // this might need to be before the line above
    }

    @Override
    public int getBid(Pick pick) {
        // TODO Auto-generated method stub
        return 0;
    }
}
