package rp.assignments.team.warehouse.server.job.assignment;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.server.Robot;
import rp.assignments.team.warehouse.server.job.Pick;
import rp.assignments.team.warehouse.server.job.comparators.CompareByRewardComparator;

public class AuctionPickAssigner implements IPickAssigner {

    private Queue<Pick> picks;
    private Set<Robot> bidders;

    private static final Logger logger = LogManager.getLogger(AuctionPickAssigner.class);

    /**
     * @param picks The initial picks for assignment.
     * @param bidders The bidders for the auction.
     */
    public AuctionPickAssigner(Queue<Pick> picks, Set<Robot> bidders) {
        super();
        this.picks = picks;
        this.bidders = bidders;
    }

    /**
     * @param bidders The bidders for the auction.
     */
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
                    logger.trace("Assigning pick for item {} in job {} to picker {}.", pick.getItem().getId(), pick.getJob().getId(), picker.getName());
                } else {
                    logger.error("PickAuctioner auctioned pick to null picker.");
                }
            }
        } else {
            logger.warn("Called next() when no picks remaining to assign");
        }
    }

    @Override
    public boolean hasNext() {
        return !this.picks.isEmpty();
    }

    @Override
    public void addPicks(List<Pick> picks) {
        Collections.sort(picks, new CompareByRewardComparator());

        this.picks.addAll(picks);
    }

}
