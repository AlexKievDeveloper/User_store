package com.glushkov.web.servlet;


import com.glushkov.dao.jdbc.JdbcUserDao;
import com.glushkov.entity.User;
import com.glushkov.service.UserService;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.Response;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class AddUserServletITest {

    private static final String DROP_TABLE = "DROP TABLE users";//TODO сделать не константой и переместить внутрь метода?

    @Test
    void doGetTest() throws ServletException {
        //prepare
        UserService mockUserService = mock(UserService.class);
        AddUserServlet addUserServlet = new AddUserServlet(mockUserService);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpChannel mockHttpChannel = mock(HttpChannel.class);
        HttpOutput mockHttpOutput = mock(HttpOutput.class);

        HttpServletResponse response = new Response(mockHttpChannel, mockHttpOutput);

        //when
        addUserServlet.doGet(mockRequest, response);

        //then
        assertEquals(200, response.getStatus());
        assertEquals("text/html;charset=utf-8", response.getContentType());
    }

    @Test
    void doPostTest() throws SQLException, ServletException {
        //prepare
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:~/test/resources/db;INIT=runscript from 'src/test/resources/h2-test-schema.sql';");
        dataSource.setUser("h2");
        dataSource.setPassword("h2");

        JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource);
        UserService userService = new UserService(jdbcUserDao);
        AddUserServlet addUserServlet = new AddUserServlet(userService);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        when(mockRequest.getParameter("firstName")).thenReturn("Alex");
        when(mockRequest.getParameter("secondName")).thenReturn("Developer");
        when(mockRequest.getParameter("salary")).thenReturn("3000.1");
        when(mockRequest.getParameter("dateOfBirth")).thenReturn("1993-06-23");

        List<User> listOfUsersBefore = jdbcUserDao.findAll();
        assertTrue(listOfUsersBefore.isEmpty());

        //when
        addUserServlet.doPost(mockRequest, mockResponse);

        //then
        List<User> listOfUsersAfter = jdbcUserDao.findAll();
        assertFalse(listOfUsersAfter.isEmpty());
        assertEquals(1, listOfUsersAfter.size());
        assertEquals(1, listOfUsersAfter.get(0).getId());
        assertEquals("Alex", listOfUsersAfter.get(0).getFirstName());
        assertEquals("Developer", listOfUsersAfter.get(0).getSecondName());
        assertEquals(3000.1, listOfUsersAfter.get(0).getSalary());
        assertEquals(LocalDate.of(1993, 6, 23), listOfUsersAfter.get(0).getDateOfBirth());

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE);
        }
    }
}
