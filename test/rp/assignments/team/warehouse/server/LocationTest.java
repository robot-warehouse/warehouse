package rp.assignments.team.warehouse.server;

import org.junit.Assert;
import org.junit.Test;

public class LocationTest {

    private static Location l1v1 = new Location(2, 3);
    private static Location l1v2 = new Location(2, 3);
    private static Location l2v1 = new Location(1, 6);
    private static Location l2v2 = new Location(1, 6);
    private static Location l3 = new Location(0, 3);
    private static Location l4 = new Location(2, 0);
    private static Location l5 = new Location(0, 6);
    private static Location l6 = new Location(1, 0);

    @Test
    public void getCoordinatesShouldBeCoordinates() {
        Assert.assertEquals(2, l1v1.getX());
        Assert.assertEquals(3, l1v1.getY());
    }

    @Test
    public void equalsOfSameLocationShouldBeTrue() {
        Assert.assertTrue(l1v1.equals(l1v2));
        Assert.assertTrue(l1v2.equals(l1v1));

        Assert.assertTrue(l2v1.equals(l2v2));
        Assert.assertTrue(l2v2.equals(l2v1));
    }

    @Test
    public void equalsOfDifferentLocationShouldBeFalse() {
        Assert.assertFalse(l1v1.equals(l3));
        Assert.assertFalse(l1v1.equals(l4));
        Assert.assertFalse(l2v1.equals(l5));
        Assert.assertFalse(l2v1.equals(l6));

        Assert.assertFalse(l3.equals(l1v1));
        Assert.assertFalse(l4.equals(l1v1));
        Assert.assertFalse(l5.equals(l2v1));
        Assert.assertFalse(l6.equals(l2v1));
    }

    @Test
    public void equalsOfNullShouldBeFalse() {
        Assert.assertFalse(l1v1.equals(null));
    }

}
