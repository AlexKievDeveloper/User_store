package com.glushkov.dao.jdbc;


import com.glushkov.dao.UserDao;
import com.glushkov.dao.jdbc.mapper.UserRowMapper;
import com.glushkov.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcUserDao implements UserDao {

    private static final String FIND_ALL = "SELECT id, firstname, secondname, salary, dateofbirth FROM users;";

    private static final String SAVE = "INSERT INTO users(firstname, secondname, salary, dateofbirth) VALUES (?, ?, ?, ?);";

    private static final String FIND_BY_ID = "SELECT id, firstname, secondname, salary, dateofbirth FROM users WHERE id = ?";

    private static final String UPDATE = "UPDATE users SET firstname = ?, secondname = ?, salary = ? , dateofbirth = ? WHERE id = ?;";

    private static final String DELETE = "DELETE FROM users WHERE id = ?;";

    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<User> findAll() throws SQLException {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL)) {

            List<User> usersList = new ArrayList<>();

            while (resultSet.next()) {
                User user = USER_ROW_MAPPER.userRowMapper(resultSet);
                usersList.add(user);
            }
            return usersList;
        }
    }

    public void save(User user) throws SQLException {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setDouble(3, user.getSalary());
            preparedStatement.setDate(4, Date.valueOf(user.getDateOfBirth()));

            preparedStatement.executeUpdate();
        }
    }

    public User findById(int id) throws SQLException {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                resultSet.next();

                return USER_ROW_MAPPER.userRowMapper(resultSet);
            }
        }
    }

    public void update(User user) throws SQLException {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setDouble(3, user.getSalary());
            preparedStatement.setDate(4, Date.valueOf(user.getDateOfBirth()));
            preparedStatement.setInt(5, user.getId());

            preparedStatement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
