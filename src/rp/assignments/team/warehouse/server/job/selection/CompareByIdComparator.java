package rp.assignments.team.warehouse.server.job.selection;

import rp.assignments.team.warehouse.server.job.IIDed;

import java.util.Comparator;

public class CompareByIdComparator implements Comparator<IIDed> {

    public int compare(IIDed a, IIDed b) {
        return a.getId() - b.getId();
    }
}
