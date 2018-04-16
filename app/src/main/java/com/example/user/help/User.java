package com.example.user.help;

/**
 * Created by USER on 3/12/2018.
 */

public class User {

    String userId;
    String email;
    String password;
    String firstname;
    String lastname;
    String contact;
    String location;

    public User(){}

    public User(String email, String password, String firstname, String lastname, String contact, String location){
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.contact = contact;
        this.location = location;
    }

    public User(String password, String firstname, String lastname, String contact, String location){
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.contact = contact;
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
