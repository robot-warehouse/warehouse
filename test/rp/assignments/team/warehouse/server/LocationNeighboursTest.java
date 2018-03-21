package rp.assignments.team.warehouse.server;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.hasItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class LocationNeighboursTest {

    @Parameterized.Parameters
    public static Collection testCases() {
        return Arrays.asList(new Location[][][] {
            { { new Location(0, 0) }, { new Location(0, 1), new Location(1, 0) } },
            { { new Location(0, 1) }, { new Location(0, 2), new Location(1, 1), new Location(0, 0) } },
            { { new Location(1, 0) }, { new Location(1, 1), new Location(2, 0), new Location(0, 0) } },
            { { new Location(2, 2) }, { new Location(2, 3), new Location(3, 2), new Location(2, 1), new Location(1, 2) } },
            { { new Location(Location.MAX_X, Location.MAX_Y) }, { new Location(Location.MAX_X, Location.MAX_Y-1), new Location(Location.MAX_X-1, Location.MAX_Y) } },
            { { new Location(Location.MAX_X-1, Location.MAX_Y) }, { new Location(Location.MAX_X, Location.MAX_Y), new Location(Location.MAX_X-1, Location.MAX_Y-1), new Location(Location.MAX_X-2, Location.MAX_Y) } },
            { { new Location(Location.MAX_X, Location.MAX_Y-1) }, { new Location(Location.MAX_X, Location.MAX_Y), new Location(Location.MAX_X, Location.MAX_Y-2), new Location(Location.MAX_X-1, Location.MAX_Y-1) } },
        });
    }

    private Location location;
    private Location[] neighbours;

    public LocationNeighboursTest(Location[] input, Location[] expected) {
        this.location = input[0];
        this.neighbours = expected;
    }
    
    @Test
    public void neighboursAreCorrect() {
        List<Location> actual = new ArrayList<Location>();
        for (Location l : location.getNeighbours()) {
            if (l != null) {
                actual.add(l);
            }
        }
        
        Assert.assertFalse(actual.contains(null));
        Assert.assertThat(actual, hasItems(neighbours));
    }

}
