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
            "FROM public.users;";

    private static final String SAVE_TRANSACTION_TO_DB = "INSERT INTO public.users(id, \"firstName\", \"secondName\", salary, \"dateOfBirth\")\n" +
            "VALUES (?, ?, ?, ?, ?);";

    private DefaultDataSource dataSource;

    public JdbcUserDao(DefaultDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Map<String, Object>> getAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL)) {

            List<Map<String, Object>> usersList = new ArrayList<>();
            Map<String, Object> tableHeaders = new HashMap<>();
            tableHeaders.put("id", "Id");
            tableHeaders.put("firstName", "First name");
            tableHeaders.put("secondName", "Second name");
            tableHeaders.put("salary", "Salary");
            tableHeaders.put("dateOfBirth", "Date of birth");

            usersList.add(tableHeaders);

            while (resultSet.next()) {
                Map<String, Object> userMap = new UserRowMapper().userRowMapper(resultSet);
                usersList.add(userMap);
            }
            return usersList;

        } catch (SQLException e) {
            throw new RuntimeException("Can't show all transactions. ", e);
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

        } catch (SQLException e) {
            throw new RuntimeException("Can't add transaction to DB ", e);
        }
    }

    protected static class UserRowMapper {
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
