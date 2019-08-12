package com.wit.codered.user.model;

import java.util.Objects;

/**
 * Represents user.
 * Can be read from DB and used to persent on user interface.
 */
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String city;

    public User(long id, String firstName, String lastName, String city) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
    }

    public User() {
        this.id = -1;
        this.firstName = "";
        this.lastName = "";
        this.city = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                city.equals(user.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, city);
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, firstName='%s', lastName='%s', city='%s']",
                id, firstName, lastName, city);
    }
}
