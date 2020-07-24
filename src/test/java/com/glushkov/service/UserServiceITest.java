package com.glushkov.service;


import com.glushkov.dao.jdbc.JdbcUserDao;
import com.glushkov.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceITest {

    static PGSimpleDataSource dataSource = new PGSimpleDataSource();
    JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource);
    UserService userService = new UserService(jdbcUserDao);

    @BeforeAll
    static void setUp() {
        dataSource.setURL("jdbc:postgresql://localhost:5432/user_store");
        dataSource.setUser("postgres");
        dataSource.setPassword("");
    }

    @Test
    void getAllTest() {
        //when
        List<User> listOfUsersMap = userService.getAll();

        //then
        assertFalse(listOfUsersMap.isEmpty());
        for (User user : listOfUsersMap) {
            assertNotEquals(0, user.getId());
            assertNotNull(user.getFirstName());
            assertNotNull(user.getSecondName());
            assertNotNull(user.getSecondName());
            assertNotNull(user.getDateOfBirth());
        }
    }

    @Test
    void saveTest() {
        //prepare
        List<User> listOfUsersMapBefore = userService.getAll();
        int expected = listOfUsersMapBefore.size() + 1;

        User user = new User();
        user.setId(30);
        user.setFirstName("Kirill");
        user.setSecondName("Mavrody");
        user.setSalary(2000.0);
        LocalDate dateOfBirth = new Date(1993, 6, 23).toLocalDate();
        user.setDateOfBirth(dateOfBirth);

        //when
        userService.save(user);

        List<User> listOfUsersMapAfter = userService.getAll();
        int actual = listOfUsersMapAfter.size();
        userService.remove(30);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void getByIdTest() {
        //prepare
        User user = new User();
        user.setId(30);
        user.setFirstName("Kirill");
        user.setSecondName("Mavrody");
        user.setSalary(2000.0);
        LocalDate dateOfBirth = new Date(1993, 6, 23).toLocalDate();
        user.setDateOfBirth(dateOfBirth);
        userService.save(user);

        //when
        User userFromDb = userService.getById(30);
        userService.remove(30);

        //then
        assertNotNull(userFromDb);
        assertEquals(30, userFromDb.getId());
        assertEquals("Kirill", userFromDb.getFirstName());
        assertEquals("Mavrody", userFromDb.getSecondName());
        assertEquals(2000.0, userFromDb.getSalary());
        assertEquals(dateOfBirth, userFromDb.getDateOfBirth());
    }

    @Test
    void updateTest() {
        //prepare
        User user = new User();
        user.setId(30);
        user.setFirstName("Kirill");
        user.setSecondName("Mavrody");
        user.setSalary(2000.0);
        LocalDate dateOfBirth = new Date(1993, 6, 23).toLocalDate();
        user.setDateOfBirth(dateOfBirth);
        userService.save(user);
        user.setSecondName("Shevchenko");

        //when
        userService.update(user, 30);
        User userFromDb = userService.getById(30);
        userService.remove(30);

        //then
        assertNotNull(userFromDb);
        assertEquals(30, userFromDb.getId());
        assertEquals("Kirill", userFromDb.getFirstName());
        assertEquals("Shevchenko", userFromDb.getSecondName());
        assertEquals(2000.0, userFromDb.getSalary());
        assertEquals(dateOfBirth, userFromDb.getDateOfBirth());
    }

    @Test
    void removeTest() {
        //prepare
        List<User> listOfUsersBefore = userService.getAll();
        int expected = listOfUsersBefore.size();

        User user = new User();
        user.setId(30);
        user.setFirstName("Kirill");
        user.setSecondName("Mavrody");
        user.setSalary(2000.0);
        LocalDate dateOfBirth = new Date(1993, 6, 23).toLocalDate();
        user.setDateOfBirth(dateOfBirth);
        userService.save(user);

        List<User> listOfUsersTemp = userService.getAll();
        assertEquals(expected + 1, listOfUsersTemp.size());

        //when
        userService.remove(30);
        List<User> listOfUsersAfter = userService.getAll();
        int actual = listOfUsersAfter.size();

        //then
        assertEquals(expected, actual);
    }
}