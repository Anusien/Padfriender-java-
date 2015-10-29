package com.anusien.padfriender.model.user;

public class UserId {
    public static final int ID_LENGTH = 9;
    private final int id[];

    public UserId(final int[] id) {
        if(id.length != ID_LENGTH) {
            throw new IndexOutOfBoundsException();
        }
        this.id = id.clone();
    }

    public UserId(final String id) {
        this.id = new int[ID_LENGTH];

        int j = 0;
        for(int i = 0; i < ID_LENGTH; i++) {
            if(id.charAt(j) == ',' || id.charAt(j) == ' ') {
                j++;
                continue;
            }
            this.id[i] = Integer.parseInt("" + id.charAt(j));
            j++;
        }
    }

    public int[] getId() {
        return this.id;
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                builder.append(id[i+j]);
            }
            if(i < 2) { // avoid last comma
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
