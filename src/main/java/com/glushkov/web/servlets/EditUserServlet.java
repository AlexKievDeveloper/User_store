package com.glushkov.web.servlets;


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

    private UserService userService;

    public EditUserServlet(UserService userService) {
        this.userService = userService;
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            User user = userService.getById(Integer.parseInt(request.getParameter("id")));

            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", user.getId());
            userMap.put("firstName", user.getFirstName());
            userMap.put("secondName", user.getSecondName());
            userMap.put("salary", user.getSalary());
            String date = user.getDateOfBirth().format(formatter);
            userMap.put("dateOfBirth", date);

            PageGenerator pageGenerator = PageGenerator.instance();

            String page = pageGenerator.getPage("edit.html", userMap);

            response.getWriter().println(page);

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException ioException) {
            throw new RuntimeException("Error while response writing");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        User user = new User();
        user.setId(Integer.parseInt(request.getParameter("id")));
        user.setFirstName(request.getParameter("firstName"));
        user.setSecondName(request.getParameter("secondName"));
        user.setSalary(Double.parseDouble(request.getParameter("salary").replaceAll("[^0-9.]", "")));
        user.setDateOfBirth(LocalDate.parse(request.getParameter("dateOfBirth"), formatter));

        int idToUpdate = Integer.parseInt(request.getParameter("idToUpdate"));

        userService.update(user, idToUpdate);

        AllUsersServlet allUsersServlet = new AllUsersServlet(userService);
        allUsersServlet.doGet(request, response);
    }
}
