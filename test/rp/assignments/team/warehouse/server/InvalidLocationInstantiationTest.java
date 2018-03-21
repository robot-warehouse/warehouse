package rp.assignments.team.warehouse.server;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class InvalidLocationInstantiationTest {

    @Parameters(name = "{index}: ({0}, {1})")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { Location.MIN_X-1, Location.MIN_Y-1 }, { Location.MIN_X, Location.MAX_Y+1 }, { Location.MAX_X+1, Location.MIN_Y }, { Location.MIN_X-1, Location.MAX_Y+1 }, { Location.MAX_X+1, Location.MIN_Y-1 }, { Location.MAX_X+1, Location.MAX_Y+1 }, { Integer.MIN_VALUE, -Integer.MIN_VALUE }, { Integer.MAX_VALUE, Integer.MAX_VALUE }
      });
    }

    private int x;
    private int y;

    public InvalidLocationInstantiationTest(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Test(expected=IllegalArgumentException.class)
    public void cannotInstantiateinvalidLocation() {
        Location location = new Location(x, y);
        Assert.assertNull(location);
    }

    @Test
    public void isValidLocationOfInvalidLocationShouldBeTrue() {
        Assert.assertFalse(Location.isValidLocation(x, y));
    }

}
