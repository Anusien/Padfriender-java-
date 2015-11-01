package com.anusien.padfriender.persistence.monster;

import com.anusien.padfriender.persistence.monster.MonsterJsonRetriever;

import javax.annotation.Nullable;

public class FriendlyMonsterJsonRetriever extends MonsterJsonRetriever {
    @Nullable
    private final String json;

    public FriendlyMonsterJsonRetriever(@Nullable final String json) {
        this.json = json;
    }

    @Nullable
    @Override
    public String getJson() {
        return json;
    }
}
