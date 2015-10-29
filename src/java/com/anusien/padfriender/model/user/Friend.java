package com.anusien.padfriender.model.user;

import javax.annotation.Nonnull;

public class Friend {
    @Nonnull private final User user;
    private final boolean bestFriend;

    public Friend(@Nonnull final User user, final boolean bestFriend) {
        this.user = user;
        this.bestFriend = bestFriend;
    }
}
