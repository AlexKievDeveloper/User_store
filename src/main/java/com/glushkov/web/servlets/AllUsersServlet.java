package com.glushkov.web.servlets;

import com.glushkov.entities.User;
import com.glushkov.services.UserService;
import com.glushkov.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllUsersServlet extends HttpServlet {

    private UserService userService;

    public AllUsersServlet(UserService userService) {
        this.userService = userService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            List<User> users = userService.findAll();

            Map<String, Object> input = new HashMap<>();
            input.put("users", users);

            PageGenerator pageGenerator = PageGenerator.instance();

            String page = pageGenerator.getPage("page.ftl", input);

            response.getWriter().println(page);

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException ioException) {
            throw new RuntimeException("Error while response writing", ioException);
        }
    }
}
