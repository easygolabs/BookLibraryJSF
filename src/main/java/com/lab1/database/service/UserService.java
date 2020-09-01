package com.lab1.database.service;

import com.lab1.database.dao.UserDAO;
import com.lab1.database.dao.UserRole;
import com.lab1.database.model.User;

import java.util.List;

public class UserService {

    private UserDAO userDAO=new UserDAO();

    public List<UserRole> getAllUserRole() {
        return userDAO.getAllUserRole();
    }

    public void add(User newUser, String role) {
        userDAO.add(newUser,role);
    }

    public void deleteUser(String username) {
        userDAO.delete(username);
    }

    public User findUserByName(String name) {
        return userDAO.findUserByName(name);
    }

    public void updateData(User user, String name) {
        userDAO.updateData(user,name);
    }

    public boolean isExist(String username){

        boolean isExist=false;

        for(UserRole userRole:getAllUserRole()){
            if(userRole.getUsername().equals(username)) {
                isExist = true;
            }
        }
        return isExist;
    }
}