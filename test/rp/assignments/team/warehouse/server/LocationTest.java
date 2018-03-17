package rp.assignments.team.warehouse.server;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Enclosed.class)
public class LocationTest {

    public static class LocationMethodTests {

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

    @RunWith(Parameterized.class)
    public static class ValidLocationInstantiationTests {

        @Parameters(name = "{index}: ({0}, {1})")
        public static Iterable<Object[]> data() {
            return Arrays.asList(new Object[][] {
                { Location.MIN_X, Location.MIN_Y }, { Location.MIN_X, Location.MIN_Y+1 }, { Location.MIN_X+1, Location.MIN_Y }, { Location.MIN_X+1, Location.MIN_Y+1 }, { Location.MIN_X, Location.MAX_Y }, { Location.MAX_X, Location.MIN_Y }, { Location.MAX_X, Location.MAX_Y }
          });
        }

        private int x;
        private int y;

        public ValidLocationInstantiationTests(int x, int y) {
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

    @RunWith(Parameterized.class)
    public static class InvalidLocationInstantiationTests {

        @Parameters(name = "{index}: ({0}, {1})")
        public static Iterable<Object[]> data() {
            return Arrays.asList(new Object[][] {
                { Location.MIN_X-1, Location.MIN_Y-1 }, { Location.MIN_X, Location.MAX_Y+1 }, { Location.MAX_X+1, Location.MIN_Y }, { Location.MIN_X-1, Location.MAX_Y+1 }, { Location.MAX_X+1, Location.MIN_Y-1 }, { Location.MAX_X+1, Location.MAX_Y+1 }, { Integer.MIN_VALUE, -Integer.MIN_VALUE }, { Integer.MAX_VALUE, Integer.MAX_VALUE }
          });
        }

        private int x;
        private int y;

        public InvalidLocationInstantiationTests(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Test(expected=AssertionError.class)
        public void cannotInstantiateinvalidLocation() {
            Location location = new Location(x, y);
        }

        @Test
        public void isValidLocationOfInvalidLocationShouldBeTrue() {
            Assert.assertFalse(Location.isValidLocation(x, y));
        }

    }
}
