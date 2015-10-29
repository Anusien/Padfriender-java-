package com.anusien.padfriender.model.user;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Friend {
    @Nonnull private final User user;
    private final boolean bestFriend;
    @Nullable public String note;

    public Friend(@Nonnull final User user, final boolean bestFriend, @Nullable final String note) {
        this.user = user;
        this.bestFriend = bestFriend;
        this.note = note;
    }
}
