package com.lab1.database.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users_roles")
public class UserRole {

    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "rolename")
    private String rolename;

    public UserRole() {
    }

    public UserRole(String username, String rolename) {
        this.username = username;
        this.rolename = rolename;
    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRolename() {
        return rolename;
    }
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"[username: "+username+" role: "+rolename+"]\n";

    }
}
