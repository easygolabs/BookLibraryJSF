package com.lab1.database.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addUserPasswordErrorServlet")
public class AddUserPasswordErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getOutputStream().print("Password is incorrect");
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Check input fields password and confirmPassword");
    }
}