package rp.assignments.team.warehouse.server.job.assignment;

import rp.assignments.team.warehouse.server.job.Pick;

public interface Bidder extends Picker {

    /** Get the bidder's bid for a certain pick */
    public int getBid(Pick pick);

}
