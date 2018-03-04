package rp.assignments.team.warehouse.server.job.selection;

public class JobItem {

	private final Item item;
	private final int count;

	/**
	 * @param item
	 *            The item.
	 * @param count
	 *            How many of the required item need to be dropped off.
	 */
	public JobItem(Item item, int count) {
		assert count > 0;

		this.item = item;
		this.count = count;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return this.item;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * @return the reward of a single item times the how many to pick
	 */
	public float getReward() {
		return this.item.getReward() * this.count;
	}

	@Override
	public String toString() {
		return String.format("%d*<%s>", this.count, this.item);
	}

}
