package com.anusien.padfriender.config;

import com.anusien.padfriender.model.monster.Monster;
import com.anusien.padfriender.persistence.monster.MonsterJsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Map;

@Component
public class AppConfig {

    @Bean(name = "monsterList")
    @Autowired
    public Map<Integer, Monster> getMonsters(@Nonnull final MonsterJsonParser parser) {
        return parser.getMonsters();
    }
}
