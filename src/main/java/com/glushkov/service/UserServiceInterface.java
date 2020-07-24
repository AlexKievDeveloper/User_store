package com.glushkov.service;


import com.glushkov.entity.User;

import java.util.List;

public interface UserServiceInterface {

    List<User> getAll();

    void save(User user);

    User getById(int id);

    void update(User user, int idToUpdate);

    void remove(int userId);
}
