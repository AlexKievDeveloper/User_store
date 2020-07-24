package com.glushkov.dao.jdbc;


import com.glushkov.entity.User;

import java.util.List;


public interface JdbcUserDaoInterface {

    void save(User user);

    List<User> getAll();

    User getById(int id);

    void update(User user, int idToUpdate);

    void remove(int userId);
}
