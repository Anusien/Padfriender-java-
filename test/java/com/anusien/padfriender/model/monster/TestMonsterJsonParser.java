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
        final Monster reference = new Monster(1, 1, 1, 2, "Tyrra", "\u63d0\u62c9",
                "/static/img/monsters/60x60/1.55abb441ae5e.png",
                Monster.Element.Fire, null, Monster.Type.Dragon, null, null, 0, 5, false);

        final MonsterJsonRetriever retriever = new FriendlyMonsterJsonRetriever(json);
        final MonsterJsonParser parser = new MonsterJsonParser(retriever);

        final Map<Integer, Monster> monsters = parser.getMonsters();
        Assert.assertNotNull(monsters);
        Assert.assertEquals(1, monsters.size());
        Assert.assertTrue(monsters.containsKey(reference.getId()));
        Assert.assertTrue(reference.equals(monsters.get(reference.getId())));
    }

    @Test
    public void testAwokenSkills() {
        final String json = "[{\"element\":0,\"element2\":2,\"image60_size\":8201," +
                "\"name\":\"Flame-Winged CyberBeast, Markab\",\"max_level\":99,\"awoken_skills\":[10,10,21,27,27]," +
                "\"image60_href\":\"/static/img/monsters/60x60/2190.6cf4171e2303.png\",\"rarity\":5,\"hp_max\":3024," +
                "\"rcv_min\":77,\"rcv_max\":254,\"rcv_scale\":1.5,\"atk_scale\":1.5,\"id\":2190,\"type3\":null," +
                "\"type2\":5,\"image40_href\":\"/static/img/monsters/40x40/2190.490fc504971a.png\",\"hp_scale\":1.5," +
                "\"xp_curve\":5000000,\"leader_skill\":\"Ruby Converter\",\"team_cost\":25,\"type\":8,\"hp_min\":672," +
                "\"name_jp\":\"\\u707c\\u7ffc\\u6a5f\\u5c0e\\u7378\\u30fb\\u5ba4\\u5bbf\\u4e00\"," +
                "\"image40_size\":4227,\"active_skill\":\"Blast Signal\",\"version\":186,\"atk_min\":419," +
                "\"feed_xp\":1125.0,\"atk_max\":1467,\"jp_only\":false}]";
        final Monster reference = new Monster(2190, 2190, 2190, 5, "Flame-Winged CyberBeast, Markab",
                "\u707c\u7ffc\u6a5f\u5c0e\u7378\u30fb\u5ba4\u5bbf\u4e00",
                "/static/img/monsters/60x60/2190.6cf4171e2303.png",
                Monster.Element.Fire, Monster.Element.Wood,
                Monster.Type.Machine, Monster.Type.God, null, 5, 99, false);

        final MonsterJsonRetriever retriever = new FriendlyMonsterJsonRetriever(json);
        final MonsterJsonParser parser = new MonsterJsonParser(retriever);

        final Map<Integer, Monster> monsters = parser.getMonsters();
        Assert.assertNotNull(monsters);
        Assert.assertEquals(1, monsters.size());
        Assert.assertTrue(monsters.containsKey(reference.getId()));
        Assert.assertTrue(reference.equals(monsters.get(reference.getId())));
    }

    @Test
    public void canParseAllMonsters() {
        final MonsterJsonParser parser = new MonsterJsonParser(new LocalMonsterJsonRetriever());
        final Map<Integer, Monster> monsters = parser.getMonsters();

        Assert.assertNotNull(monsters);
    }
}
