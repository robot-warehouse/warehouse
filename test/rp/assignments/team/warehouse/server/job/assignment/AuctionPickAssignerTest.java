package rp.assignments.team.warehouse.server.job.assignment;

import rp.assignments.team.warehouse.server.Robot;
import rp.assignments.team.warehouse.server.job.Item;
import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.Pick;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AuctionPickAssignerTest {

    private Job job0;
    private Item item0;
    private Pick pick0, pick1, pick2;
    private Robot bidder0, bidder1;

    private List<Pick> picks;
    private Set<Robot> bidders;

    private AuctionPickAssigner assigner;

    private boolean usingInitialPicks;

    @Parameters(name = "{index}: (initial picks = {0})")
    public static Iterable<Object> data() {
        return Arrays.asList(new Object[] {
            true, false
        });
    }

    public AuctionPickAssignerTest(boolean usingInitialPicks) {
        this.usingInitialPicks = usingInitialPicks;
    }

    @Before
    public void beforeEach() {

        job0 = mock(Job.class);
        when(job0.getId()).thenReturn(0);
        item0 = mock(Item.class);
        when(item0.getId()).thenReturn(0);
        
        pick0 = mock(Pick.class);
        when(pick0.getReward()).thenReturn(10.0f);
        when(pick0.getWeight()).thenReturn(0.0f);
        when(pick0.getJob()).thenReturn(job0);
        when(pick0.getItem()).thenReturn(item0);
        pick1 = mock(Pick.class);
        when(pick1.getReward()).thenReturn(5.0f);
        when(pick1.getWeight()).thenReturn(0.0f);
        when(pick1.getJob()).thenReturn(job0);
        when(pick1.getItem()).thenReturn(item0);
        pick2 = mock(Pick.class);
        when(pick2.getReward()).thenReturn(15.0f);
        when(pick2.getWeight()).thenReturn(0.0f);
        when(pick2.getJob()).thenReturn(job0);
        when(pick2.getItem()).thenReturn(item0);

        picks = new LinkedList<Pick>();
        picks.add(pick0);
        picks.add(pick1);
        picks.add(pick2);

        bidder0 = mock(Robot.class);
        when(bidder0.isAvailable(any(Pick.class))).thenReturn(true);
        when(bidder0.getBid(pick0)).thenReturn(50);
        when(bidder0.getBid(pick1)).thenReturn(30);
        when(bidder0.getBid(pick2)).thenReturn(10);
        when(bidder0.getName()).thenReturn("Bidder0");
        bidder1 = mock(Robot.class);
        when(bidder1.isAvailable(any(Pick.class))).thenReturn(true);
        when(bidder1.getBid(pick0)).thenReturn(80);
        when(bidder1.getBid(pick1)).thenReturn(20);
        when(bidder1.getBid(pick2)).thenReturn(Integer.MAX_VALUE);
        when(bidder1.getName()).thenReturn("Bidder1");

        bidders = new HashSet<Robot>();
        bidders.add(bidder0);
        bidders.add(bidder1);
        
        if (this.usingInitialPicks) {
            assigner = new AuctionPickAssigner(picks, bidders);
        } else {
            assigner = new AuctionPickAssigner(bidders);
            assigner.addPicks(picks);
        }
    }
    
    @Test
    public void nextShouldAssignPicksInOrderOfReward() {
        Pick assigned;
        
        assigned = assigner.next();
        Assert.assertEquals(pick2, assigned);
        
        assigned = assigner.next();
        Assert.assertEquals(pick0, assigned);
        
        assigned = assigner.next();
        Assert.assertEquals(pick1, assigned);
    }

    @Test
    public void nextShouldAssignPickToCorrectPicker() {
        assigner.next();
        verify(bidder0, times(1)).assignPick(pick2);
        verify(bidder1, never()).assignPick(pick2);

        assigner.next();
        verify(bidder0, times(1)).assignPick(pick0);
        verify(bidder1, never()).assignPick(pick0);

        assigner.next();
        verify(bidder0, never()).assignPick(pick1);
        verify(bidder1, times(1)).assignPick(pick1);
    }

    @Test
    public void hasNextShouldBeTrueWhenThereArePicks() {
        Assert.assertTrue(assigner.hasNext());
    }

    @Test
    public void hasNextShouldBeFalseWhenThereAreNoPicks() {
        assigner.next();
        assigner.next();
        Assert.assertTrue(assigner.hasNext());
        assigner.next();
        Assert.assertFalse(assigner.hasNext());
    }

}
