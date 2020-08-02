package com.glushkov.dao.jdbc;

import com.glushkov.entity.User;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcUserDaoITest {

    private static final String DELETE = "DELETE FROM users";

    private static final String RESTART_AUTO_INC = "ALTER TABLE users ALTER COLUMN id RESTART WITH 1";

    private static JdbcDataSource dataSource = new JdbcDataSource();

    private static JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource);

    @BeforeAll
    static void setUp() throws SQLException {
        dataSource.setURL("jdbc:h2:file:~/src/test/resources/db.mv.db/user_store;MV_STORE=false");
        dataSource.setUser("h2");
        dataSource.setPassword("h2");

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(DELETE);
            statement.execute(RESTART_AUTO_INC);
        }

        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setFirstName("Kirill");
            user.setSecondName("Mavrody");
            user.setSalary(2000.0);
            LocalDate dateOfBirth = LocalDate.of(1993, 6, 23);
            user.setDateOfBirth(dateOfBirth);
            jdbcUserDao.save(user);
        }
    }

    @Test
    void findAllTest() {

        //when
        List<User> listOfUsersMap = jdbcUserDao.findAll();

        //then
        assertFalse(listOfUsersMap.isEmpty());
        assertEquals(5, listOfUsersMap.size());

        for (User userFromDB : listOfUsersMap) {
            assertNotEquals(0, userFromDB.getId());
            assertNotNull(userFromDB.getFirstName());
            assertNotNull(userFromDB.getSecondName());
            assertNotNull(userFromDB.getSecondName());
            assertNotNull(userFromDB.getDateOfBirth());
        }
    }

    @Test
    void saveTest() {
        //prepare
        List<User> listOfUsersMapBefore = jdbcUserDao.findAll();
        assertEquals(5, listOfUsersMapBefore.size());

        User user = new User();
        user.setFirstName("Kirill");
        user.setSecondName("Mavrody");
        user.setSalary(2000.0);
        LocalDate dateOfBirth = LocalDate.of(1993, 6, 23);
        user.setDateOfBirth(dateOfBirth);

        //when
        jdbcUserDao.save(user);

        List<User> listOfUsersMapAfter = jdbcUserDao.findAll();
        int actual = listOfUsersMapAfter.size();
        jdbcUserDao.delete(6);

        //then
        assertEquals(6, actual);
        assertEquals(6, listOfUsersMapAfter.get(5).getId());
        assertEquals(user.getFirstName(), listOfUsersMapAfter.get(5).getFirstName());
        assertEquals(user.getSecondName(), listOfUsersMapAfter.get(5).getSecondName());
        assertEquals(user.getSalary(), listOfUsersMapAfter.get(5).getSalary());
        assertEquals(user.getDateOfBirth(), listOfUsersMapAfter.get(5).getDateOfBirth());
    }

    @Test
    void findByIdTest() {
        //when
        User userFromDb = jdbcUserDao.findById(1);

        //then
        assertNotNull(userFromDb);
        assertEquals(1, userFromDb.getId());
        assertEquals("Kirill", userFromDb.getFirstName());
        assertEquals("Mavrody", userFromDb.getSecondName());
        assertEquals(2000.0, userFromDb.getSalary());
        assertEquals(LocalDate.of(1993, 6, 23), userFromDb.getDateOfBirth());
    }

    @Test
    void updateTest() {
        //prepare
        User user = new User();
        user.setId(2);
        user.setFirstName("Kirill");
        user.setSecondName("Shevchenko");
        user.setSalary(2000.0);
        LocalDate dateOfBirth = LocalDate.of(1993, 6, 23);
        user.setDateOfBirth(dateOfBirth);

        //when
        jdbcUserDao.update(user);
        User userFromDb = jdbcUserDao.findById(2);

        //then
        assertNotNull(userFromDb);
        assertEquals(2, userFromDb.getId());
        assertEquals("Kirill", userFromDb.getFirstName());
        assertEquals("Shevchenko", userFromDb.getSecondName());
        assertEquals(2000.0, userFromDb.getSalary());
        assertEquals(dateOfBirth, userFromDb.getDateOfBirth());
    }

    @Test
    void deleteTest() {
        //when
        jdbcUserDao.delete(5);
        List<User> listOfUsersAfter = jdbcUserDao.findAll();

        //then
        assertEquals(4, listOfUsersAfter.size());
    }

    @AfterAll
    static void cleanUp() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(DELETE);
            statement.execute(RESTART_AUTO_INC);
        }
    }
}
