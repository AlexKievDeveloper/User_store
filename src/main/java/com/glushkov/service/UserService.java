package com.glushkov.service;

import com.glushkov.dao.UserDao;
import com.glushkov.entity.User;

import java.util.List;


public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void save(User user) {
        userDao.save(user);
    }

    public List<User>findByName(String name) { return userDao.findByName(name); }

    public User findById(int id) {
        return userDao.findById(id);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(int id) {
        userDao.delete(id);
    }
}
