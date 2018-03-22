package rp.assignments.team.warehouse.server.job;

public class JobItem {

    /** The item */
    private final Item item;

    /** The number of items */
    private final int count;

    /**
     * @param item The item.
     * @param count How many of the required item need to be dropped off.
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
     * @see Item#getReward()
     */
    public float getReward() {
        return this.item.getReward() * this.count;
    }

    /**
     * @return the weight of a single item times the how many to pick
     * @see Item#getWeight()
     */
    public float getWeight() {
        return this.item.getWeight() * this.count;
    }

    @Override
    public String toString() {
        return String.format("%d*<%s>", this.count, this.item);
    }
}
