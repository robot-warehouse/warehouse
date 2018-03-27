package rp.assignments.team.warehouse.server.job.comparators;

import static org.mockito.Mockito.*;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

import rp.assignments.team.warehouse.server.job.IPrioritised;

public class CompareByPriorityComparatorTest {
    
    private Comparator<IPrioritised> comparator = new CompareByPriorityComparator();
    private Comparator<IPrioritised> comparatorAsc = new CompareByPriorityComparator(true);
    
    private static IPrioritised a = mock(IPrioritised.class);
    private static IPrioritised b = mock(IPrioritised.class);
    private static IPrioritised c = mock(IPrioritised.class);
    
    static {
        when(a.getPriority()).thenReturn(10.0f);
        when(b.getPriority()).thenReturn(20.0f);
        when(c.getPriority()).thenReturn(10.0f);
    }
    
    @Test
    public void shouldGivePositiveIfFirstPriorityLower() {
        Assert.assertTrue(comparator.compare(a, b) > 0);
    }
    
    @Test
    public void shouldGiveNegativeIfFirstPriorityHigher() {        
        Assert.assertTrue(comparator.compare(b, a) < 0);
    }
    
    @Test
    public void shouldGiveZeroForEqualPriorities() {        
        Assert.assertTrue(comparator.compare(a, c) == 0);
    }
    
    @Test
    public void shouldGiveNegativeIfFirstPriorityLowerIfAscending() {
        Assert.assertTrue(comparatorAsc.compare(a, b) < 0);
    }
    
    @Test
    public void shouldGivePositiveIfFirstPriorityHigherIfAscending() {        
        Assert.assertTrue(comparatorAsc.compare(b, a) > 0);
    }
    
}
