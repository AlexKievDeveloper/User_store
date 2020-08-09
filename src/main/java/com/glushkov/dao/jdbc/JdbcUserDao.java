package com.glushkov.dao.jdbc;


import com.glushkov.dao.UserDao;
import com.glushkov.dao.jdbc.generator.QueryGenerator;
import com.glushkov.dao.jdbc.mapper.UserRowMapper;
import com.glushkov.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class JdbcUserDao implements UserDao {

    private static final QueryGenerator QUERY_GENERATOR = new QueryGenerator();

    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<User> findAll() {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_GENERATOR.findAll(User.class))) {

            List<User> usersList = new ArrayList<>();

            while (resultSet.next()) {
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                usersList.add(user);
            }
            return usersList;
        } catch (SQLException e) {
            logger.error("Error while getting all users from DB", e);
            throw new RuntimeException(e);
        }
    }

    public void save(User user) {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(QUERY_GENERATOR.save(user));
        } catch (SQLException e) {
            logger.error("Error while saving user to DB", e);
        }
    }

    public User findById(int id) {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_GENERATOR.findByID(User.class, id))) {

            resultSet.next();

            if (!resultSet.isLast()) {
                throw new RuntimeException("More than one row found for id: " + id);
            }

            return USER_ROW_MAPPER.mapRow(resultSet);
        } catch (SQLException e) {
            logger.error("Error while getting user from DB, id: {}", id, e);
            throw new RuntimeException(e);
        }
    }

    public List<User> findByName(String name) {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_GENERATOR.findByName(User.class, name))) {

            List<User> usersList = new ArrayList<>();

            while (resultSet.next()) {
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                usersList.add(user);
            }

            return usersList;
        } catch (SQLException e) {
            logger.error("Error while getting user from DB, name: {}", name, e);
            throw new RuntimeException(e);
        }
    }

    public void update(User user) {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(QUERY_GENERATOR.update(user));
        } catch (SQLException e) {
            logger.error("Error while updating user in DB, id: {}", user.getId());
        }
    }

    public void delete(int id) {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(QUERY_GENERATOR.delete(User.class, id));
        } catch (SQLException e) {
            logger.error("Error while deleting user from DB, id: {}", id, e);
        }
    }
}
