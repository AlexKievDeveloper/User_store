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

public class SearchUserServlet extends HttpServlet {

    private final UserService userService;

    public SearchUserServlet(UserService userService) {
        this.userService = userService;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String enteredName = request.getParameter("enteredName");

        List<User> usersList = userService.findByName(enteredName);

        Map<String, Object> usersMap = new HashMap<>();

        if (usersList.isEmpty()) {
            usersMap.put("message", "Sorry, no users were found for your request: " + enteredName);
        } else {
            usersMap.put("users", usersList);
        }

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(PageGenerator.instance().getPage("page.ftl", usersMap));
    }
}
