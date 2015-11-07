package com.anusien.padfriender.model.user;

import com.anusien.padfriender.model.monster.UserMonster;

import javax.annotation.Nonnull;
import java.util.Set;

public class User {
    private final int id;
    @Nonnull private final UserId userid;
    @Nonnull private final String email;
    @Nonnull private final String name;
    @Nonnull private final Set<UserMonster> monsters;
    @Nonnull private final Set<User> friends;

    public User(@Nonnull final UserId userid, @Nonnull final String email, @Nonnull final String name,
                @Nonnull final Set<UserMonster> monsters, @Nonnull final Set<User> friends) {
        this(userid, email, name, monsters, friends, 0);
    }

    public User(@Nonnull final UserId userid, @Nonnull final String email, @Nonnull final String name,
                @Nonnull final Set<UserMonster> monsters, @Nonnull final Set<User> friends, final int id) {
        this.userid = userid;
        this.email = email;
        this.name = name;
        this.monsters = monsters;
        this.friends = friends;

        this.id = id;
    }
}
