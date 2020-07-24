package com.glushkov.dao.jdbc;

import com.glushkov.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcUserDaoITest {

    static PGSimpleDataSource dataSource = new PGSimpleDataSource();
    JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource);

    @BeforeAll
    static void setUp() {
        dataSource.setURL("jdbc:postgresql://localhost:5432/user_store");
        dataSource.setUser("postgres");
        dataSource.setPassword("");
    }

    @Test
    void getAllTest() {

        //when
        List<User> listOfUsersMap = jdbcUserDao.getAll();

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
        List<User> listOfUsersMapBefore = jdbcUserDao.getAll();
        int expected = listOfUsersMapBefore.size() + 1;

        User user = new User();
        user.setId(30);
        user.setFirstName("Kirill");
        user.setSecondName("Mavrody");
        user.setSalary(2000.0);
        LocalDate dateOfBirth = new Date(1993, 6, 23).toLocalDate();
        user.setDateOfBirth(dateOfBirth);

        //when
        jdbcUserDao.save(user);

        List<User> listOfUsersMapAfter = jdbcUserDao.getAll();
        int actual = listOfUsersMapAfter.size();
        jdbcUserDao.remove(30);
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
        jdbcUserDao.save(user);

        //when
        User userFromDb = jdbcUserDao.getById(30);
        jdbcUserDao.remove(30);
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
        jdbcUserDao.save(user);
        user.setSecondName("Shevchenko");

        //when
        jdbcUserDao.update(user, 30);
        User userFromDb = jdbcUserDao.getById(30);
        jdbcUserDao.remove(30);

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
        List<User> listOfUsersBefore = jdbcUserDao.getAll();
        int expected = listOfUsersBefore.size();

        User user = new User();
        user.setId(30);
        user.setFirstName("Kirill");
        user.setSecondName("Mavrody");
        user.setSalary(2000.0);
        LocalDate dateOfBirth = new Date(1993, 6, 23).toLocalDate();
        user.setDateOfBirth(dateOfBirth);
        jdbcUserDao.save(user);

        List<User> listOfUsersTemp = jdbcUserDao.getAll();
        assertEquals(expected + 1, listOfUsersTemp.size());

        //when
        jdbcUserDao.remove(30);
        List<User> listOfUsersAfter = jdbcUserDao.getAll();
        int actual = listOfUsersAfter.size();

        //then
        assertEquals(expected, actual);
    }
}
