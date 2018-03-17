package rp.assignments.team.warehouse.server.job.assignment;

import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rp.assignments.team.warehouse.server.Robot;
import rp.assignments.team.warehouse.server.job.Pick;

public class PickAuctionerTest {

    private static Pick pick;
    private static Bidder bidder0, bidder1;

    private static Set<Bidder> bidders;

    private PickAuctioner auctioner;

    @BeforeClass
    public static void setupBeforeClass() {
        pick = mock(Pick.class);
        when(pick.getReward()).thenReturn(10.0f);

        bidder0 = mock(Robot.class);
        when(bidder0.isAvailable()).thenReturn(true);
        when(bidder0.getBid(pick)).thenReturn(50);
        bidder1 = mock(Robot.class);
        when(bidder1.isAvailable()).thenReturn(true);
        when(bidder1.getBid(pick)).thenReturn(30);

        bidders = new HashSet<Bidder>();
        bidders.add(bidder0);
        bidders.add(bidder1);
    }

    @Before
    public void setupBeforeEach() {
        auctioner = new PickAuctioner(pick, bidders);
    }

    @Test
    public void auctionGivesPickToCorrectBidder() {
        Assert.assertEquals(bidder1, auctioner.auction());
    }

}