package com.glushkov.daos.jdbc.mapper;

import com.glushkov.entities.User;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserRowMapperTest {

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
        UserRowMapper userRowMapper = new UserRowMapper();

        //when
        User actual = userRowMapper.userRowMapper(resultSet);

        //then
        assertNotNull(actual);
        assertEquals(1, actual.getId());
        assertEquals("Alex", actual.getFirstName());
        assertEquals("Developer", actual.getSecondName());
        assertEquals(3000.0, actual.getSalary());
        assertEquals(dateOfBirth.toLocalDate(), actual.getDateOfBirth());
    }
}