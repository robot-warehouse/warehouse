package rp.assignments.team.warehouse.server;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ValidLocationInstantiationTest {

    @Parameters(name = "{index}: ({0}, {1})")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { Location.MIN_X, Location.MIN_Y }, { Location.MIN_X, Location.MIN_Y+1 }, { Location.MIN_X+1, Location.MIN_Y }, { Location.MIN_X+1, Location.MIN_Y+1 }, { Location.MIN_X, Location.MAX_Y }, { Location.MAX_X, Location.MIN_Y }, { Location.MAX_X, Location.MAX_Y }
      });
    }

    private int x;
    private int y;

    public ValidLocationInstantiationTest(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Test
    public void canInstantiateValidLocation() {
        Location location = new Location(x, y);
        Assert.assertNotNull(location);
    }

    @Test
    public void isValidLocationOfValidLocationShouldBeTrue() {
        Assert.assertTrue(Location.isValidLocation(x, y));
    }

}
