package rp.assignments.team.warehouse.server.job.comparators;

import static org.mockito.Mockito.*;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

import rp.assignments.team.warehouse.server.job.IIDed;

public class CompareByIdComparatorTest {
    
    private Comparator<IIDed> comparator = new CompareByIdComparator();
    
    private static IIDed a = mock(IIDed.class);
    private static IIDed b = mock(IIDed.class);
    private static IIDed c = mock(IIDed.class);
    
    static {
        when(a.getId()).thenReturn(10);
        when(b.getId()).thenReturn(20);
        when(c.getId()).thenReturn(10);
    }
    
    @Test
    public void shouldGiveNegativeIfFirstIdLower() {
        Assert.assertTrue(comparator.compare(a, b) < 0);
    }
    
    @Test
    public void shouldGivePositiveIfFirstIdHigher() {
        Assert.assertTrue(comparator.compare(b, a) > 0);
    }
    
    @Test
    public void shouldGiveZeroForEqualIds() {
        Assert.assertTrue(comparator.compare(a, c) == 0);
    }
    
}
