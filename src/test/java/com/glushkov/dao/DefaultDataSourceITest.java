package com.glushkov.dao;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DefaultDataSourceITest {

    @Test
    void getConnection() throws SQLException {
        //prepare
        DefaultDataSource defaultDataSource = new DefaultDataSource();
        //when
        Connection connection = defaultDataSource.getConnection();
        //then
        assertFalse(connection.isClosed());
        connection.close();
        assertTrue(connection.isClosed());
    }
}