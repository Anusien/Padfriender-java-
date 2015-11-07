package com.anusien.padfriender.model.user;

import com.google.common.annotations.*;

import javax.annotation.*;

public class UserId {
    public static final int ID_LENGTH = 9;
    private final int id[];

    @VisibleForTesting
    protected UserId(final int[] id) {
        if(id.length != ID_LENGTH) {
            throw new IndexOutOfBoundsException();
        }
        this.id = id.clone();
    }

    @Nullable
    public static UserId getUserIdFromString(@Nullable final String id) {
        if(id == null || id.length() < ID_LENGTH) {
            return null;
        }
        final int[] numbers = new int[ID_LENGTH];

        int id_index = 0;
        for(int string_index = 0; string_index < id.length(); string_index++) {
            if(id_index == ID_LENGTH) {
                break;
            }

            if(id.length() < id_index) {
                return null;
            }

            if(id.charAt(string_index) == ',' || id.charAt(string_index) == ' ') {
                continue;
            }
            try {
                numbers[id_index] = Integer.parseInt("" + id.charAt(string_index));
            } catch(NumberFormatException e) {
                return null;
            }
            id_index++;
        }
        return new UserId(numbers);
    }

    public int[] getId() {
        return this.id;
    }

    @Nonnull
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                builder.append(id[3*i+j]);
            }
            if(i < 2) { // avoid last comma
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
