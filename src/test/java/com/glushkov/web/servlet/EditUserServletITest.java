package com.glushkov.web.servlet;

import com.glushkov.dao.jdbc.JdbcUserDao;
import com.glushkov.entity.User;
import com.glushkov.service.UserService;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.Response;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EditUserServletITest {

    private static final String DROP_TABLE = "DROP TABLE users";

    private static JdbcDataSource dataSource = new JdbcDataSource();

    private static JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource);

    private static UserService userService = new UserService(jdbcUserDao);

    private static EditUserServlet editUserServlet = new EditUserServlet(userService);

    @BeforeAll
    static void setUp() throws SQLException {
        dataSource.setURL("jdbc:h2:~/test/resources/db;INIT=runscript from 'src/test/resources/h2-test-schema.sql';");
        dataSource.setUser("h2");
        dataSource.setPassword("h2");

        User user = new User();
        user.setFirstName("Alex");
        user.setSecondName("Developer");
        user.setSalary(3000.1);
        LocalDate dateOfBirth = LocalDate.of(1993, 6, 23);
        user.setDateOfBirth(dateOfBirth);
        jdbcUserDao.save(user);
    }

    @Test
    void doGetTest() throws ServletException {
        //prepare
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpChannel mockHttpChannel = mock(HttpChannel.class);
        HttpOutput mockHttpOutput = mock(HttpOutput.class);

        HttpServletResponse response = new Response(mockHttpChannel, mockHttpOutput);
        when(mockRequest.getParameter("id")).thenReturn("1");

        //when
        editUserServlet.doGet(mockRequest, response);

        //then
        assertEquals(200, response.getStatus());
        assertEquals("text/html;charset=utf-8", response.getContentType());
    }

    @Test
    void doPostTest() throws ServletException, SQLException {
        //prepare
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

        when(mockRequest.getParameter("id")).thenReturn("1");
        when(mockRequest.getParameter("firstName")).thenReturn("Alex");
        when(mockRequest.getParameter("secondName")).thenReturn("Developer");
        when(mockRequest.getParameter("salary")).thenReturn("5000.0");
        when(mockRequest.getParameter("dateOfBirth")).thenReturn("1993-06-23");

        HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        //when
        editUserServlet.doPost(mockRequest, mockResponse);

        //then
        assertEquals(5000.0, jdbcUserDao.findById(1).getSalary());
    }

    @AfterAll
    static void cleanUp() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE);
        }
    }
}