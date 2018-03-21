package rp.assignments.team.warehouse.server;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.assignment.AuctionPickAssigner;
import rp.assignments.team.warehouse.server.job.assignment.IPickAssigner;
import rp.assignments.team.warehouse.server.job.selection.IJobSelector;

public class ServerThread extends Thread {

    /** The Warehouse. */
    private Warehouse warehouse;

    /** The job selector */
    private IJobSelector jobSelector;

    /** The pick assigner */
    private IPickAssigner pickAssigner;

    /** The index of the next drop location */
    private int dropIndex = 0;

    /**
     * @param warehouse The Warehouse.
     * @param jobSelector The job selector.
     */
    public ServerThread(Warehouse warehouse, IJobSelector jobSelector) {
        assert warehouse != null;
        assert jobSelector != null;

        this.warehouse = warehouse;
        this.jobSelector = jobSelector;
        this.pickAssigner = new AuctionPickAssigner(warehouse.getRobots());
    }

    /**
     * Get the jobs currently being worked on.
     * 
     * @return List of jobs being worked on.
     */
    public Set<Job> getWorkingOnJobs() {
        return this.warehouse.getWorkingOnJobs();
    }

    /**
     * Check if the jobs being worked on have any picks available.
     * 
     * @return True if any job has picks available.
     */
    public boolean jobsHaveAvailablePicks() {
        return this.pickAssigner.hasNext();
        // return getWorkingOnJobs().stream()
        //     .anyMatch(j -> j.hasAvailablePicks());
    }

    private Location getNextDropLocation() {
        List<Location> dropLocations = this.warehouse.getDropLocations();

        if (this.dropIndex >= dropLocations.size()) {
            this.dropIndex = 0;
        }

        return dropLocations.get(dropIndex++);
    }

    @Override
    public void run() {
        while (this.warehouse.isRunning()) {
        	Iterator<Job> jobIterator = this.getWorkingOnJobs().iterator();
        	
        	while (jobIterator.hasNext()) {
        		Job job = jobIterator.next();
        		
        		if (job.isComplete()) {
        			this.warehouse.completeJob(job);
        		}
        	}
        	
            if (this.jobSelector.hasNext()) { // TODO be smarter (what does this mean I can't remember)
            	Set<Job> workingOnJobs = getWorkingOnJobs();
                if (workingOnJobs.size() <= 0 || !jobsHaveAvailablePicks()) {
                    Job newJob = this.jobSelector.next();
                    newJob.setDropLocation(getNextDropLocation());

                    pickAssigner.addPicks(newJob.getAvailablePicks());
                    this.warehouse.addWorkingOnJob(newJob);
                }

                pickAssigner.next();
            } else {
        	    this.warehouse.assignedAllJobs();
                return;
            }

            Thread.yield();
        }
    }
}
