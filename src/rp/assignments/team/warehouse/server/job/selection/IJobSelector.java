package rp.assignments.team.warehouse.server.job.selection;

import rp.assignments.team.warehouse.server.job.Job;

public interface IJobSelector {

    /**
     * Retrieves and removes the next highest priority job
     *
     * @return The next job to select.
     */
    public Job next();

    /**
     * Peek at the next job without removing it.
     *
     * @return The next job to select.
     */
    public Job preview();

    /**
     * Check if there are more jobs which can be selected.
     *
     * @return True if there are more jobs which can be selected
     */
    public boolean hasNext();

    /**
     * Remove a job from the selector. Called when a job has been cancelled.
     *
     * @param job The job to be removed.
     * @return True if a job was removed as a result of this call.
     */
    public boolean remove(Job job);
}
