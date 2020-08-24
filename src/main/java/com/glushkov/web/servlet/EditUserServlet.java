package com.glushkov.web.servlet;

import com.glushkov.entity.User;
import com.glushkov.service.UserService;
import com.glushkov.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class EditUserServlet extends HttpServlet {

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final UserService userService;

    public EditUserServlet(UserService userService) {
        this.userService = userService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        User user = userService.findById(Integer.parseInt(request.getParameter("id")));

        Map<String, Object> userMap = getUserMap(user);

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(PageGenerator.instance().getPage("/edit.html", userMap));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = new User();
        user.setId(Integer.parseInt(request.getParameter("id")));
        user.setFirstName(request.getParameter("firstName"));
        user.setSecondName(request.getParameter("secondName"));
        user.setSalary(Double.parseDouble(request.getParameter("salary")));
        user.setDateOfBirth(LocalDate.parse(request.getParameter("dateOfBirth"), DATE_TIME_FORMATTER));
        userService.update(user);
        response.sendRedirect("/users");
    }

    private Map<String, Object> getUserMap(User user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("firstName", user.getFirstName());
        userMap.put("secondName", user.getSecondName());
        userMap.put("salary", user.getSalary());
        userMap.put("dateOfBirth", user.getDateOfBirth());
        return userMap;
    }
}
