package rp.assignments.team.warehouse.server.job;

import rp.assignments.team.warehouse.server.Location;

public class Pick implements IRewardable {

    private final Job job;
    private final Item item;
    private boolean completed;

    /**
     * @param job The job the pick is for.
     * @param item The item to pick.
     */
    public Pick(Job job, Item item) {
        assert job != null;
        assert item != null;

        this.job = job;
        this.item = item;
        this.completed = false;
    }

    /**
     * Get the job the pick is for.
     *
     * @return The job the pick is for.
     */
    public Job getJob() {
        return this.job;
    }

    /**
     * Get the item to pick.
     *
     * @return The item to pick.
     */
    public Item getItem() {
        return this.item;
    }

    /**
     * Get the weight contributed by the pick.
     *
     * @return The weight of the item being picked.
     */
    public float getWeight() {
        return this.getItem().getWeight();
    }

    /**
     * Get the reward contributed by the pick.
     *
     * @return The reward of the item being picked.
     */
    public float getReward() {
        return this.getItem().getReward();
    }

    /**
     * Get the location to pick the item from.
     *
     * @return The pick location.
     */
    public Location getPickLocation() {
        return this.item.getLocation();
    }

    /**
     * Check if the pick has been completed.
     *
     * @return True if the pick has been completed.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Set the pick as completed.
     */
    public void setCompleted() {
        assert this.completed == false;

        this.completed = true;
        this.job.pickCompleted(this);
    }

}
