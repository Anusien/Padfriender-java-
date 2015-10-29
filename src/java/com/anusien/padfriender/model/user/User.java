package com.anusien.padfriender.model.user;

import javax.annotation.Nonnull;
import java.util.Set;

public class User {
    @Nonnull private final UserId id;
    @Nonnull private final String email;
    @Nonnull private final String passwordHash;
    @Nonnull private final Set<UserMonster> monsters;
    @Nonnull private final Set<User> friends;

    public User(@Nonnull final UserId id, @Nonnull final String email, @Nonnull final String passwordHash,
                @Nonnull final Set<UserMonster> monsters, @Nonnull final Set<User> friends) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.monsters = monsters;
        this.friends = friends;
    }
}
