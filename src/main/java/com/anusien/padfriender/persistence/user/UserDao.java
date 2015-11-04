package com.anusien.padfriender.persistence.user;

import com.anusien.padfriender.model.user.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class UserDao {
    private final DataSource dataSource;

    @Autowired
    public UserDao(@Nonnull final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private final static String createUserSql = "INSERT INTO users (email, name, password, friendcode, enabled) " +
                                             "VALUES(?, ?, ?, ?, ?);";

    // TODO check to see if that user already exists
    public void createUser(@Nonnull final String email, @Nonnull final String name, @Nonnull final UserId friendCode,
                           @Nonnull final String password) throws IOException {
        final String hash;
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            final byte[] bytes = md.digest();
            hash = new BigInteger(1, bytes).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e);
        }

        final JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update(createUserSql, email, name, hash, friendCode.toString(), 1);
    }
}
