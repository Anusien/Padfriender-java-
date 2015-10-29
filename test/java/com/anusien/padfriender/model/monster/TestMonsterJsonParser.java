package com.anusien.padfriender.model.monster;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class TestMonsterJsonParser {

    @Test
    public void testBaseCase() {
        final String json = "[{\"element\":0,\"element2\":null,\"image60_size\":12301,\"name\":\"Tyrra\"," +
                "\"max_level\":5,\"awoken_skills\":[]," +
                "\"image60_href\":\"/static/img/monsters/60x60/1.55abb441ae5e.png\",\"rarity\":2," +
                "\"hp_max\":144,\"rcv_min\":8,\"rcv_max\":13,\"rcv_scale\":1.0,\"atk_scale\":1.0,\"id\":1," +
                "\"type3\":null,\"type2\":null,\"image40_href\":\"/static/img/monsters/40x40/1.b05c24c06800.png\"," +
                "\"hp_scale\":1.0,\"xp_curve\":1500000,\"leader_skill\":\"Fire Power\",\"team_cost\":2,\"type\":4," +
                "\"hp_min\":52,\"name_jp\":\"\\u63d0\\u62c9\",\"image40_size\":6826,\"active_skill\":\"Heat Breath\"," +
                "\"version\":186,\"atk_min\":57,\"feed_xp\":100.0,\"atk_max\":71,\"jp_only\":false}]";
        final Monster reference = new Monster(1, 1, 1, "Tyrra", "\u63d0\u62c9",
                "/static/img/monsters/60x60/1.55abb441ae5e.png",
                Monster.Element.Fire, null, Monster.Type.Dragon, null, null, 0, false);

        final MonsterJsonRetriever retriever = new FriendlyMonsterJsonRetriever(json);
        final MonsterJsonParser parser = new MonsterJsonParser(retriever);

        final Map<Integer, Monster> monsters = parser.getMonsters();
        Assert.assertNotNull(monsters);
        Assert.assertEquals(1, monsters.size());
        Assert.assertTrue(monsters.containsKey(1));
        Assert.assertTrue(reference.equals(monsters.get(1)));
    }
}
