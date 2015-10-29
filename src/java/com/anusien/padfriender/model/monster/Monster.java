package com.anusien.padfriender.model.monster;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Monster implements Comparable<Monster> {
    @Override
    public int compareTo(@Nonnull final Monster o) {
        return new Integer(this.id).compareTo(o.id);
    }

    @Override
    public boolean equals(@Nonnull final Object o) {
        if(!(o instanceof Monster)) {
            return false;
        }
        final Monster other = (Monster) o;

        return new EqualsBuilder().append(this.id, other.id).append(this.usId, other.usId)
                .append(this.pdxId, other.pdxId).append(this.rarity, other.rarity)
                .append(this.name, other.name).append(this.jpName, other.jpName)
                .append(this.imagePath, other.imagePath).append(this.primaryElement,other.primaryElement)
                .append(this.secondaryElement, other.secondaryElement).append(this.primaryType, other.primaryType)
                .append(this.secondaryType, other.secondaryType).append(this.tertiaryType, other.tertiaryType)
                .append(this.numAwakenings, other.numAwakenings).append(this.maxLevel, other.maxLevel)
                .append(this.jpOnly, other.jpOnly).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(109, 53).append(id).append(usId).append(pdxId).append(rarity)
                .append(name).append(jpName)
                .append(imagePath).append(primaryElement)
                .append(secondaryElement).append(primaryType)
                .append(secondaryType).append(tertiaryType)
                .append(numAwakenings).append(maxLevel).append(jpOnly).toHashCode();
    }

    public enum Element {
        /* These intentionally match the values from Padherder */
        Fire(0),
        Water(1),
        Wood(2),
        Light(3),
        Dark(4);

        private final int id;
        Element(int id) {
            this.id = id;
        }
        public int getValue() { return id; }

        @Nullable
        public static Element getElement(final Integer id) {
            if(id == null) {
                return null;
            }
            return getElement(id.intValue());
        }

        @Nullable
        public static Element getElement(final int id)
        {
            final Element[] elements = Element.values();
            for(int i = 0; i < elements.length; i++)
            {
                if(elements[i].getValue() == id) {
                    return elements[i];
                }
            }
            return null;
        }
    }

    public enum Type {
        /* These intentionally match the values from Padherder */
        EvoMaterial(0),
        Balanced(1),
        Physical(2),
        Healer(3),
        Dragon(4),
        God(5),
        Attacker(6),
        Devil(7),
        Machine(8),
        AwokenSkill(12),
        Protected(13),
        EnhanceMaterial(14),
        Vendor(15),
        NONE(-1);

        private final int id;
        Type(final int id) {
            this.id = id;
        }

        public int getValue() { return id; }

        @Nullable
        public static Type getType(final Integer id) {
            if(id == null) {
                return null;
            }
            return getType(id.intValue());
        }

        @Nullable
        public static Type getType(final int id)
        {
            final Type[] types = Type.values();
            for(int i = 0; i < types.length; i++)
            {
                if(types[i].getValue() == id) {
                    return types[i];
                }
            }
            return null;
        }
    }

    private final int id;
    private final int usId;
    private final int pdxId;
    private final int rarity;
    @Nonnull private final String name;
    @Nonnull private final String jpName;
    private final String imagePath;
    @Nonnull private final Element primaryElement;
    @Nullable private final Element secondaryElement;
    @Nonnull private final Type primaryType;
    @Nullable private final Type secondaryType;
    @Nullable private final Type tertiaryType;
    private final int maxLevel;
    private final int numAwakenings;
    private final boolean jpOnly;

    public Monster(final int id, final int usId, final int pdxId, final int rarity, @Nonnull final String name,
                   @Nonnull final String jpName, @Nonnull final String imagePath,
                   @Nonnull final Element primaryElement, @Nullable final Element secondaryElement,
                   @Nonnull final Type primaryType, @Nullable final Type secondaryType, @Nullable final Type tertiaryType,
                   final int numAwakenings, final int maxLevel, final boolean jpOnly) {

        Preconditions.checkArgument(id > 0);
        this.id = id;

        this.usId = usId;
        this.pdxId = pdxId;
        this.rarity = rarity;
        this.name = Preconditions.checkNotNull(name);
        this.jpName = Preconditions.checkNotNull(jpName);
        this.imagePath = Preconditions.checkNotNull(imagePath);
        this.primaryElement = Preconditions.checkNotNull(primaryElement);
        this.secondaryElement = secondaryElement;
        this.primaryType = Preconditions.checkNotNull(primaryType);
        this.secondaryType = secondaryType;
        this.tertiaryType = tertiaryType;
        this.maxLevel = maxLevel;
        this.numAwakenings = numAwakenings;
        this.jpOnly = jpOnly;
    }

    public int getId() {
        return id;
    }
}
