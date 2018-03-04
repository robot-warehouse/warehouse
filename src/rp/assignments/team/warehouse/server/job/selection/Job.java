package rp.assignments.team.warehouse.server.job.selection;

import java.util.List;
import java.util.stream.Collectors;

public class Job implements IIDed, IPrioritised, IRewardable {

	private final int id;
	private final List<JobItem> jobItems;
	private boolean previouslyCancelled;

	private final int pickCount;
	private final float reward;
	private final float averageReward;
	private final float priority;

	/**
	 * @param id
	 *            The job id.
	 * @param jobItems
	 *            List of job items.
	 */
	public Job(int id, List<JobItem> jobItems) {
		this.id = id;
		this.jobItems = jobItems;
		this.previouslyCancelled = false;
		this.pickCount = calcPickCount(this);
		this.reward = calcReward(this);
		this.averageReward = calcAverageReward(this);
		this.priority = calcPriority(this);
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
	 * Mark the job as having been previously cancelled. Note that once called this
	 * action cannot be undone.
	 */
	public void setPreviouslyCancelled() {
		this.previouslyCancelled = true;
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

	private static int calcPickCount(Job j) {
		return j.jobItems.stream().mapToInt(x -> x.getCount()).sum();
	}

	/**
	 * Get the total reward earned by fully completing the job.
	 *
	 * @return The total reward
	 */
	public float getReward() {
		return this.reward;
	}

	private static float calcReward(Job j) {
		return (float) j.jobItems.stream().mapToDouble(x -> (double) x.getReward()).sum();
	}

	/**
	 * Get the job priority.
	 *
	 * @return The job priority.
	 */
	public float getPriority() {
		return this.priority;
	}

	private static float calcPriority(Job j) {
		return j.getReward();
	}

	/**
	 * Get the average reward per item.
	 *
	 * @return The average reward per item.
	 */
	private float getAverageReward() {
		return this.averageReward;
	}

	private static float calcAverageReward(Job j) {
		return j.reward / j.pickCount;
	}

	@Override
	public String toString() {
		return String.format("%d %b %d{[%s]}", this.id, this.previouslyCancelled, this.jobItems.size(),
				this.jobItems.stream().map(JobItem::toString).collect(Collectors.joining("],[")));
	}

}
