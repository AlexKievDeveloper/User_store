package com.glushkov.daos.jdbc.mapper;

import com.glushkov.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper {
    public User userRowMapper(ResultSet resultSet) {

        User user = new User();

        try {
            user.setId(resultSet.getInt("id"));
            user.setFirstName(resultSet.getString("firstName"));
            user.setSecondName(resultSet.getString("secondName"));
            user.setSalary(resultSet.getDouble("salary"));
            user.setDateOfBirth(resultSet.getDate("dateOfBirth").toLocalDate());

            return user;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Can`t get value from result set. ", sqlException);
        }
    }
}
