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
	 * @param jobItems
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
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @return the job items
	 */
	public List<JobItem> getJobItems() {
		return this.jobItems;
	}

	/**
	 * @return whether the job been previously cancelled
	 */
	public boolean isPreviouslyCancelled() {
		return this.previouslyCancelled;
	}

	/**
	 * @param cancelled
	 *            True if the job has been previously cancelled.
	 */
	public void setPreviouslyCancelled(boolean previouslyCancelled) {
		this.previouslyCancelled = previouslyCancelled;
	}

	/**
	 * @return the number of picks to complete the job
	 */
	public int getPickCount() {
		return this.pickCount;
	}

	public static int calcPickCount(Job j) {
		return j.jobItems.stream().mapToInt(x -> x.getCount()).sum();
	}

	/**
	 * @return the total reward of all the job items
	 */
	public float getReward() {
		return this.reward;
	}

	public static float calcReward(Job j) {
		return (float) j.jobItems.stream().mapToDouble(x -> (double) x.getReward()).sum();
	}

	/**
	 * @return the priority of the job
	 */
	public float getPriority() {
		return this.priority;
	}

	public static float calcPriority(Job j) {
		return j.getReward();
	}

	/**
	 * @return the total reward of all the items
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
