package com.glushkov.dao.jdbc.mapper;

import com.glushkov.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserRowMapperTest {

    @Test
    void userRowMapperTest() throws SQLException {
        //prepare
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("firstName")).thenReturn("Alex");
        when(mockResultSet.getString("secondName")).thenReturn("Developer");
        when(mockResultSet.getDouble("salary")).thenReturn(3000.0);
        Date dateOfBirth = Date.valueOf(LocalDate.of(1993, 6, 23));
        when(mockResultSet.getDate("dateOfBirth")).thenReturn(dateOfBirth);
        UserRowMapper userRowMapper = new UserRowMapper();

        //when
        User actual = userRowMapper.mapRow(mockResultSet);

        //then
        assertNotNull(actual);
        assertEquals(1, actual.getId());
        assertEquals("Alex", actual.getFirstName());
        assertEquals("Developer", actual.getSecondName());
        assertEquals(3000.0, actual.getSalary());
        assertEquals(dateOfBirth.toLocalDate(), actual.getDateOfBirth());
    }
}