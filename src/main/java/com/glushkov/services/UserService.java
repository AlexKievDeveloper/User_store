package com.glushkov.services;

import com.glushkov.daos.jdbc.JdbcUserDao;
import com.glushkov.entities.User;

import java.util.List;

public class UserService implements UserServiceInterface {

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

    public void update(User user, int idToUpdate) {
        jdbcUserDao.update(user, idToUpdate);
    }

    public void delete(int userId) {
        jdbcUserDao.delete(userId);
    }
}
