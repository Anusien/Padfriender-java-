package com.anusien.padfriender.model.monster;

import com.anusien.padfriender.model.user.UserId;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;

public class UserMonster {
    @Nonnull private final UserId owner;
    @Nonnull private final Date seen;
    @Nonnull private final Date updated;
    @Nullable private final Date updatedByOwner;

    @Nonnull private final Monster monster;
    private final int awakenings;
    private final int plusHp;
    private final int plusAtk;
    private final int plusRcv;
    
    public UserMonster(@Nonnull final Monster monster, @Nonnull final UserId owner, final int awakenings,
                       final int plusHp, final int plusAtk, final int plusRcv) {
        this(new Date(), new Date(), null, monster, owner, awakenings, plusHp, plusAtk, plusRcv);
    }

    public UserMonster(@Nonnull final Date seen, @Nonnull final Date updated, @Nullable final Date updatedByOwner,
                       @Nonnull final Monster monster, @Nonnull final UserId owner, final int awakenings,
                       final int plusHp, final int plusAtk, final int plusRcv) {
        this.seen = seen;
        this.updated = updated;
        this.updatedByOwner = updatedByOwner;
        this.monster = monster;
        this.owner = owner;
        this.awakenings = awakenings;
        this.plusHp = plusHp;
        this.plusAtk = plusAtk;
        this.plusRcv = plusRcv;
    }

    @Nonnull
    public UserId getOwner() {
        return owner;
    }
}
