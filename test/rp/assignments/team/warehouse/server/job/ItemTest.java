package rp.assignments.team.warehouse.server.job;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import rp.assignments.team.warehouse.server.Location;

@RunWith(Enclosed.class)
public class ItemTest {

    public static class ItemInstantiationTests {

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
        }

        @Test(expected = AssertionError.class)
        public void cannotCreateItemWithNaNReward() {
            Item item = new Item(5, Float.NaN, 15.0f, location);
        }

        @Test(expected = AssertionError.class)
        public void cannotCreateItemWithNaNWeight() {
            Item item = new Item(5, 10.0f, Float.NaN, location);
        }

        @Test(expected = AssertionError.class)
        public void cannotCreateItemWithNegativeWeight() {
            Item item = new Item(5, 10.0f, -15.0f, location);
        }

        @Test(expected = AssertionError.class)
        public void cannotCreateItemWithNullLocation() {
            Item item = new Item(5, 10.0f, 15.0f, null);
        }

    }

    @RunWith(Parameterized.class)
    public static class ItemIdParsingTests {

        @Parameters(name = "{index}: {0}={1}")
        public static Iterable<Object[]> data() {
            return Arrays.asList(new Object[][] { 
                { 0, "aa" }, { 1, "ab" }, { 25, "az" }, { 26, "ba" }, { 28, "bc" }, {675 , "zz" }
          });
        }

        private int numericId;
        private String stringId;

        public ItemIdParsingTests(int numericId, String stringId) {
            this.numericId = numericId;
            this.stringId = stringId;
        }

        @Test
        public void parseIdOfStringShouldBeCorrect() {
            Assert.assertEquals(numericId, Item.parseId(stringId));
        }

        @Test
        public void numericIdToStringOfNumericShouldBeCorrect() {
            Assert.assertEquals(stringId, Item.numericIdToString(numericId));
        }

        @Test
        public void parseIdOfNumericIdToStringShouldBeTheSameAsNumeric() {
            Assert.assertEquals(numericId, Item.parseId(Item.numericIdToString(numericId)));
        }

        @Test
        public void numericIdToStringOfParseIdShouldBeTheSameAsString() {
            Assert.assertEquals(stringId, Item.numericIdToString(Item.parseId(stringId)));
        }

    }

}
