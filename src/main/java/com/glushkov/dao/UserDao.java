package com.glushkov.dao;


import java.util.List;
import java.util.Map;


public interface UserDao {
    void save(Map<String, Object> transactionsList);

    List<Map<String, Object>> getAll();
}
