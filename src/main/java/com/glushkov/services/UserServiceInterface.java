package com.glushkov.services;


import com.glushkov.entities.User;

import java.util.List;

public interface UserServiceInterface {

    List<User> findAll();

    void save(User user);

    User findById(int id);

    void update(User user, int idToUpdate);

    void delete(int userId);
}
