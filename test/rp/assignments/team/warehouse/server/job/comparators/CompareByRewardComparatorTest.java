package rp.assignments.team.warehouse.server.job.comparators;

import static org.mockito.Mockito.*;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

import rp.assignments.team.warehouse.server.job.IRewardable;

public class CompareByRewardComparatorTest {

    private Comparator<IRewardable> comparator = new CompareByRewardComparator();
    private Comparator<IRewardable> comparatorAsc = new CompareByRewardComparator(true);
    
    private static IRewardable a = mock(IRewardable.class);
    private static IRewardable b = mock(IRewardable.class);
    private static IRewardable c = mock(IRewardable.class);
    
    static {
        when(a.getReward()).thenReturn(10.0f);
        when(b.getReward()).thenReturn(20.0f);
        when(c.getReward()).thenReturn(10.0f);
    }
    
    @Test
    public void shouldGivePositiveIfFirstRewardLower() {
        Assert.assertTrue(comparator.compare(a, b) > 0);
    }
    
    @Test
    public void shouldGiveNegativeIfFirstRewardHigher() {        
        Assert.assertTrue(comparator.compare(b, a) < 0);
    }
    
    @Test
    public void shouldGiveZeroForEqualRewards() {        
        Assert.assertTrue(comparator.compare(a, c) == 0);
    }
    
    @Test
    public void shouldGiveNegativeIfFirstRewardLowerIfAscending() {
        Assert.assertTrue(comparatorAsc.compare(a, b) < 0);
    }
    
    @Test
    public void shouldGivePositiveIfFirstRewardHigherIfAscending() {        
        Assert.assertTrue(comparatorAsc.compare(b, a) > 0);
    }
    
}
