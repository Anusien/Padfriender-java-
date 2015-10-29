package com.anusien.padfriender.model.monster;

import javax.annotation.Nullable;

public class LocalMonsterJsonRetriever extends MonsterJsonRetriever {

    @Nullable
    @Override
    public String getJson() {
        return this.getJsonFromCachedFile();
    }
}
