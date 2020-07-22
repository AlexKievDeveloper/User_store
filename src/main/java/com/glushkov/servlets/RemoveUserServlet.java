package com.glushkov.servlets;

import com.glushkov.dao.DefaultDataSource;
import com.glushkov.dao.JdbcUserDao;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RemoveUserServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("id"));

        JdbcUserDao jdbcUserDao = new JdbcUserDao(new DefaultDataSource());
        jdbcUserDao.remove(userId);

        AllUsersServlet allUsersServlet = new AllUsersServlet();
        allUsersServlet.doGet(request, response);
    }
}
