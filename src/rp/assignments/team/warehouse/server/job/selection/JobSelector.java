package rp.assignments.team.warehouse.server.job.selection;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class JobSelector implements ISelector<Job> {

	private Queue<Job> jobs;

	/**
	 * @param jobs
	 *            A list of the jobs to be available for selection.
	 */
	public JobSelector(List<Job> jobs) {
		jobs.sort(new CompareByPriorityComparator(false));
		Queue<Job> queue = new LinkedList<Job>(jobs);
		this.jobs = queue;
	}

	@Override
	public Job next() {
		return this.jobs.remove();
	}

	@Override
	public Job preview() {
		return this.jobs.element();
	}

	@Override
	public boolean hasNext() {
		return this.jobs.peek() != null;
	}

}
