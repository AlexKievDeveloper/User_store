package com.glushkov.dao;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JdbcUserDaoITest {

    @Test
    void getAll() {
        //prepare
        DefaultDataSource defaultDataSource = new DefaultDataSource();
        JdbcUserDao jdbcUserDao = new JdbcUserDao(defaultDataSource);

        //when
        List<Map<String, Object>> list = jdbcUserDao.getAll();
        Map<String, Object> tableHeaders = list.get(0);

        //then
        assertFalse(list.isEmpty());
        assertEquals("Id", tableHeaders.get("id"));
        assertEquals("First name", tableHeaders.get("firstName"));
        assertEquals("Second name", tableHeaders.get("secondName"));
        assertEquals("Salary", tableHeaders.get("salary"));
        assertEquals("Date of birth", tableHeaders.get("dateOfBirth"));
    }
}