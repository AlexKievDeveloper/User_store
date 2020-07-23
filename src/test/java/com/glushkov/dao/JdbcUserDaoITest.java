package com.glushkov.dao;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JdbcUserDaoITest {

    @Test
    void getAllTest() {
        //prepare
        JdbcUserDao jdbcUserDao = new JdbcUserDao(new DefaultDataSource());

        //when
        List<Map<String, Object>> listOfUsersMap = jdbcUserDao.getAll();

        //then
        assertFalse(listOfUsersMap.isEmpty());
        for (Map<String, Object> userMap : listOfUsersMap) {
            assertNotNull(userMap.get("id"));
            assertNotNull(userMap.get("firstName"));
            assertNotNull(userMap.get("secondName"));
            assertNotNull(userMap.get("salary"));
            assertNotNull(userMap.get("dateOfBirth"));
        }
    }

    @Test
    void saveTest() {
        //prepare
        JdbcUserDao jdbcUserDao = new JdbcUserDao(new DefaultDataSource());
        List<Map<String, Object>> listOfUsersMapBefore = jdbcUserDao.getAll();
        int expected = listOfUsersMapBefore.size() + 1;

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", 30);
        userMap.put("firstName", "Kirill");
        userMap.put("secondName", "Mavrody");
        userMap.put("salary", 2000.0);
        LocalDate dateOfBirth = new Date(1993, 6, 23).toLocalDate();
        userMap.put("dateOfBirth", dateOfBirth);

        //when
        jdbcUserDao.save(userMap);

        List<Map<String, Object>> listOfUsersMapAfter = jdbcUserDao.getAll();
        int actual = listOfUsersMapAfter.size();
        jdbcUserDao.remove(30);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void getByIdTest() {
        //prepare
        JdbcUserDao jdbcUserDao = new JdbcUserDao(new DefaultDataSource());
        Map<String, Object> userMapToDb = new HashMap<>();
        userMapToDb.put("id", 30);
        userMapToDb.put("firstName", "Kirill");
        userMapToDb.put("secondName", "Mavrody");
        userMapToDb.put("salary", 2000.0);
        LocalDate dateOfBirth = new Date(1993, 6, 23).toLocalDate();
        userMapToDb.put("dateOfBirth", dateOfBirth);
        jdbcUserDao.save(userMapToDb);

        //when
        Map<String, Object> userMapFromDb = jdbcUserDao.getById(30);
        jdbcUserDao.remove(30);
        //then
        assertFalse(userMapFromDb.isEmpty());
        assertEquals(30, userMapFromDb.get("id"));
        assertEquals("Kirill", userMapFromDb.get("firstName"));
        assertEquals("Mavrody", userMapFromDb.get("secondName"));
        assertEquals(2000.0, userMapFromDb.get("salary"));
        assertEquals(dateOfBirth, userMapFromDb.get("dateOfBirth"));
    }

    @Test
    void updateTest() {
        //prepare
        JdbcUserDao jdbcUserDao = new JdbcUserDao(new DefaultDataSource());
        Map<String, Object> userMapToDb = new HashMap<>();
        userMapToDb.put("id", 30);
        userMapToDb.put("firstName", "Kirill");
        userMapToDb.put("secondName", "Mavrody");
        userMapToDb.put("salary", 2000.0);
        LocalDate dateOfBirth = new Date(1993, 6, 23).toLocalDate();
        userMapToDb.put("dateOfBirth", dateOfBirth);
        jdbcUserDao.save(userMapToDb);
        userMapToDb.replace("secondName", "Shevchenko");

        //when
        jdbcUserDao.update(userMapToDb, 30);
        Map<String, Object> userMapFromDb = jdbcUserDao.getById(30);
        jdbcUserDao.remove(30);

        //then
        assertFalse(userMapFromDb.isEmpty());
        assertEquals(30, userMapFromDb.get("id"));
        assertEquals("Kirill", userMapFromDb.get("firstName"));
        assertEquals("Shevchenko", userMapFromDb.get("secondName"));
        assertEquals(2000.0, userMapFromDb.get("salary"));
        assertEquals(dateOfBirth, userMapFromDb.get("dateOfBirth"));
    }

    @Test
    void removeTest() {
        //prepare
        JdbcUserDao jdbcUserDao = new JdbcUserDao(new DefaultDataSource());
        List<Map<String, Object>> listOfUsersMapBefore = jdbcUserDao.getAll();
        int expected = listOfUsersMapBefore.size();

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", 30);
        userMap.put("firstName", "Kirill");
        userMap.put("secondName", "Mavrody");
        userMap.put("salary", 2000.0);
        LocalDate dateOfBirth = new Date(1993, 6, 23).toLocalDate();
        userMap.put("dateOfBirth", dateOfBirth);

        jdbcUserDao.save(userMap);
        List<Map<String, Object>> listOfUsersMapTemp = jdbcUserDao.getAll();
        assertEquals(expected + 1, listOfUsersMapTemp.size());

        //when
        jdbcUserDao.remove(30);
        List<Map<String, Object>> listOfUsersMapAfter = jdbcUserDao.getAll();
        int actual = listOfUsersMapAfter.size();

        //then
        assertEquals(expected, actual);
    }

    @Test
    void userRowMapperTest() throws SQLException {
        //prepare
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("firstName")).thenReturn("Alex");
        when(resultSet.getString("secondName")).thenReturn("Developer");
        when(resultSet.getDouble("salary")).thenReturn(3000.0);
        Date dateOfBirth = new Date(1993, 6, 23);
        when(resultSet.getDate("dateOfBirth")).thenReturn(dateOfBirth);

        //when
        Map<String, Object> actual = JdbcUserDao.userRowMapper(resultSet);

        //then
        assertNotNull(actual);
        assertEquals(1, actual.get("id"));
        assertEquals("Alex", actual.get("firstName"));
        assertEquals("Developer", actual.get("secondName"));
        assertEquals(3000.0, actual.get("salary"));
        assertEquals(dateOfBirth.toLocalDate(), actual.get("dateOfBirth"));
    }
}