package com.glushkov.servlets;

import com.glushkov.dao.DefaultDataSource;
import com.glushkov.dao.JdbcUserDao;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class AddUserServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String page = new String(Files.readAllBytes(Paths.get("templates/webapp/form.html")));
            response.getWriter().println(page);
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException ioException) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException("Error while loading form");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", request.getParameter("id"));
        userMap.put("firstName", request.getParameter("firstName"));
        userMap.put("secondName", request.getParameter("secondName"));
        userMap.put("salary", request.getParameter("salary"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
        userMap.put("dateOfBirth", LocalDate.parse(request.getParameter("dateOfBirth"), formatter));

        JdbcUserDao jdbcUserDao = new JdbcUserDao(new DefaultDataSource());
        jdbcUserDao.save(userMap);

        AllUsersServlet allUsersServlet = new AllUsersServlet();
        allUsersServlet.doGet(request, response);
    }
}
