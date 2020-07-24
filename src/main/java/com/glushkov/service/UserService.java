package com.glushkov.service;

import com.glushkov.dao.jdbc.JdbcUserDao;
import com.glushkov.entity.User;

import java.util.List;

public class UserService implements UserServiceInterface {

    private JdbcUserDao jdbcUserDao;

    public UserService(JdbcUserDao jdbcUserDao) {
        this.jdbcUserDao = jdbcUserDao;
    }

    public List<User> getAll() {
        return jdbcUserDao.getAll();
    }

    public void save(User user) {
        jdbcUserDao.save(user);
    }

    public User getById(int id) {
        return jdbcUserDao.getById(id);
    }

    public void update(User user, int idToUpdate) {
        jdbcUserDao.update(user, idToUpdate);
    }

    public void remove(int userId) {
        jdbcUserDao.remove(userId);
    }
}
