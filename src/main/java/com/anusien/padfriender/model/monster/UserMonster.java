package com.anusien.padfriender.model.monster;

import com.anusien.padfriender.model.user.UserId;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;

public class UserMonster {
    private final int id;
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
        this(0, new Date(), new Date(), null, monster, owner, awakenings, plusHp, plusAtk, plusRcv);
    }

    public UserMonster(final int id, @Nonnull final Date seen, @Nonnull final Date updated,
                       @Nullable final Date updatedByOwner, @Nonnull final Monster monster, @Nonnull final UserId owner,
                       final int awakenings, final int plusHp, final int plusAtk, final int plusRcv) {
        this.id = id;
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

    @Nonnull
    public static UserMonster merge(@Nullable final UserMonster oldMonster, @Nonnull final UserMonster newMonster,
                                    final boolean isOwner) {
        final Date now = new Date();
        final Date updatedByOwner;
        if(isOwner) {
            updatedByOwner = now;
        } else {
            updatedByOwner = oldMonster == null ? null : oldMonster.getUpdatedByOwner();
        }
        final int id;
        if(newMonster.getId() > 0) {
            id = newMonster.getId();
        } else {
            id = oldMonster != null ? oldMonster.getId() : 0;
        }

        return new UserMonster(id, now, now, updatedByOwner, newMonster.getMonster(), newMonster.getOwner(),
                newMonster.getAwakenings(), newMonster.getPlusHp(), newMonster.getPlusAtk(), newMonster.getPlusRcv());
    }

    @Nullable
    public Date getUpdatedByOwner() {
        return updatedByOwner;
    }

    public int getId() {
        return id;
    }

    public int getAwakenings() {
        return awakenings;
    }

    public int getPlusHp() {
        return plusHp;
    }

    public int getPlusAtk() {
        return plusAtk;
    }

    public int getPlusRcv() {
        return plusRcv;
    }

    @Nonnull
    public Monster getMonster() {
        return monster;
    }

    @Nonnull
    public Date getSeen() {
        return seen;
    }

    @Nonnull
    public Date getUpdated() {
        return updated;
    }
}
