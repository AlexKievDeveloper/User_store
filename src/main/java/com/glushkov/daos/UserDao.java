package com.glushkov.daos;


import com.glushkov.entities.User;

import java.util.List;


public interface UserDao {

    User findById(int id);

    List<User> findAll();

    void save(User user);

    void update(User user, int idToUpdate);

    void delete(int userId);
}
