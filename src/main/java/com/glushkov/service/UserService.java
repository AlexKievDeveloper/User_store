package com.glushkov.service;

import com.glushkov.dao.jdbc.JdbcUserDao;
import com.glushkov.entity.User;

import java.util.List;

public class UserService {

    private JdbcUserDao jdbcUserDao;

    public UserService(JdbcUserDao jdbcUserDao) {
        this.jdbcUserDao = jdbcUserDao;
    }

    public List<User> findAll() {
        return jdbcUserDao.findAll();
    }

    public void save(User user) {
        jdbcUserDao.save(user);
    }

    public User findById(int id) {
        return jdbcUserDao.findById(id);
    }

    public void update(User user) {
        jdbcUserDao.update(user);
    }

    public void delete(int id) {
        jdbcUserDao.delete(id);
    }
}
