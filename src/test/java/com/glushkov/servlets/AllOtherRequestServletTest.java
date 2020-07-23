package com.glushkov.servlets;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class AllOtherRequestServletTest {

    @Test
    void readContentITest() throws IOException {
        //prepare
        Path path = Paths.get("templates/webapp/favicon.ico");
        byte[] expected = Files.readAllBytes(path);
        AllOtherRequestServlet allOtherRequestServlet = new AllOtherRequestServlet();

        //when
        byte[] actualFirst = allOtherRequestServlet.readContent("/favicon.ico").readAllBytes();
        byte[] actualSecond = allOtherRequestServlet.readContent("/webapp/favicon.ico").readAllBytes();

        //then
        for (int i = 0; i < expected.length-1; i++) {
            assertEquals(expected[i], actualFirst[i]);
            assertEquals(expected[i], actualSecond[i]);
        }
    }
}