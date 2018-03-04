package rp.assignments.team.warehouse.server.job.selection;

public interface ISelector<E> {
	/** Retrieves and removes the next highest priority element */
	E next();

	/** Peek at the next element without removing it */
	E preview();

	/** True if there are more elements which can be selected */
	boolean hasNext();
}
