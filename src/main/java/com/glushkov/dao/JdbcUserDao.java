package com.glushkov.dao;


import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUserDao implements UserDao {

    private static final String GET_ALL = "SELECT id, \"firstName\", \"secondName\", salary, \"dateOfBirth\"\n" +
            "FROM public.users ORDER BY id;";

    private static final String SAVE_TRANSACTION_TO_DB = "INSERT INTO public.users(id, \"firstName\", \"secondName\", salary, \"dateOfBirth\")\n" +
            "VALUES (?, ?, ?, ?, ?);";

    private static String GET_BY_ID = "SELECT id, \"firstName\", \"secondName\", salary, \"dateOfBirth\"\n" +
            "FROM public.users WHERE id = ?";

    private static String UPDATE_TEST = "UPDATE public.users SET id=?, \"firstName\"=?, \"secondName\"=?, salary=?, \"dateOfBirth\"=? WHERE id = ?;";

    private static String REMOVE = "DELETE FROM public.users WHERE id = ?;";

    private DefaultDataSource dataSource;

    public JdbcUserDao(DefaultDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Map<String, Object>> getAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL)) {

            List<Map<String, Object>> usersList = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Object> userMap = new UserRowMapper().userRowMapper(resultSet);
                usersList.add(userMap);
            }
            return usersList;

        } catch (SQLException sqlException) {
            throw new RuntimeException("Can't show all users. ", sqlException);
        }
    }

    public void save(Map<String, Object> userMap) {
        try (Connection connection = dataSource.getConnection();) {

            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_TRANSACTION_TO_DB);
            preparedStatement.setInt(1, Integer.parseInt(userMap.get("id").toString()));
            preparedStatement.setString(2, userMap.get("firstName").toString());
            preparedStatement.setString(3, userMap.get("secondName").toString());
            preparedStatement.setDouble(4, Double.parseDouble(userMap.get("salary").toString()));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            LocalDate createDate = LocalDate.parse(userMap.get("dateOfBirth").toString(), formatter);
            Date date = Date.valueOf(createDate);
            preparedStatement.setDate(5, date);

            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            throw new RuntimeException("Can't add user to DB ", sqlException);
        }
    }

    public Map<String, Object> getById(String id) {

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setInt(1, Integer.parseInt(id));
             ResultSet resultSet = preparedStatement.executeQuery();
             resultSet.next();
            return new UserRowMapper().userRowMapper(resultSet);

        } catch (SQLException sqlException) {
            throw new RuntimeException("Can't get user from DB.", sqlException);
        }
    }

    public void update(Map<String, Object> userMap, int idToUpdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        LocalDate createDate = LocalDate.parse(userMap.get("dateOfBirth").toString(), formatter);
        Date date = Date.valueOf(createDate);

        try (Connection connection = dataSource.getConnection();) {

            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TEST);
            preparedStatement.setInt(1, Integer.parseInt(userMap.get("id").toString()));
            preparedStatement.setString(2, userMap.get("firstName").toString());
            preparedStatement.setString(3, userMap.get("secondName").toString());
            preparedStatement.setDouble(4, Double.parseDouble(userMap.get("salary").toString()));
            preparedStatement.setDate(5, date);
            preparedStatement.setInt(6, idToUpdate);

            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            throw new RuntimeException("Can't add update in DB ", sqlException);
        }
    }

    public void remove(int userId){
        try (Connection connection = dataSource.getConnection();) {

            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            throw new RuntimeException("Can't remove user from DB ", sqlException);
        }
    }

    static class UserRowMapper {
        public Map<String, Object> userRowMapper(ResultSet resultSet) {

            Map<String, Object> userMap = new HashMap<>();

            try {
                userMap.put("id", resultSet.getInt("id"));
                userMap.put("firstName", resultSet.getString("firstName"));
                userMap.put("secondName", resultSet.getString("secondName"));
                userMap.put("salary", resultSet.getDouble("salary"));
                Timestamp dateOfBirth = resultSet.getTimestamp("dateOfBirth");
                userMap.put("dateOfBirth", dateOfBirth.toLocalDateTime().toLocalDate());

                return userMap;
            } catch (SQLException sqlException) {
                throw new RuntimeException("Can`t get value from result set. ", sqlException);
            }
        }
    }
}
