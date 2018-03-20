package rp.assignments.team.warehouse.server.job.comparators;

import java.util.Comparator;

import rp.assignments.team.warehouse.server.job.IRewardable;

public class CompareByRewardComparator implements Comparator<IRewardable> {

    private boolean ascending;

    public CompareByRewardComparator() {
        this.ascending = false;
    }

    public CompareByRewardComparator(boolean ascending) {
        super();
        this.ascending = ascending;
    }

    @Override
    public int compare(IRewardable a, IRewardable b) {
        return (int) (a.getReward() - b.getReward()) * (this.ascending ? 1 : -1);
    }
}
