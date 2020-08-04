package com.glushkov.web.servlet;


import com.glushkov.entity.User;
import com.glushkov.service.UserService;
import com.glushkov.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AllUsersServlet extends HttpServlet {

    private UserService userService;

    public AllUsersServlet(UserService userService) {
        this.userService = userService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        List<User> users;

        try {
            users = userService.findAll();

            Map<String, Object> input = new HashMap<>();
            input.put("users", users);

            PageGenerator pageGenerator = PageGenerator.instance();

            String page = pageGenerator.getPage("page.ftl", input);

            response.setContentType("text/html;charset=utf-8");

            response.getWriter().println(page);

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
