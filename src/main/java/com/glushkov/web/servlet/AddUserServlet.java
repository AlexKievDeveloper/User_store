package com.glushkov.web.servlet;


import com.glushkov.entity.User;
import com.glushkov.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class AddUserServlet extends HttpServlet {

    private UserService userService;

    public AddUserServlet(UserService userService) {
        this.userService = userService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String page = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/form.ftl")));
        response.getWriter().println(page);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        User user = new User();

        user.setFirstName(request.getParameter("firstName"));
        user.setSecondName(request.getParameter("secondName"));
        user.setSalary(Double.parseDouble(request.getParameter("salary")));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        user.setDateOfBirth(LocalDate.parse(request.getParameter("dateOfBirth"), formatter));
        userService.save(user);

        response.sendRedirect("/users");
    }
}