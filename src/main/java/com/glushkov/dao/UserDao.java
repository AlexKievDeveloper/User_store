package com.glushkov.dao;


import com.glushkov.entity.User;

import java.util.List;


public interface UserDao {

    User findById(int id);

    List<User> findByName(String name);

    List<User> findAll();

    void save(User user);

    void update(User user);

    void delete(int userId);


}
