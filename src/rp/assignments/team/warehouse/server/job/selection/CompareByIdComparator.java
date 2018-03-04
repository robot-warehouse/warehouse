package rp.assignments.team.warehouse.server.job.selection;

import java.util.Comparator;

class CompareByIdComparator implements Comparator<IIDed> {
	public int compare(IIDed a, IIDed b) {
		return a.getId() - b.getId();
	}
}
