package rp.assignments.team.warehouse.server.job;

import rp.assignments.team.warehouse.server.Location;

public class Pick implements IRewardable {

    /** The job the pick is for */
    private final Job job;

    /** The item to pickup */
    private final Item item;

    /** Whether the pick has been picked */
    private boolean picked;

    /** Whether the pick has been completed */
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
        this.picked = false;
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
     * @see Item#getReward()
     */
    public float getReward() {
        return this.getItem().getReward();
    }

    /**
     * Get the location to pick the item from.
     *
     * @return The pick location.
     * @see Item#getLocation()
     */
    public Location getPickLocation() {
        return this.item.getLocation();
    }

    /**
     * Get the location to drop the item off.
     *
     * @return The drop location.
     * @see Job#getDropLocation()
     */
    public Location getDropLocation() {
        return this.getJob().getDropLocation();
    }

    /**
     * Check if the item has been picked from the pick location.
     *
     * @return True if the item has been picked.
     */
    public boolean isPicked() {
        return picked;
    }

    /**
     * Set the pick as picked.
     */
    public void setPicked() {
        assert !this.picked;

        this.picked = true;
    }

    /**
     * Set the pick as not picked. Drop the pick and set as if it was never picked.
     */
    public void setDropped() {
        assert this.picked;

        this.picked = false;
    }

    /**
     * Check if the pick has been completed.
     *
     * @return True if the item has been dropped off.
     */
    public boolean isCompleted() {
        return this.completed;
    }

    /**
     * Set the pick as completed.
     */
    public void setCompleted() {
        assert !this.completed;

        this.completed = true;
        this.job.pickCompleted(this);
    }

    /**
     * Check if a pick has the same job and item as this one.
     *
     * @param pick The pick to compare with.
     * @return True if the pick has the same job and item as this pick.
     */
    public boolean isSameJobAndItem(Pick pick) {
        return this.getJob().equals(pick.getJob()) && this.getItem().equals(pick.getItem());
    }

}
