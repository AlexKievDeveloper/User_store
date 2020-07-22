package com.glushkov.servlets;

import com.glushkov.dao.DefaultDataSource;
import com.glushkov.dao.JdbcUserDao;
import com.glushkov.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class EditUserServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            JdbcUserDao jdbcUserDao = new JdbcUserDao(new DefaultDataSource());
            Map<String, Object> userMap = jdbcUserDao.getById(request.getParameter("id"));

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

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", request.getParameter("id"));
        userMap.put("firstName", request.getParameter("firstName"));
        userMap.put("secondName", request.getParameter("secondName"));
        userMap.put("salary", request.getParameter("salary"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
        userMap.put("dateOfBirth", LocalDate.parse(request.getParameter("dateOfBirth"), formatter));

        int idToUpdate= Integer.parseInt(request.getParameter("idToUpdate"));

        JdbcUserDao jdbcUserDao = new JdbcUserDao(new DefaultDataSource());
        jdbcUserDao.update(userMap, idToUpdate);

        AllUsersServlet allUsersServlet = new AllUsersServlet();
        allUsersServlet.doGet(request, response);
    }
}
