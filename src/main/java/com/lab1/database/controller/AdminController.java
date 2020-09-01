package com.lab1.database.controller;

import com.lab1.database.model.User;
import com.lab1.database.service.UserService;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@ManagedBean
public class AdminController {

    //Для формы добавления пользователя в БД
    private String username;
    private String password;
    private String confirmPassword;

    private List users;
    private UserService userService=new UserService();

    public List getUsers(){
        users=userService.getAllUserRole();
        return users;
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
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    public void deleteUser(String name){
        userService.deleteUser(name);
    }

    public String getCurrentUserName() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return  request.getRemoteUser();
    }

    public void addUser() throws IOException {

        User newUser = new User(username,password,0.0);

        if (getUsername().equals("")||getPassword().equals("")) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("http://localhost:8080/addErrorServlet");
        }
        else {
            if (!userService.isExist(getUsername())) {
                if (password.equals(confirmPassword)) {
                    userService.add(newUser, "user");
                } else {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.getExternalContext().redirect("http://localhost:8080/addUserPasswordErrorServlet");
                }
            }
            else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().redirect("http://localhost:8080/addUserErrorServlet");
            }
        }
    }
}