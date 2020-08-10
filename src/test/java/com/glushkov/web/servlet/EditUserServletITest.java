package com.glushkov.web.servlet;

import com.glushkov.Starter;
import com.glushkov.dao.jdbc.JdbcUserDao;
import com.glushkov.entity.User;
import com.glushkov.service.UserService;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.Response;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EditUserServletITest {

    private static final String DROP_TABLE = "DROP TABLE users";

    private static final JdbcDataSource dataSource = new JdbcDataSource();

    private static final JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource);

    private static final UserService userService = new UserService(jdbcUserDao);

    private static final EditUserServlet editUserServlet = new EditUserServlet(userService);

    @BeforeAll
    static void setUp() throws IOException {
        Properties properties = new Properties();

        BufferedInputStream propertiesBufferedInputStream = new BufferedInputStream(Starter.class.getResourceAsStream("/tests.properties"));
        properties.load(propertiesBufferedInputStream);

        dataSource.setURL(properties.getProperty("jdbc.host")+properties.getProperty("create-table"));
        dataSource.setUser(properties.getProperty("jdbc.user"));
        dataSource.setPassword(properties.getProperty("jdbc.password"));

        User user = new User();
        user.setFirstName("Alex");
        user.setSecondName("Developer");
        user.setSalary(3000.1);
        LocalDate dateOfBirth = LocalDate.of(1993, 6, 23);
        user.setDateOfBirth(dateOfBirth);
        jdbcUserDao.save(user);
    }

    @Test
    @DisplayName("Processes the client's request and sends a response with status code, content type, encoding and a page with user data")
    void doGetTest() throws IOException {
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
    @DisplayName("Processes the request and update the user data in the database")
    void doPostTest() throws IOException {
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
