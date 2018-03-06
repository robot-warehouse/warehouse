package rp.assignments.team.warehouse.server.job.selection;

import rp.assignments.team.warehouse.server.job.IRewardable;

import java.util.Comparator;

public class CompareByRewardComparator implements Comparator<IRewardable> {

    private boolean ascending;

    public CompareByRewardComparator() {
        this.ascending = true;
    }

    public CompareByRewardComparator(boolean ascending) {
        super();
        this.ascending = ascending;
    }

    public int compare(IRewardable a, IRewardable b) {
        return (int) (a.getReward() - b.getReward()) * (this.ascending ? 1 : -1);
    }
}
