package rp.assignments.team.warehouse.server;

import java.util.List;
import java.util.Set;

import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.assignment.AuctionPickAssigner;
import rp.assignments.team.warehouse.server.job.assignment.IPickAssigner;
import rp.assignments.team.warehouse.server.job.selection.IJobSelector;

public class ServerThread extends Thread {

    private Warehouse warehouse;
    private IJobSelector jobSelector;
    private IPickAssigner pickAssigner;

    public ServerThread(Warehouse warehouse, IJobSelector jobSelector) {
        assert warehouse != null;

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

    @Override
    public void run() {
        while (this.warehouse.isRunning()) {
            if (this.jobSelector != null && this.jobSelector.hasNext()) { // TODO be smarter
                if (getWorkingOnJobs().size() <= 0 || !jobsHaveAvailablePicks()) {
                    // TODO add another job to working on
                    if (jobSelector.hasNext()) {
                        Job newJob = this.jobSelector.next();
                        pickAssigner.addPicks(newJob.getAvailablePicks());
                        this.warehouse.addWorkingOnJob(newJob);
                    } else {
                        // TODO No more jobs...do something
                        return;
                    }
                }

                pickAssigner.next();
            }

            Thread.yield();
        }
    }
}