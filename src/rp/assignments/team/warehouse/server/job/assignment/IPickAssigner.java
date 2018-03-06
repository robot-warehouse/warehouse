package rp.assignments.team.warehouse.server.job.assignment;

public interface IPickAssigner {

    /** Assigns the next pick to an available picker */
    public void next();

    /** True if there are more picks which can be assigned */
    public boolean hasNext();

}
