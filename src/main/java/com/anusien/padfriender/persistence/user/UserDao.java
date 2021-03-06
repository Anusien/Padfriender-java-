package com.anusien.padfriender.persistence.user;

import com.anusien.padfriender.model.user.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.io.IOException;

@Component
public class UserDao {
    private final DataSource dataSource;

    @Autowired
    public UserDao(@Nonnull final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private final static String CREATE_USER_SQL = "INSERT INTO users(email, name, password, friendcode, enabled) " +
                                                "VALUES(?, ?, ?, ?, ?);";
    private final static String CREATE_USER_ROLE_SQL = "INSERT INTO user_authorities(user_id, authority) " +
                                                    "SELECT users.id, 'ROLE_USER' FROM users WHERE users.email = ?";

    // TODO check to see if that user already exists
    public void createUser(@Nonnull final String email, @Nonnull final String name, @Nonnull final UserId friendCode,
                           @Nonnull final String password) throws IOException {
        final String hash = new ShaPasswordEncoder(256).encodePassword(password, email);

        final JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update(CREATE_USER_SQL, email, name, hash, friendCode.toString(), 1);
        template.update(CREATE_USER_ROLE_SQL, email);
    }
}
