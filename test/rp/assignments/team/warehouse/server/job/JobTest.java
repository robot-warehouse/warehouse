package rp.assignments.team.warehouse.server.job;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JobTest {

    private static Item mockedItema;

    private static JobItem mockedJobItem0;
    private static JobItem mockedJobItem1;
    private static JobItem mockedJobItem2;
    private static List<JobItem> jobItems;

    private Job job;

    @BeforeClass
    public static void setUpBeforeClass() {
        mockedItema = mock(Item.class);

        mockedJobItem0 = mock(JobItem.class);
        when(mockedJobItem0.getItem()).thenReturn(mockedItema);
        when(mockedJobItem0.getCount()).thenReturn(3);
        mockedJobItem1 = mock(JobItem.class);
        mockedJobItem2 = mock(JobItem.class);

        jobItems = new ArrayList<JobItem>() {{
                add(mockedJobItem0);
                add(mockedJobItem1);
                add(mockedJobItem2);
        }};
    }

    @Before
    public void setupBeforeEach() {
        job = new Job(10, jobItems);
    }

    @Test
    public void canGetIdOfJob() {
        Assert.assertEquals(10, job.getId());
    }

    @Test
    public void canGetItemsOfJob() {
        Assert.assertEquals(jobItems, job.getJobItems());
    }

    @Test
    public void canGetPickCountOfJob() {
        Assert.assertEquals(3, job.getPickCount());
    }

    @Test
    public void cancelledShouldBeFalseByDefault() {
        Assert.assertFalse(job.isCancelled());
    }

    @Test
    public void canSetJobAsCancelled() {
        Assert.assertFalse(job.isCancelled());
        job.setCancelled();
        Assert.assertTrue(job.isCancelled());
    }

    @Test
    public void previouslyCancelledShouldBeFalseByDefault() {
        Assert.assertFalse(job.isPreviouslyCancelled());
    }

    @Test
    public void canSetJobAsPreviously() {
        Assert.assertFalse(job.isPreviouslyCancelled());
        job.setPreviouslyCancelled();
        Assert.assertTrue(job.isPreviouslyCancelled());
    }

    @Test
    public void completedPickCountShouldBeZeroInitially() {
        Assert.assertEquals(0, job.getCompletedPickCount());
    }

    @Test
    public void completedPickCountIncrementsWhenPickCompleted() {
        Pick pick = job.getAvailablePicks().get(0);
        int picksCompleted = job.getCompletedPickCount();
        pick.setCompleted();
        Assert.assertEquals(picksCompleted + 1, job.getCompletedPickCount());
    }

    @Test(expected = AssertionError.class)
    public void cannotCompleteNullPick() {
        job.pickCompleted(null);
    }

    @Test(expected = AssertionError.class)
    public void cannotCompletePickWhichIsNotCompleted() {
        Pick pick = mock(Pick.class);
        when(pick.getJob()).thenReturn(job);
        when(pick.isCompleted()).thenReturn(false);
        job.pickCompleted(pick);
    }

    @Test(expected = AssertionError.class)
    public void cannotCompletePickWhichIsNotInJob() {
        Pick pick = mock(Pick.class);
        when(pick.getJob()).thenReturn(mock(Job.class));
        when(pick.isCompleted()).thenReturn(true);
        job.pickCompleted(pick);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotCompletePickWhichWasAlreadyCompleted() {
        Pick pick = job.getAvailablePicks().get(0);
        pick.setCompleted();
        job.pickCompleted(pick);
    }

    @Test
    public void jobIsCompleteWhenAllPicksAreCompleted() {
        Assert.assertFalse(job.isComplete());
        List<Pick> picks = new ArrayList<Pick>(job.getAvailablePicks());
        picks.forEach(p -> p.setCompleted());
        Assert.assertTrue(job.isComplete());
        Assert.assertEquals(3, job.getCompletedPickCount());
    }

    @Test
    public void hasAvailablePicksShouldBeInitiallyTrue() {
        Assert.assertTrue(job.hasAvailablePicks());
    }

    @Test
    public void hasAvailablePicksShouldBeFalseWhenCompleted() {
        Assert.assertTrue(job.hasAvailablePicks());
        List<Pick> picks = new ArrayList<Pick>(job.getAvailablePicks());
        picks.forEach(p -> p.setCompleted());
        Assert.assertTrue(job.isComplete());
        Assert.assertFalse(job.hasAvailablePicks());
    }

    // TODO test rewards and priorities

}
