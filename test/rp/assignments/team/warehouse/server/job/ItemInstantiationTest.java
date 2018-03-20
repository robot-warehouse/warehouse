package rp.assignments.team.warehouse.server.job;

import org.junit.Assert;
import org.junit.Test;

import rp.assignments.team.warehouse.server.Location;

public class ItemInstantiationTest {

    private static Location location = new Location(0, 0);

    @Test
    public void canCreateItemWithValidProperties() {
        Item item = new Item(5, 10.0f, 15.0f, location);
        Assert.assertNotNull(item);
        Assert.assertEquals(item.getId(), 5);
        Assert.assertEquals(item.getReward(), 10.0f, 0.0f);
        Assert.assertEquals(item.getWeight(), 15.0f, 0.0f);
        Assert.assertEquals(item.getLocation(), location);
    }

    @Test(expected = AssertionError.class)
    public void cannotCreateItemWithNegativeId() {
        Item item = new Item(-5, 10.0f, 15.0f, location);
        Assert.assertNull(item);
    }

    @Test(expected = AssertionError.class)
    public void cannotCreateItemWithNaNReward() {
        Item item = new Item(5, Float.NaN, 15.0f, location);
        Assert.assertNull(item);
    }

    @Test(expected = AssertionError.class)
    public void cannotCreateItemWithNaNWeight() {
        Item item = new Item(5, 10.0f, Float.NaN, location);
        Assert.assertNull(item);
    }

    @Test(expected = AssertionError.class)
    public void cannotCreateItemWithNegativeWeight() {
        Item item = new Item(5, 10.0f, -15.0f, location);
        Assert.assertNull(item);
    }

    @Test(expected = AssertionError.class)
    public void cannotCreateItemWithNullLocation() {
        Item item = new Item(5, 10.0f, 15.0f, null);
        Assert.assertNull(item);
    }

}
