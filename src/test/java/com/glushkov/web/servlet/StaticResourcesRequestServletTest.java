package com.glushkov.web.servlet;

import org.eclipse.jetty.server.HttpOutput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StaticResourcesRequestServletTest {

    @Test
    @DisplayName("Processes the request and write file to response")
    void doGetTest() throws IOException {
        //prepare
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        StaticResourcesRequestServlet resourcesServlet = new StaticResourcesRequestServlet();

        HttpOutput mockHttpOutput = mock(HttpOutput.class);

        when(mockRequest.getRequestURI()).thenReturn("/favicon.ico");
        when(mockResponse.getOutputStream()).thenReturn(mockHttpOutput);

        //when
        resourcesServlet.doGet(mockRequest, mockResponse);

        //then TODO сделать проверку контента который попадает в mockHttpOutput
        verify(mockRequest).getRequestURI();
    }

    @Test
    @DisplayName("Returns input stream of bytes from the specified path")
    void readContentTest() throws IOException {
        //prepare
        byte[] expected = getClass().getResourceAsStream("/static/home.html").readAllBytes();
        StaticResourcesRequestServlet resourcesServlet = new StaticResourcesRequestServlet();

        //when
        byte[] actualFirst = resourcesServlet.readContent("/home.html").readAllBytes();
        byte[] actualSecond = resourcesServlet.readContent("/").readAllBytes();

        //then
        for (int i = 0; i < expected.length - 1; i++) {
            assertEquals(expected[i], actualFirst[i]);
            assertEquals(expected[i], actualSecond[i]);
        }
    }
}