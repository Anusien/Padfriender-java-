package com.anusien.padfriender.model.monster;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class MonsterJsonParser {
    private static final Logger logger = Logger.getLogger(MonsterJsonParser.class);

    @Nonnull private final MonsterJsonRetriever retriever;

    private static class JsonMonster {
        @JsonProperty("id")
        public int id;

        @JsonProperty("us_id")
        public Integer usId;

        @JsonProperty("pdx_id")
        public Integer pdxId;

        @JsonProperty("name")
        public String name;

        @JsonProperty("image60_href")
        public String imagePath;

        @JsonProperty("element")
        public Integer primaryElementId;

        @JsonProperty("element2")
        public Integer secondaryElementId;

        @JsonProperty("type")
        private Integer primaryTypeId;

        @JsonProperty("type2")
        public Integer secondaryTypeId;

        @JsonProperty("type3")
        public Integer tertiaryTypeId;

        @JsonProperty("awoken_skills")
        public int[] awakenings;

        @JsonProperty("name_jp")
        public String jpName;

        @JsonProperty("jp_only)")
        public boolean jpOnly;

        @Nullable
        public Monster convert() {
            final Monster.Element primaryElement = Monster.Element.getElement(primaryElementId);
            final Monster.Element secondaryElement = Monster.Element.getElement(secondaryElementId);
            final Monster.Type primaryType  = Monster.Type.getType(primaryTypeId);
            final Monster.Type secondaryType  = Monster.Type.getType(secondaryTypeId);
            final Monster.Type tertiaryType  = Monster.Type.getType(tertiaryTypeId);

            int numAwakenings = awakenings == null ? 0 : awakenings.length;

            if(id == -1 || name == null || jpName == null || imagePath == null || primaryElement == null
                    || primaryType == null) {
                return null;
            }

            return new Monster(id, usId != null ? usId : id, pdxId != null ? pdxId : id, name, jpName, imagePath,
                    primaryElement, secondaryElement,
                    primaryType, secondaryType,
                    tertiaryType, numAwakenings, jpOnly);
        }
    }

    public MonsterJsonParser(@Nonnull final MonsterJsonRetriever retriever) {
        this.retriever = Preconditions.checkNotNull(retriever);
    }

    @Nonnull
    public Map<Integer, Monster> getMonsters() {
        final String json = retriever.getJson();

        final ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        try {
            final List<JsonMonster> jsonMonsters =  mapper.readValue(json,
                    new TypeReference<List<JsonMonster>>() { } );
            final Map<Integer, Monster> monsters = new HashMap<>();
            for(final JsonMonster jsonMonster : jsonMonsters) {
                monsters.put(jsonMonster.id, jsonMonster.convert());
            }

            return monsters;

        } catch (IOException e) {
            logger.error("Exception trying to parse monsters from Json", e);
            return Collections.emptyMap();
        }
    }


}
