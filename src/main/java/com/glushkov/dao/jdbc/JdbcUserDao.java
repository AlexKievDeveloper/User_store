package com.glushkov.dao.jdbc;


import com.glushkov.dao.UserDao;
import com.glushkov.dao.jdbc.mapper.UserRowMapper;
import com.glushkov.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcUserDao implements UserDao {

    private static final String FIND_ALL = "SELECT id, firstname, secondname, salary, dateofbirth FROM users;";

    private static final String SAVE = "INSERT INTO users(firstname, secondname, salary, dateofbirth) VALUES (?, ?, ?, ?);";

    private static final String FIND_BY_ID = "SELECT id, firstname, secondname, salary, dateofbirth FROM users WHERE id = ?;";

    private static final String FIND_BY_NAME = "SELECT id, firstname, secondname, salary, dateofbirth FROM users WHERE firstname LIKE ?;";

    private static final String UPDATE = "UPDATE users SET firstname = ?, secondname = ?, salary = ? , dateofbirth = ? WHERE id = ?;";

    private static final String DELETE = "DELETE FROM users WHERE id = ?;";

    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<User> findAll() {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL)) {

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
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setDouble(3, user.getSalary());
            preparedStatement.setDate(4, Date.valueOf(user.getDateOfBirth()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while saving user to DB", e);
        }
    }

    public User findById(int id) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                resultSet.next();

                if (!resultSet.isLast()) {
                    throw new RuntimeException("More than one row found for id: " + id);
                }

                return USER_ROW_MAPPER.mapRow(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Error while getting user from DB, id: {}", id, e);
            throw new RuntimeException(e);
        }
    }

    public List<User> findByName(String name){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                List<User> usersList = new ArrayList<>();
                while (resultSet.next()) {
                    User user = USER_ROW_MAPPER.mapRow(resultSet);
                    usersList.add(user);
                }

                return usersList;
            }
        } catch (SQLException e) {
            logger.error("Error while getting user from DB, name: {}", name, e);
            throw new RuntimeException(e);
        }
    }

    public void update(User user) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setDouble(3, user.getSalary());
            preparedStatement.setDate(4, Date.valueOf(user.getDateOfBirth()));
            preparedStatement.setInt(5, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while updating user in DB, id: {}", user.getId());
        }
    }

    public void delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while deleting user from DB, id: {}", id, e);
        }
    }
}
