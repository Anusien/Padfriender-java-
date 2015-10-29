package com.anusien.padfriender.model.monster;

import com.anusien.padfriender.util.IntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TestMonsterJsonRetriever {

    @Test
    @Category(IntegrationTest.class)
    public void canFindFromFile() {
        final String json = new MonsterJsonRetriever().getJsonFromCachedFile();
        Assert.assertNotNull(json);
    }

    @Test
    public void canFindFromUrl() {
        final String json = new MonsterJsonRetriever().getJsonFromUrl(MonsterJsonRetriever.JsonURL);
        Assert.assertNotNull(json);
    }
}
