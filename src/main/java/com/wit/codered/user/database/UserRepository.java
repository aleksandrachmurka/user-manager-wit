package com.wit.codered.user.database;

import com.wit.codered.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonMap;

/**
 * Provides access to database using SQL.
 * Data is mapped to/from User java class.
 */
@Repository
public class UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT id, first_name, last_name FROM users", UserRepository::mapRow);
    }

    public List<User> findByFirstName(String firstName) {
        return jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM users WHERE first_name = :first_name", singletonMap("first_name", firstName),
                UserRepository::mapRow
        );
    }

    public List<User> findByLastName(String lastName) {
        return jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM users WHERE last_name = :last_name", singletonMap("last_name", lastName),
                UserRepository::mapRow
        );
    }

    public User findById(long id) {
        List<User> userList = jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM users WHERE id = :id", singletonMap("id", id),
                UserRepository::mapRow
        );

        return userList.isEmpty() ? null : userList.get(0);
    }

    public void save(User user) {
        if (user.getId() == -1) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("first_name", user.getFirstName());
            parameters.put("last_name", user.getLastName());
            jdbcTemplate.update("INSERT INTO users(first_name, last_name) VALUES (:first_name,:last_name)", parameters);
        } else {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("first_name", user.getFirstName());
            parameters.put("last_name", user.getLastName());
            parameters.put("id", user.getId());
            jdbcTemplate.update("update users set first_name = :first_name, last_name = :last_name where id = :id", parameters);
        }
    }

    public void delete(User user) {
        jdbcTemplate.update("DELETE FROM users where id = :id", singletonMap("id", user.getId()));
    }

    private static User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"));
    }


}
