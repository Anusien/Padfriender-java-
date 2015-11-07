package com.anusien.padfriender.model.user;

import org.junit.*;

public class TestUserId {
    static final String goodId = "123,456,789";

    @Test
    public void shouldToStringCorrectly() {
        final UserId id = new UserId(new int[] {1,2,3,4,5,6,7,8,9});
        Assert.assertEquals(goodId, id.toString());
    }

    @Test
    public void canParseWithCommas() {
        final String toParse = "123,456,789";
        final UserId id = UserId.getUserIdFromString(toParse);
        Assert.assertNotNull(id);
        Assert.assertEquals(goodId, id.toString());
    }

    @Test
    public void canParseWithSpaces() {
        final String toParse = "123 456 789";
        final UserId id = UserId.getUserIdFromString(toParse);
        Assert.assertNotNull(id);
        Assert.assertEquals(goodId, id.toString());
    }

    @Test
    public void tooShortReturnsNull() {
        final String toParse = "12345678";
        final UserId id = UserId.getUserIdFromString(toParse);
        Assert.assertNull(id);
    }

    @Test
    public void notNumbersReturnsNull() {
        final String toParse="ABCDEFGHI";
        final UserId id = UserId.getUserIdFromString(toParse);
        Assert.assertNull(id);
    }
}
