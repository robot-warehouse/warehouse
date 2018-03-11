package rp.assignments.team.warehouse.server.job.assignment;

import java.util.List;

import rp.assignments.team.warehouse.server.job.Pick;

public interface IPickAssigner {

    /** Assigns the next pick to an available picker */
    public void next();

    /** True if there are more picks which can be assigned */
    public boolean hasNext();

    public void addPicks(List<Pick> picks);

}
