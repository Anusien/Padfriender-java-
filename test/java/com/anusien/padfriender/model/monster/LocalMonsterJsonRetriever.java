package com.anusien.padfriender.model.monster;

import com.anusien.padfriender.persistence.monster.MonsterJsonRetriever;

import javax.annotation.Nullable;

public class LocalMonsterJsonRetriever extends MonsterJsonRetriever {

    @Nullable
    @Override
    public String getJson() {
        return this.getJsonFromCachedFile();
    }
}
