package com.anusien.padfriender.persistence.user;

import com.anusien.padfriender.model.monster.Monster;
import com.anusien.padfriender.model.monster.UserMonster;
import com.anusien.padfriender.model.user.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class UserMonsterDAO {
    private static final String GET_FRIENDS_SQL =
            "SELECT user_monsters.*, friend.friendcode FROM users " +
            "INNER JOIN user_friends1 ON user_friends.user1 = users.id " +
            "INNER JOIN user_friends2 ON user_friends.user2 = users.id " +
            "INNER JOIN user_monsters ON " +
            "   (user_monsters.user_id = user_friends1.user_2 OR user_monsters.user_id = user_friends2.user_1) " +
            "INNER JOIN users AS friend ON friend.id = user_monsters.friend_id " +
            "WHERE users.email=?";

    @Nonnull private final DataSource connection;
    @Nonnull private final UserMonsterRowMapper mapper;

    @Autowired
    public UserMonsterDAO(@Nonnull final DataSource connection, @Qualifier("monsterList")final Map<Integer, Monster> monsters) {
        this.connection = connection;

        mapper = new UserMonsterRowMapper(monsters);
    }

    public Collection<UserMonster> getFriendMonsters(@Nonnull final String email) {
        final JdbcTemplate template = new JdbcTemplate(connection);
        final Collection<UserMonster> monsters = template.query(GET_FRIENDS_SQL, new Object[]{email}, mapper);
        return monsters;
    }

    public Map<String, Set<UserMonster>> getGroupedFriendMonsters(@Nonnull final String email) {
        final Collection<UserMonster> monsters = getFriendMonsters(email);
        final HashMap<String, Set<UserMonster>> sortedMonsters = new HashMap<>();
        for(final UserMonster monster : monsters) {
            final String userId = monster.getOwner().toString();
            if(!sortedMonsters.containsKey(userId)) {
                sortedMonsters.put(userId, new HashSet<UserMonster>());
            }
            sortedMonsters.get(userId).add(monster);
        }
        return sortedMonsters;
    }

    private class UserMonsterRowMapper implements RowMapper<UserMonster> {
        final Map<Integer, Monster> monsters;

        public UserMonsterRowMapper(final Map<Integer, Monster> monsters) {
            this.monsters = monsters;
        }

        @Override
        public UserMonster mapRow(final ResultSet rs, final int i) throws SQLException {
            final int monsterId = rs.getInt("monster_id");
            final Monster monster = monsters.get(monsterId);

            if (monster == null) {
                return null;
            }

            final Date seen = rs.getDate("seen");
            final Date updated = rs.getDate("updated");
            final Date updatedByOwner = rs.getDate("updated_by_owner");

            final String ownerCode = rs.getString("friendcode");
            final UserId ownerId = new UserId(ownerCode);

            final int awakenings = rs.getInt("awakenings");
            final int plusHp = rs.getInt("plus_hp");
            final int plusAtk = rs.getInt("plus_atk");
            final int plusRcv = rs.getInt("plus_rcv");

            return new UserMonster(seen, updated, updatedByOwner, monster, ownerId, awakenings, plusHp, plusAtk, plusRcv);
        }
    }
}
