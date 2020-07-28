package com.glushkov.daos.jdbc;


import com.glushkov.daos.UserDao;
import com.glushkov.daos.jdbc.mapper.UserRowMapper;
import com.glushkov.entities.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class JdbcUserDao implements UserDao {

    private static final String FIND_ALL = "SELECT id, \"firstName\", \"secondName\", salary, \"dateOfBirth\"" +
            "FROM public.users ORDER BY id;";

    private static final String SAVE = "INSERT INTO public.users(id, \"firstName\", \"secondName\", salary, \"dateOfBirth\")" +
            "VALUES (?, ?, ?, ?, ?);";

    private static final String FIND_BY_ID = "SELECT id, \"firstName\", \"secondName\", salary, \"dateOfBirth\"" +
            "FROM public.users WHERE id = ?";

    private static final String UPDATE = "UPDATE public.users SET id=?, \"firstName\"=?, \"secondName\"=?, salary=?, \"dateOfBirth\"=? WHERE id = ?;";

    private static final String DELETE = "DELETE FROM public.users WHERE id = ?;";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-d");

    private DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<User> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL)) {

            List<User> usersList = new ArrayList<>();
            UserRowMapper userRowMapper = new UserRowMapper();

            while (resultSet.next()) {
                User user = userRowMapper.userRowMapper(resultSet);
                usersList.add(user);
            }
            return usersList;

        } catch (SQLException e) {
            throw new RuntimeException("Can't show all users. ", e);
        }
    }

    public void save(User user) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getSecondName());
            preparedStatement.setDouble(4, user.getSalary());

            LocalDate createDate = LocalDate.parse(user.getDateOfBirth().toString(), DATE_TIME_FORMATTER);
            preparedStatement.setDate(5, Date.valueOf(createDate));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Can't add user to DB. User with ID: " + user.getId() + " already exists. " +
                    "Go back to the previous page and try again.", e);
        }
    }

    public User findById(int id) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);) {
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery();){

                resultSet.next();

                UserRowMapper userRowMapper = new UserRowMapper();
                User user = userRowMapper.userRowMapper(resultSet);

                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get user from DB.", e);
        }
    }

    public void update(User user, int idToUpdate) {
        LocalDate createDate = LocalDate.parse(user.getDateOfBirth().toString(), DATE_TIME_FORMATTER);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getSecondName());
            preparedStatement.setDouble(4, user.getSalary());
            preparedStatement.setDate(5, Date.valueOf(createDate));
            preparedStatement.setInt(6, idToUpdate);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Can't add update in DB ", e);
        }
    }

    public void delete(int userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Can't remove user from DB ", e);
        }
    }
}
