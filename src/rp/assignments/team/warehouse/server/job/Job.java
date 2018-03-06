package rp.assignments.team.warehouse.server.job;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Job implements IIDed, IPrioritised, IRewardable {

    private final int id;
    private final List<JobItem> jobItems;
    private final int pickCount;
    private final float reward;
    private final float averageReward;
    private final float priority;
    private boolean previouslyCancelled;
    private List<Pick> availablePicks;
    private int completedPickCount;

    /**
     * @param id       The job id.
     * @param jobItems List of job items.
     */
    public Job(int id, List<JobItem> jobItems) {
        assert id >= 0;

        this.id = id;
        this.jobItems = jobItems;
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

    private static float calcPriority(Job j) {
        return j.getReward();
    }

    private static float calcAverageReward(Job j) {
        return j.reward / j.pickCount;
    }

    /**
     * @return The job id.
     */
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
     * @return True if the job has been previously cancelled.
     */
    public boolean isPreviouslyCancelled() {
        return this.previouslyCancelled;
    }

    /**
     * Mark the job as having been previously cancelled. Note that once called this action cannot be undone.
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
        assert pick.isCompleted();

        if (!this.availablePicks.contains(pick)) {
            throw new IllegalArgumentException("Completed pick must belong to the job and not already be completed.");
        }

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
     * Get the number of individual picks to complete the job. This sums the count for each job item.
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
    public float getReward() {
        return this.reward;
    }

    /**
     * Get the job priority.
     *
     * @return The job priority.
     */
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

    @Override
    public String toString() {
        return String.format("%d %b %d{[%s]}", this.id, this.previouslyCancelled, this.jobItems.size(),
                this.jobItems.stream().map(JobItem::toString).collect(Collectors.joining("],[")));
    }

}
