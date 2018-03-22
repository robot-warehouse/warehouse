package rp.assignments.team.warehouse.server.job.assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    /** Queue of picks to assign. */
    private Queue<Pick> picks;

    /** The robots in the warehouse. */
    private Set<Robot> robots;

    private static final Logger logger = LogManager.getLogger(AuctionPickAssigner.class);

    /**
     * @param picks The initial picks for assignment.
     * @param robots The bidders for the auction.
     */
    public AuctionPickAssigner(List<Pick> picks, Set<Robot> robots) {
        super();
        this.picks = new LinkedList<>();
        picks.sort(new CompareByRewardComparator());
        this.picks.addAll(picks);
        this.robots = robots;
    }

    /**
     * @param robots The bidders for the auction.
     */
    public AuctionPickAssigner(Set<Robot> robots) {
        super();
        this.picks = new LinkedList<>();
        this.robots = robots;
    }

    @Override
    public Pick next() {
        if (hasNext()) {
            Pick pick = this.picks.peek();

            // Get bidders which either don't have any picks or have picks which are for the same job & item as the
            // next pick
            // Sort bidders so that we give more picks of the same job & item to the same one
            List<Bidder> availableBidders = this.robots.stream()
                .filter(x -> x.isAvailable(pick))
                .sorted(Comparator.comparingInt(x -> x.getCurrentPicks().size()))
                .collect(Collectors.toCollection(ArrayList::new));

            if (availableBidders.size() > 0) {

                assert pick != null;

                PickAuctioneer auctioneer = new PickAuctioneer(pick, availableBidders);

                Picker picker = auctioneer.auction();

                if (picker != null) {
                    this.picks.remove();
                    picker.assignPick(pick);
                    logger.trace("Assigning pick for item <{}> in job {} to picker {}.", pick.getItem().toString(),
                        pick.getJob().getId(), picker.getName());
                    return pick;
                } else {
                    logger.error("PickAuctioneer auctioned pick to null picker.");
                }
            }
        } else {
            logger.warn("Called next() when no picks remaining to assign");
        }

        return null;
    }

    @Override
    public boolean hasNext() {
        return !this.picks.isEmpty();
    }

    @Override
    public void addPicks(List<Pick> picks) {
        picks.sort(new CompareByRewardComparator());

        this.picks.addAll(picks);
    }

}
