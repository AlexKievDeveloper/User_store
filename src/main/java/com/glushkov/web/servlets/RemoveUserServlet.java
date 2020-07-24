package com.glushkov.web.servlets;


import com.glushkov.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RemoveUserServlet extends HttpServlet {

    private UserService userService;

    public RemoveUserServlet(UserService userService) {
        this.userService = userService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("id"));

        userService.remove(userId);

        AllUsersServlet allUsersServlet = new AllUsersServlet(userService);
        allUsersServlet.doGet(request, response);
    }
}
