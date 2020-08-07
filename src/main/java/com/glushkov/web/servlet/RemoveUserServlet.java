package com.glushkov.web.servlet;


import com.glushkov.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RemoveUserServlet extends HttpServlet {

    private final UserService userService;

    public RemoveUserServlet(UserService userService) {
        this.userService = userService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        userService.delete(id);
        response.sendRedirect("/users");
    }
}
