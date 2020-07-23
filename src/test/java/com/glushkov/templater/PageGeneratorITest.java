package com.glushkov.templater;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PageGeneratorITest {

    @Test
    void getPage() throws IOException {
        //prepare
        Path path = Paths.get("src/test/java/com/glushkov/templater/PageForTestPageGenerator.html");
        byte[] bytes = Files.readAllBytes(path);
        String expectedPage = new String(bytes);

        Map<String, Object> user = new HashMap<>();
        user.put("id", 1);
        user.put("firstName", "Anton");
        user.put("secondName", "Kylaev");
        user.put("salary", 2000.5);
        user.put("dateOfBirth", LocalDate.of(1993, 8, 13));

        List<Map<String, Object>> users = new ArrayList<>();
        users.add(user);
        Map<String, Object> input = new HashMap<>();
        input.put("users", users);

        //when
        String actualPage = PageGenerator.instance().getPage("page.ftl", input);
        //then
        assertEquals(expectedPage, actualPage);
    }
}