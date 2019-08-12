package com.wit.codered.user.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class UserDataInitializer {
    private static final Logger log = LoggerFactory.getLogger(UserDataInitializer.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        jdbcTemplate.execute("DROP TABLE users IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE users(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255), city VARCHAR(255))");
    }

    public void createTestUsers() {
        // Split up the array of whole names into an array of first/last names
        List<Object[]> splitUpData = Stream.of("John Woo London", "Jeff Dean Paris", "Josh Bloch Jaffa", "Josh Long Hongkong")
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        // Use a Java 8 stream to print out each tuple of the list
        splitUpData.forEach(item -> log.info(String.format("Inserting user record for %s %s from %s", item[0], item[1], item[2])));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO users(first_name, last_name, city) VALUES (?,?,?)", splitUpData);
    }
}

