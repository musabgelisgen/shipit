package com.db.shipit.models;

public class User {
    private String ID;
    private String email;
    private String encryptedPassword;
    private String firstName;
    private String lastName;

    public User(String ID, String email, String encryptedPassword, String firstName, String lastName) {
        this.ID = ID;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(){}

    public String getID() {
        return ID;
    }

    public User setID(String ID) {
        this.ID = ID;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public User setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }

    @Override
    public String toString() {
        return "User{" +
                "ID='" + ID + '\'' +
                ", email='" + email + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!ID.equals(user.ID)) return false;
        if (!email.equals(user.email)) return false;
        if (!encryptedPassword.equals(user.encryptedPassword)) return false;
        if (!firstName.equals(user.firstName)) return false;
        return lastName.equals(user.lastName);
    }

    @Override
    public int hashCode() {
        int result = ID.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + encryptedPassword.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }
}
