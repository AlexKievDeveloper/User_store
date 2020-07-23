/*
package com.glushkov.servlets;

import com.glushkov.dao.JdbcUserDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@PrepareForTest(JdbcUserDao.class)
class AllUsersServletITest {

    @Test
    void doGet() throws Exception {
        //prepare
        String expected = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\"/>\n" +
                "    <title>Web 1</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table border=\"1\">\n" +
                "    <tr>\n" +
                "        <td width = 50px>Id</td>\n" +
                "        <td width = 150px>First name</td>\n" +
                "        <td width = 150px>Second name</td>\n" +
                "        <td width = 150px>Salary</td>\n" +
                "        <td width = 150px>Date of birth</td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>\n" +
                "\n" +
                "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\"/>\n" +
                "    <title>Web 1</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table border=\"1\">\n" +
                "    <tr>\n" +
                "        <td width = 50px>1</td>\n" +
                "        <td width = 150px>Alex</td>\n" +
                "        <td width = 150px>Hurgada</td>\n" +
                "        <td width = 150px>4 000,5</td>\n" +
                "        <td width = 150px>1999-09-24</td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>";

        Map<String, Object> tableHeaders = new HashMap<>();
        tableHeaders.put("id", "Id");
        tableHeaders.put("firstName", "First name");
        tableHeaders.put("secondName", "Second name");
        tableHeaders.put("salary", "Salary");
        tableHeaders.put("dateOfBirth", "Date of birth");

        Map<String, Object> userMap = new HashMap<>();
        tableHeaders.put("id", "1");
        tableHeaders.put("firstName", "Alex");
        tableHeaders.put("secondName", "Hurgada");
        tableHeaders.put("salary", "4 000.5");
        tableHeaders.put("dateOfBirth", "1999-09-24");

        List<Map<String, Object>> users = new ArrayList<>();
        users.add(tableHeaders);
        users.add(userMap);

        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);

        JdbcUserDao mockedJdbcUserDao = mock(JdbcUserDao.class);
        when(mockedJdbcUserDao.getAll()).thenReturn(users);
        PowerMockito.whenNew(JdbcUserDao.class).withAnyArguments().thenReturn(mockedJdbcUserDao);

        //нужно добавить подмену конструктора дждбсЮзерДао на mockedJdbcUserDao у которого переопределён гет олл

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(testResponse.getWriter()).thenReturn(pw);

        ShowUsersServlet showUsersServlet = new ShowUsersServlet();
        //when
        showUsersServlet.doGet(testRequest, testResponse);
        PowerMockito.verifyNew(JdbcUserDao.class, Mockito.times(1)).withNoArguments();

        String actual = sw.getBuffer().toString().trim();
        System.out.println(actual);

        //then
        assertEquals(expected, actual);
    }
}
*/
