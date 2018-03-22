package rp.assignments.team.warehouse.server.job.assignment;

import rp.assignments.team.warehouse.server.job.Pick;

public interface Bidder extends Picker {

    /**
     * Get the bidder's bid for a given pick.
     *
     * @param pick The pick to calculate the bid for.
     * @return The bid for the pick.
     */
    public int getBid(Pick pick);
}
