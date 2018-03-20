package rp.assignments.team.warehouse.server.job;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import rp.assignments.team.warehouse.server.Location;

public class Job implements IIDed, IPrioritised, IRewardable {

    private final int id;
    private final List<JobItem> jobItems;
    private final int pickCount;
    private final float reward;
    private final float averageReward;
    private final float priority;
    private boolean cancelled;
    private boolean previouslyCancelled;
    private List<Pick> availablePicks;
    private int completedPickCount;
    private Location dropLocation;

    /**
     * @param id The job id.
     * @param jobItems List of job items.
     */
    public Job(int id, List<JobItem> jobItems) {
        assert id >= 0;
        assert jobItems != null;

        this.id = id;
        this.jobItems = jobItems;
        this.cancelled = false;
        this.previouslyCancelled = false;
        this.pickCount = calcPickCount(this);
        this.reward = calcReward(this);
        this.averageReward = calcAverageReward(this);
        this.priority = calcPriority(this);
        this.availablePicks = generatePicks();
        this.completedPickCount = 0;
    }

    private static int calcPickCount(Job j) {
        return j.jobItems.stream().mapToInt(x -> x.getCount()).sum();
    }

    private static float calcReward(Job j) {
        return (float) j.jobItems.stream().mapToDouble(x -> (double) x.getReward()).sum();
    }

    /**
     * Get the priority of a job.
     *
     * @param job The job to calculate the priority of.
     * @return The priority of the given job.
     */
    private static float calcPriority(Job job) {
        return job.getReward();
    }

    /**
     * Calculate the average reward of a job.
     *
     * @param job The job to calculate the average reward for.
     * @return The average reward of the given job.
     */
    private static float calcAverageReward(Job job) {
        return job.reward / job.pickCount;
    }

    /**
     * @return The job id.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * @return The job items.
     */
    public List<JobItem> getJobItems() {
        return this.jobItems;
    }

    /**
     * @return True if the job has been cancelled.
     */
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Mark the job as now cancelled. Any progress made towards the job is now worthless
     * and execution of the job should be terminated ASAP.
     */
    public void setCancelled() {
        this.cancelled = true;
    }

    /**
     * @return True if the job has been previously cancelled.
     */
    public boolean isPreviouslyCancelled() {
        return this.previouslyCancelled;
    }

    /**
     * Mark the job as having been previously cancelled. Note that once called this
     * action cannot be undone.
     */
    public void setPreviouslyCancelled() {
        this.previouslyCancelled = true;
    }

    /**
     * Notify that a pick was completed. Should only be called by the pick.
     *
     * @param pick The pick calling this method.
     */
    public void pickCompleted(Pick pick) {
        assert pick != null;
        assert pick.getJob().equals(this);
        assert pick.isCompleted();

        if (!this.availablePicks.contains(pick)) {
            throw new IllegalArgumentException("Completed pick must belong to the job and not already be completed.");
        }

        this.availablePicks.remove(pick);
        this.completedPickCount++;
    }

    /**
     * Check if the job has been completed.
     *
     * @return True if all picks for the job have been completed.
     */
    public boolean isComplete() {
        assert this.completedPickCount >= 0;
        assert this.completedPickCount <= this.pickCount;

        return this.completedPickCount == this.pickCount;
    }

    /**
     * Get the number of picks that have been completed.
     *
     * @return The number of picks completed.
     */
    public int getCompletedPickCount() {
        return this.completedPickCount;
    }

    /**
     * Check if the job has been completed.
     *
     * @return True if all picks for the job have been completed.
     */
    public boolean hasAvailablePicks() {
        return this.availablePicks.size() > 0;
    }

    /**
     * Get a list of available picks for the job.
     *
     * @return A list of available picks.
     */
    public List<Pick> getAvailablePicks() {
        return this.availablePicks;
    }

    /**
     * Get the number of individual picks to complete the job. This sums the count
     * for each job item.
     *
     * @return The number of picks.
     */
    public int getPickCount() {
        return this.pickCount;
    }

    /**
     * Get the total reward earned by fully completing the job.
     *
     * @return The total reward
     */
    @Override
    public float getReward() {
        return this.reward;
    }

    /**
     * Get the job priority.
     *
     * @return The job priority.
     */
    @Override
    public float getPriority() {
        return this.priority;
    }

    /**
     * Get the average reward per item.
     *
     * @return The average reward per item.
     */
    private float getAverageReward() {
        return this.averageReward;
    }

    /**
     * Generate a list of picks for this job.
     *
     * @return The list of picks.
     */
    private List<Pick> generatePicks() {
        List<Pick> picks = new ArrayList<Pick>(this.pickCount);

        for (JobItem ji : this.jobItems) {
            for (int i = 0; i < ji.getCount(); i++) {
                picks.add(new Pick(this, ji.getItem()));
            }
        }

        return picks;
    }

    /**
     * Get the drop location for the job.
     *
     * @return The drop location for the job.
     */
    public Location getDropLocation() {
        return this.dropLocation;
    }

    /**
     * Set the drop location for the job.
     *
     * @param location The drop location for the job.
     */
    public void setDropLocation(Location location) {
        this.dropLocation = location;
    }

    @Override
    public String toString() {
        return String.format("%d %b %d{[%s]}", this.id, this.previouslyCancelled, this.jobItems.size(),
                this.jobItems.stream().map(JobItem::toString).collect(Collectors.joining("],[")));
    }

}
