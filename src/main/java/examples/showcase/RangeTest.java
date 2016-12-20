package examples.showcase;

import org.junit.Assert;
import org.junit.Test;

public class RangeTest {

    @Test
    public void guaveRange() {
        com.google.common.collect.Range<Integer> range = com.google.common.collect.Range.closed(10, 20);
        Assert.assertEquals(range.contains(15), true);
        Assert.assertEquals(range.contains(34), false);
    }

    @Test
    public void commonsRange() {
        org.apache.commons.lang3.Range<Integer> range = org.apache.commons.lang3.Range.between(10, 20);
        Assert.assertEquals(range.contains(15), true);
        Assert.assertEquals(range.contains(34), false);
    }
}
