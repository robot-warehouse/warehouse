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
            Assert.assertFalse(l1v1.equals(l2v1));
            Assert.assertFalse(l2v1.equals(l1v1));
            
            Assert.assertFalse(l1v2.equals(l2v2));
            Assert.assertFalse(l2v2.equals(l1v2));
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
                { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 }, { Location.MAX_X, Location.MAX_Y }
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
                { -1, -1 }, { 0, Location.MAX_Y+1 }, { Location.MAX_X+1, 0 }, { Location.MAX_X+1, Location.MAX_Y+1 }, { Integer.MAX_VALUE, Integer.MAX_VALUE }
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
