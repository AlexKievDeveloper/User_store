package com.glushkov.dao;


import com.glushkov.entity.User;

import java.sql.SQLException;
import java.util.List;


public interface UserDao {

    User findById(int id) throws SQLException;

    List<User> findAll() throws SQLException;

    void save(User user) throws SQLException;

    void update(User user) throws SQLException;

    void delete(int userId) throws SQLException;
}
