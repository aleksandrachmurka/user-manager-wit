package com.wit.codered.user;

import com.wit.codered.user.database.UserDataInitializer;
import com.wit.codered.user.database.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of our application.
 */
@SpringBootApplication
public class UserApplication implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(UserApplication.class);

    @Autowired
    public UserDataInitializer userDataInitializer;

    public static void main(String... args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // As we're using in memory H2 database it's always created empty on app start, we have to:
        // 1. Create table in database
        userDataInitializer.createTable();
        // 2. Optionally add some users to present data on application start
        userDataInitializer.createTestUsers();
    }
}
