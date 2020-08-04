package com.glushkov.web.servlet;


import com.glushkov.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RemoveUserServlet extends HttpServlet {

    private UserService userService;

    public RemoveUserServlet(UserService userService) {
        this.userService = userService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            userService.delete(id);
            response.sendRedirect("/users");
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
