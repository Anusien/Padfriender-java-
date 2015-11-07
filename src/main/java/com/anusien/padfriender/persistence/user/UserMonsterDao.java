package com.anusien.padfriender.persistence.user;

import com.anusien.padfriender.model.monster.Monster;
import com.anusien.padfriender.model.monster.UserMonster;
import com.anusien.padfriender.model.user.UserId;
import com.anusien.padfriender.persistence.monster.MonsterListProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.*;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class UserMonsterDao {

    @Nonnull private final DataSource connection;
    @Nonnull private final MonsterListProvider monsterProvider;

    @Autowired
    public UserMonsterDao(@Nonnull final DataSource connection, @Nonnull final MonsterListProvider monsterProvider) {
        this.connection = connection;
        this.monsterProvider = monsterProvider;
    }

    public Collection<UserMonster> getFriendMonsters(@Nonnull final String email) {
        final JdbcTemplate template = new JdbcTemplate(connection);
        final Collection<UserMonster> monsters = template.query(GET_FRIENDS_SQL, new Object[]{email},
                new UserMonsterRowMapper(monsterProvider.getMonsters()));
        return monsters;
    }

    private static final String GET_MONSTER_SQL =
            "SELECT user_monsters.* FROM users_monsters " +
            "INNER JOIN user_monsters ON user_monsters.user_id = users.id " +
            "WHERE users.friendcode = ? AND user_monsters.monster_id = ?";
    @Nullable
    public UserMonster getMonsterByUserAndId(@Nonnull final UserId id, final int monsterId) {
        final JdbcTemplate template = new JdbcTemplate(connection);
        final List<UserMonster> monsterList = template.query(GET_MONSTER_SQL,
                new Object[]{id.toString(), monsterId}, new UserMonsterRowMapper(monsterProvider.getMonsters()));
        if(monsterList == null || monsterList.isEmpty()) {
            return null;
        }
        return monsterList.get(0);
    }

    private static final String GET_FRIENDS_SQL =
            "SELECT user_monsters.*, friend.friendcode FROM users " +
            "INNER JOIN user_friends1 ON user_friends.user1 = users.id " +
            "INNER JOIN user_friends2 ON user_friends.user2 = users.id " +
            "INNER JOIN user_monsters ON " +
            "   (user_monsters.user_id = user_friends1.user_2 OR user_monsters.user_id = user_friends2.user_1) " +
            "INNER JOIN users AS friend ON friend.id = user_monsters.friend_id " +
            "WHERE users.email=?";
    @Nonnull
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

    public void saveMonster(@Nonnull final UserMonster monster) {
        if(monster.getId() == 0) {
            insertMonster(monster);
        } else {
            updateMonster(monster);
        }
    }

    private static final String INSERT_MONSTER_SQL =
            "INSERT INTO user_monsters(user_id, monster_id, seen, updated, updated_by_owner, awakenings, plus_hp, " +
            "plus_atk, plus_rcv) SELECT user_id, ?, ?, ?, ?, ?, ?, ?, ? FROM users WHERE users.friendcode = ?";
    private void insertMonster(@Nonnull final UserMonster monster) {
        final JdbcTemplate template = new JdbcTemplate(connection);
        template.update(INSERT_MONSTER_SQL, monster.getMonster().getId(), monster.getSeen(), monster.getUpdated(),
                monster.getUpdatedByOwner(), monster.getAwakenings(), monster.getPlusHp(), monster.getPlusAtk(),
                monster.getPlusRcv(), monster.getOwner().toString());
    }

    private static final String UPDATE_MONSTER_SQL =
            "UPDATE user_monsters SET seen = ?, updated = ?, updated_by_owner = ?, awakenings = ?, plus_hp = ?, " +
            "plus_atk = ?, plus_rcv = ?) WHERE user_monsters.id = ?";
    private void updateMonster(@Nonnull final UserMonster monster) {
        final JdbcTemplate template = new JdbcTemplate(connection);
        template.update(UPDATE_MONSTER_SQL, monster.getSeen(), monster.getUpdated(), monster.getUpdatedByOwner(),
                monster.getAwakenings(), monster.getPlusHp(), monster.getPlusAtk(), monster.getPlusRcv(),
                monster.getOwner().toString());

    }

    private class UserMonsterRowMapper implements RowMapper<UserMonster> {
        final Map<Integer, Monster> monsters;

        public UserMonsterRowMapper(final Map<Integer, Monster> monsters) {
            this.monsters = monsters;
        }

        @Override
        public UserMonster mapRow(final ResultSet rs, final int i) throws SQLException {
            final int id = rs.getInt("id");

            final int monsterId = rs.getInt("monster_id");
            final Monster monster = monsters.get(monsterId);

            if (monster == null) {
                return null;
            }

            final Date seen = rs.getDate("seen");
            final Date updated = rs.getDate("updated");
            final Date updatedByOwner = rs.getDate("updated_by_owner");

            final String ownerCode = rs.getString("friendcode");
            final UserId ownerId = UserId.getUserIdFromString(ownerCode);

            final int awakenings = rs.getInt("awakenings");
            final int plusHp = rs.getInt("plus_hp");
            final int plusAtk = rs.getInt("plus_atk");
            final int plusRcv = rs.getInt("plus_rcv");

            return new UserMonster(id, seen, updated, updatedByOwner, monster, ownerId, awakenings, plusHp, plusAtk, plusRcv);
        }
    }
}
