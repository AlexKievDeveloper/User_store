package com.glushkov.dao.jdbc;


import com.glushkov.dao.jdbc.mapper.UserRowMapper;
import com.glushkov.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class JdbcUserDao implements JdbcUserDaoInterface {

    private static final String GET_ALL = "SELECT id, \"firstName\", \"secondName\", salary, \"dateOfBirth\"\n" +
            "FROM public.users ORDER BY id;";

    private static final String SAVE_TRANSACTION_TO_DB = "INSERT INTO public.users(id, \"firstName\", \"secondName\", salary, \"dateOfBirth\")\n" +
            "VALUES (?, ?, ?, ?, ?);";

    private static String GET_BY_ID = "SELECT id, \"firstName\", \"secondName\", salary, \"dateOfBirth\"\n" +
            "FROM public.users WHERE id = ?";

    private static String UPDATE_TEST = "UPDATE public.users SET id=?, \"firstName\"=?, \"secondName\"=?, salary=?, \"dateOfBirth\"=? WHERE id = ?;";

    private static String REMOVE = "DELETE FROM public.users WHERE id = ?;";

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

    private DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<User> getAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL)) {

            List<User> usersList = new ArrayList<>();
            UserRowMapper userRowMapper = new UserRowMapper();

            while (resultSet.next()) {
                User user = userRowMapper.userRowMapper(resultSet);
                usersList.add(user);
            }
            return usersList;

        } catch (SQLException sqlException) {
            throw new RuntimeException("Can't show all users. ", sqlException);
        }
    }

    public void save(User user) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_TRANSACTION_TO_DB)) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getSecondName());
            preparedStatement.setDouble(4, user.getSalary());

            LocalDate createDate = LocalDate.parse(user.getDateOfBirth().toString(), formatter);
            preparedStatement.setDate(5, Date.valueOf(createDate));

            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            throw new RuntimeException("Can't add user to DB. User with ID: " + user.getId() + " already exists. " +
                    "Go back to the previous page and try again.", sqlException);
        }
    }

    public User getById(int id) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID);) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            UserRowMapper userRowMapper = new UserRowMapper();
            User user = userRowMapper.userRowMapper(resultSet);

            resultSet.close();
            return user;

        } catch (SQLException sqlException) {
            throw new RuntimeException("Can't get user from DB.", sqlException);
        }
    }

    public void update(User user, int idToUpdate) {
        LocalDate createDate = LocalDate.parse(user.getDateOfBirth().toString(), formatter);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TEST)) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getSecondName());
            preparedStatement.setDouble(4, user.getSalary());
            preparedStatement.setDate(5, Date.valueOf(createDate));
            preparedStatement.setInt(6, idToUpdate);

            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            throw new RuntimeException("Can't add update in DB ", sqlException);
        }
    }

    public void remove(int userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            throw new RuntimeException("Can't remove user from DB ", sqlException);
        }
    }
}
