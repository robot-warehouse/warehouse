package rp.assignments.team.warehouse.server.job;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class ItemIdParsingTests {

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
