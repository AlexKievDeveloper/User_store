package com.glushkov.web.handler;

import org.eclipse.jetty.server.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultErrorHandlerTest {

    @Test
    @DisplayName("Generates a response to the client if an error occurs during request processing")
    void generateAcceptableResponse() throws IOException {
        //prepare
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler();

        Request mockBaseRequest = mock(Request.class);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        when(mockResponse.getWriter()).thenReturn(new PrintWriter(stringWriter));

        when(mockRequest.getAttribute("javax.servlet.error.servlet_name")).thenReturn("com.glushkov.servlet.AddUserServlet");
        when(mockRequest.getAttribute("javax.servlet.error.exception")).thenReturn(new IllegalArgumentException(new NumberFormatException()));

        int code = 500;
        String message = "IllegalArgumentException";
        String mimeType = "text/html";

        String expectedPage;
        try (InputStream inputStreamFromFile = new BufferedInputStream(getClass().getResourceAsStream("/PageForTestDefaultErrorHandler.html"))) {
            expectedPage = new String(inputStreamFromFile.readAllBytes());
        }

        //when
        defaultErrorHandler.generateAcceptableResponse(mockBaseRequest, mockRequest, mockResponse, code, message, mimeType);

        //then
        verify(mockBaseRequest).setHandled(true);
        verify(mockResponse).setContentType("text/html;charset=utf-8");
        verify(mockResponse).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        verify(mockResponse).getWriter();
        assertEquals(expectedPage, stringWriter.toString());
    }
}