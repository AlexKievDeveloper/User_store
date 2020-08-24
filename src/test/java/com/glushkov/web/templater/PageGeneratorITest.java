package com.glushkov.web.templater;

import com.glushkov.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageGeneratorITest {

    @Test
    @DisplayName("Generates a page from html template and page variable values")
    void getPageTest() throws IOException {
        //prepare
        String expectedPage;
        try (InputStream inputStreamFromFile = new BufferedInputStream(getClass().getResourceAsStream("/PageForTestPageGenerator.html"))) {
            expectedPage = new String(inputStreamFromFile.readAllBytes());
        }

        User user = new User();
        user.setId(1);
        user.setFirstName("Anton");
        user.setSecondName("Kylaev");
        user.setSalary(2000.5);
        user.setDateOfBirth(LocalDate.of(1993, 8, 13));

        List<User> users = new ArrayList<>();
        users.add(user);
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("users", users);

        //when
        String actualPage = PageGenerator.instance().getPage("/page.html", pageVariables);

        //then
        assertEquals(expectedPage, actualPage);
    }
}