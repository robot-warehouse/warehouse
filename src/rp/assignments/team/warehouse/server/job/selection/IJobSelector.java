package rp.assignments.team.warehouse.server.job.selection;

import rp.assignments.team.warehouse.server.job.Job;

public interface IJobSelector {
	/** Retrieves and removes the next highest priority job */
	Job next();

	/** Peek at the next job without removing it */
	Job preview();

	/** True if there are more jobs which can be selected */
	boolean hasNext();
}
