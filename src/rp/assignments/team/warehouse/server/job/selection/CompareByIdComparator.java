package rp.assignments.team.warehouse.server.job.selection;

import java.util.Comparator;
import rp.assignments.team.warehouse.server.job.IIDed;

public class CompareByIdComparator implements Comparator<IIDed> {
	public int compare(IIDed a, IIDed b) {
		return a.getId() - b.getId();
	}
}
