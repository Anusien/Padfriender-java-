package com.anusien.padfriender.persistence.monster;

import com.anusien.padfriender.model.monster.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Map;

@Component
public class MonsterListProvider {
    @Nonnull final Map<Integer, Monster> monsters;

    @Autowired
    public MonsterListProvider(@Nonnull MonsterJsonParser parser) {
        monsters = parser.getMonsters();
    }

    @Nonnull
    public Map<Integer, Monster> getMonsters() {
        return this.monsters;
    }
}
