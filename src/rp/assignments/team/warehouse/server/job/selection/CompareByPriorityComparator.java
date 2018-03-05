package rp.assignments.team.warehouse.server.job.selection;

import java.util.Comparator;
import rp.assignments.team.warehouse.server.job.IPrioritised;

public class CompareByPriorityComparator implements Comparator<IPrioritised> {
	private boolean ascending;

	public CompareByPriorityComparator() {
		this.ascending = true;
	}

	public CompareByPriorityComparator(boolean ascending) {
		super();
		this.ascending = ascending;
	}

	public int compare(IPrioritised a, IPrioritised b) {
		return (int) (a.getPriority() - b.getPriority()) * (this.ascending ? 1 : -1);
	}
}
