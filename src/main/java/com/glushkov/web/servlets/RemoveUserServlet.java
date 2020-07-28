package com.glushkov.web.servlets;


import com.glushkov.services.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RemoveUserServlet extends HttpServlet {

    private UserService userService;

    public RemoveUserServlet(UserService userService) {
        this.userService = userService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("id"));

        userService.delete(userId);

        try {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("AllUserServlet");
            requestDispatcher.forward(request, response);
        }
        catch (IOException | ServletException e){
            throw new RuntimeException("Error while request dispatcher process", e);
        }
    }
}
