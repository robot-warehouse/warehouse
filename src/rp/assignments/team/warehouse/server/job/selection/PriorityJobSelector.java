package rp.assignments.team.warehouse.server.job.selection;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.comparators.CompareByPriorityComparator;

public class PriorityJobSelector implements IJobSelector {

    private Queue<Job> jobs;

    /**
     * @param jobs A list of the jobs to be available for selection.
     */
    public PriorityJobSelector(Set<Job> jobs) {
        Queue<Job> queue = new PriorityQueue<>(new CompareByPriorityComparator(false));
        queue.addAll(jobs);
        queue.removeIf(j -> j.isCancelled() || j.isPreviouslyCancelled() || j.isPredictedCancelled());
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

    @Override
    public boolean remove(Job job) {
        return this.jobs.remove(job);
    }
}
