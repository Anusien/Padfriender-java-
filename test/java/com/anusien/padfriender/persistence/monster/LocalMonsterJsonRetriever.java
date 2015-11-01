package com.anusien.padfriender.persistence.monster;

import javax.annotation.Nullable;

public class LocalMonsterJsonRetriever extends MonsterJsonRetriever {

    @Nullable
    @Override
    public String getJson() {
        return this.getJsonFromCachedFile();
    }
}
