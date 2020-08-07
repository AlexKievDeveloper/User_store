package com.glushkov.web.servlet;

import com.glushkov.dao.jdbc.JdbcUserDao;
import com.glushkov.entity.User;
import com.glushkov.service.UserService;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RemoveUserServletITest {
    private static final String DROP_TABLE = "DROP TABLE users";

    @Test
    void doPostTest() throws SQLException, IOException {
        //prepare
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:~/test/resources/db;INIT=runscript from 'src/test/resources/h2-test-schema.sql';");
        dataSource.setUser("h2");
        dataSource.setPassword("h2");

        JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource);
        UserService UserService = new UserService(jdbcUserDao);
        RemoveUserServlet removeUserServlet = new RemoveUserServlet(UserService);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        when(mockRequest.getParameter("id")).thenReturn("1");

        User user = new User();
        user.setFirstName("Alex");
        user.setSecondName("Developer");
        user.setSalary(3000.1);
        user.setDateOfBirth(LocalDate.of(1993, 6, 23));
        jdbcUserDao.save(user);

        List<User> listOfUsersBefore = jdbcUserDao.findAll();
        assertFalse(listOfUsersBefore.isEmpty());

        //when
        removeUserServlet.doPost(mockRequest, mockResponse);

        //then
        List<User> listOfUsersAfter = jdbcUserDao.findAll();
        assertTrue(listOfUsersAfter.isEmpty());

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE);
        }
    }
}