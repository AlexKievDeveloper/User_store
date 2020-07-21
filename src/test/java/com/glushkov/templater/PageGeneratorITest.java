package com.glushkov.templater;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PageGeneratorITest {

    @Test
    void getPage() {
        //prepare
        String expectedPage =
                "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\"/>\n" +
                "    <title>Table users</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table border=\"1\">\n" +
                "    <tr>\n" +
                "        <td width = 50px>1</td>\n" +
                "        <td width = 150px>Anton</td>\n" +
                "        <td width = 150px>Kylaev</td>\n" +
                "        <td width = 150px>2 000,5</td>\n" +
                "        <td width = 150px>1993-08-13</td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>\n\n";

        Map<String, Object> user = new HashMap<>();
        user.put("id", 1);
        user.put("firstName", "Anton");
        user.put("secondName", "Kylaev");
        user.put("salary", 2000.5);
        user.put("dateOfBirth", LocalDate.of(1993, 8, 13));

        List<Map<String, Object>> users = new ArrayList<>();
        users.add(user);

        //when
        String actualPage = PageGenerator.instance().getPage("page.html", users);
        //then
        assertEquals(expectedPage, actualPage);
    }
}