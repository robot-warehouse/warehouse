package rp.assignments.team.warehouse.server.job.assignment;

import java.util.Set;

import rp.assignments.team.warehouse.server.job.Pick;

public class PickAuctioner {

	private final Pick pick;
	private final Set<Bidder> bidders;

	/**
	 * @param pick The pick to assign
	 * @param bidders The available bidders
	 */
	public PickAuctioner(Pick pick, Set<Bidder> bidders) {
		super();
		this.pick = pick;
		this.bidders = bidders;
	}

	/**
	 * Auction the pick to a bidder.
	 * 
	 * @return The winning bidder.
	 */
	public Bidder auction() {
		Bidder winner = null;
		int bestBid = Integer.MAX_VALUE;

		for (Bidder b : this.bidders) {
			int bid = b.getBid(this.pick);

			if (bid < bestBid) {
				winner = b;
				bestBid = bid;
			}
		}

		return winner;
	}

}
