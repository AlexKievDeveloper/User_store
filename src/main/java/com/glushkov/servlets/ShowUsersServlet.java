package com.glushkov.servlets;

import com.glushkov.dao.DefaultDataSource;
import com.glushkov.dao.JdbcUserDao;
import com.glushkov.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ShowUsersServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        JdbcUserDao jdbcUserDao = new JdbcUserDao(new DefaultDataSource());
        List<Map<String, Object>> users = jdbcUserDao.getAll();

        PageGenerator pageGenerator = PageGenerator.instance();

        String page = pageGenerator.getPage("page.html", users);

        response.getWriter().println(page);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
