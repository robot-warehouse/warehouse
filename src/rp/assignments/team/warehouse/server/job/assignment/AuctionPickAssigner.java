package rp.assignments.team.warehouse.server.job.assignment;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import rp.assignments.team.warehouse.server.Robot;
import rp.assignments.team.warehouse.server.job.Pick;

public class AuctionPickAssigner implements IPickAssigner {

    private Queue<Pick> picks;
    private Set<Robot> bidders;

    public AuctionPickAssigner(Queue<Pick> picks, Set<Robot> bidders) {
        super();
        this.picks = picks;
        this.bidders = bidders;
    }

    public AuctionPickAssigner(Set<Robot> bidders) {
        super();
        this.picks = new LinkedList<Pick>();
        this.bidders = bidders;
    }

    @Override
    public void next() {
        if (hasNext()) {
            Set<Bidder> availableBidders = this.bidders.stream().filter(x -> x.isAvailable())
                    .collect(Collectors.toCollection(HashSet::new));

            if (availableBidders.size() > 0) {
                Pick pick = this.picks.peek();

                assert pick != null;

                PickAuctioner auctioner = new PickAuctioner(pick, availableBidders);

                Picker picker = auctioner.auction();

                if (picker != null) {
                    this.picks.remove();
                    picker.assignPick(pick);
                }
            }
        }
    }

    @Override
    public boolean hasNext() {
        return !this.picks.isEmpty();
    }

    @Override
    public void addPicks(List<Pick> picks) {
        // TODO maybe order them
        this.picks.addAll(picks);
    }

}
