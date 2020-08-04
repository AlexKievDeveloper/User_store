package com.glushkov.service;

import com.glushkov.dao.jdbc.JdbcUserDao;
import com.glushkov.entity.User;

import java.sql.SQLException;
import java.util.List;


public class UserService {

    private JdbcUserDao jdbcUserDao;

    public UserService(JdbcUserDao jdbcUserDao) {
        this.jdbcUserDao = jdbcUserDao;
    }

    public List<User> findAll() throws SQLException {
        return jdbcUserDao.findAll();
    }

    public void save(User user) throws SQLException {
        jdbcUserDao.save(user);
    }

    public User findById(int id) throws SQLException {
        return jdbcUserDao.findById(id);
    }

    public void update(User user) throws SQLException {
        jdbcUserDao.update(user);
    }

    public void delete(int id) throws SQLException {
        jdbcUserDao.delete(id);
    }
}
