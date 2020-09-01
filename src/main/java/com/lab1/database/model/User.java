package com.lab1.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "y_e")
    private double y_e;

    public User() {
    }

    public User(String username, String password, double y_e) {
        this.username = username;
        this.password = password;
        this.y_e = y_e;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public double getY_e() {
        return y_e;
    }
    public void setY_e(double y_e) {
        this.y_e = y_e;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"["+username+"]";
    }
}