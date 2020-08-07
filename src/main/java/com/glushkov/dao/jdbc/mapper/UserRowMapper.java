package com.glushkov.dao.jdbc.mapper;

import com.glushkov.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper {

    public User mapRow(ResultSet resultSet) throws SQLException {

        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setSecondName(resultSet.getString("secondName"));
        user.setSalary(resultSet.getDouble("salary"));
        user.setDateOfBirth(resultSet.getDate("dateOfBirth").toLocalDate());

        return user;
    }
}
