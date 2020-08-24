package com.glushkov.web.servlet;

import com.glushkov.entity.User;
import com.glushkov.service.UserService;
import com.glushkov.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllUsersServlet extends HttpServlet {

    private final UserService userService;

    public AllUsersServlet(UserService userService) {
        this.userService = userService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<User> usersList = userService.findAll();

        Map<String, Object> usersMap = new HashMap<>();
        usersMap.put("users", usersList);

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(PageGenerator.instance().getPage("/page.html", usersMap));
    }
}
