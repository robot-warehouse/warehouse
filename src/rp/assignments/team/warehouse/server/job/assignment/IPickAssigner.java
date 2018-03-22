package rp.assignments.team.warehouse.server.job.assignment;

import java.util.List;

import rp.assignments.team.warehouse.server.job.Pick;

public interface IPickAssigner {

    /**
     * Assigns the next pick to an available picker.
     */
    public Pick next();

    /**
     * Check if there are more picks to be assigned.
     *
     * @return True if there are more picks which can be assigned.
     */
    public boolean hasNext();

    /**
     * Add more picks to the assigner.
     *
     * @param picks The picks to be available for assignment.
     */
    public void addPicks(List<Pick> picks);
}
