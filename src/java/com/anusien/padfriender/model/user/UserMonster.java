package com.anusien.padfriender.model.user;

import com.anusien.padfriender.model.monster.Monster;

import javax.annotation.Nonnull;
import java.util.Date;

public class UserMonster {
    @Nonnull private final Date seen;
    @Nonnull private final Date updated;

    @Nonnull private final Monster monster;
    private final int awakenings;
    private final int plusHp;
    private final int plusAtk;
    private final int plusRcv;
    
    public UserMonster(@Nonnull final Monster monster, final int awakenings, final int plusHp, final int plusAtk,
                       final int plusRcv) {
        this(new Date(), new Date(), monster, awakenings, plusHp, plusAtk, plusRcv);
    }

    public UserMonster(@Nonnull final Date seen, @Nonnull final Date updated, @Nonnull final Monster monster,
                       final int awakenings, final int plusHp, final int plusAtk, final int plusRcv) {
        this.seen = seen;
        this.updated = updated;
        this.monster = monster;
        this.awakenings = awakenings;
        this.plusHp = plusHp;
        this.plusAtk = plusAtk;
        this.plusRcv = plusRcv;
    }
}
