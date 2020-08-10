package com.glushkov.dao.jdbc.generator;

import com.glushkov.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryGeneratorTest {
    @Test
    @DisplayName("Generates a sql query to the database to get the values of all rows for all the listed columns")
    void findAllTest() {
        //prepare
        QueryGenerator queryGenerator = new QueryGenerator();
        String expectedQuery = "SELECT id, firstname, secondname, salary, dateofbirth FROM users;";
        //when
        String actualQuery = queryGenerator.findAll(User.class);
        //then
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("Generates a sql query to the database to put a row with listed values to listed columns")
    void saveTest() {
        //prepare
        QueryGenerator queryGenerator = new QueryGenerator();

        User user = new User();
        user.setFirstName("Kirill");
        user.setSecondName("Mavrody");
        user.setSalary(2000.0);
        LocalDate dateOfBirth = LocalDate.of(1993, 6, 23);
        user.setDateOfBirth(dateOfBirth);

        String expectedQuery = "INSERT INTO users (secondname, dateofbirth, firstname, salary) VALUES ('Mavrody', '1993-06-23', 'Kirill', 2000.0);";
        //when
        String actualQuery = queryGenerator.save(user);
        //then
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("Generates a sql query to the database to put listed values to all listed columns for one row by primary key")
    void updateTest() {
        //prepare
        QueryGenerator queryGenerator = new QueryGenerator();

        User user = new User();
        user.setId(1);
        user.setFirstName("Kirill");
        user.setSecondName("Mavrody");
        user.setSalary(2000.0);
        LocalDate dateOfBirth = LocalDate.of(1993, 6, 23);
        user.setDateOfBirth(dateOfBirth);

        String expectedQuery = "UPDATE users SET secondname = 'Mavrody', dateofbirth = '1993-06-23', firstname = 'Kirill', salary = 2000.0 WHERE id = '1';";
        //when
        String actualQuery = queryGenerator.update(user);
        //then
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("Generates a sql query to the database to get values of all listed columns for one row dy primary key")
    void findByIDTest() {
        //prepare
        QueryGenerator queryGenerator = new QueryGenerator();
        String expectedQuery = "SELECT id, firstname, secondname, salary, dateofbirth FROM users WHERE id = '1';";
        //when
        String actualQuery = queryGenerator.findByID(User.class, 1);
        //then
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("Generates a sql query to the database to delete all values of one row by primary key")
    void deleteTest() {
        //prepare
        QueryGenerator queryGenerator = new QueryGenerator();
        String expectedQuery = "DELETE FROM users WHERE id = '1';";
        //when
        String actualQuery = queryGenerator.delete(User.class, 1);
        //then
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("Returns the name of the table from either annotation or class name")
    void getTableNameTest() {
        //prepare
        String expected = "users";
        //when
        String actual = QueryGenerator.getTableName(User.class);
        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Returns a map with all field values exclude primary key")
    void getFieldsValuesTest() {
        //prepare
        User user = new User();
        user.setFirstName("Kirill");
        user.setSecondName("Mavrody");
        user.setSalary(2000.0);
        LocalDate dateOfBirth = LocalDate.of(1993, 6, 23);
        user.setDateOfBirth(dateOfBirth);

        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("firstname", "'Kirill'");
        expectedMap.put("secondname", "'Mavrody'");
        expectedMap.put("salary", 2000.0);
        expectedMap.put("dateofbirth", "'1993-06-23'");
        //when
        Map<String, Object> actualMap = QueryGenerator.getFieldsValues(user);
        //then
        assertEquals(expectedMap.toString(), actualMap.toString());
    }

    @Test
    @DisplayName("Returns a map with one entry where key it is a name of the primary key column and value it is a primary key value")
    void getPrimaryKeyTest() {
        //prepare
        User user = new User();
        user.setId(1);
        user.setFirstName("Kirill");
        user.setSecondName("Mavrody");
        user.setSalary(2000.0);
        LocalDate dateOfBirth = LocalDate.of(1993, 6, 23);
        user.setDateOfBirth(dateOfBirth);

        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("id", "1");

        QueryGenerator queryGenerator = new QueryGenerator();

        //when
        Map<String, Object> actualMap = queryGenerator.getPrimaryKey(user);
        //then
        assertEquals(expectedMap.toString(), actualMap.toString());
    }
}

