package com.glushkov.web.handler;


import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class DefaultErrorHandlerTest {

    @Test
    void generateAcceptableResponse() throws IOException {
        //prepare
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler();

        Request mockBaseRequest = mock(Request.class);
        HttpServletRequest mockRequest = mock(Request.class);

        HttpChannel httpChannel = mock(HttpChannel.class);
        HttpOutput httpOutput = mock(HttpOutput.class);
        HttpServletResponse response = new Response(httpChannel, httpOutput);

        when(mockRequest.getAttribute("javax.servlet.error.servlet_name")).thenReturn("com.glushkov.servlet.AddUserServlet");
        when(mockRequest.getAttribute("javax.servlet.error.exception")).thenReturn(new IllegalArgumentException(new NumberFormatException()));

        int code = 500;
        String message = "IllegalArgumentException";
        String mimeType = "text/html";

        //when
        defaultErrorHandler.generateAcceptableResponse(mockBaseRequest, mockRequest, response, code, message, mimeType);

        //then
        assertEquals(500, response.getStatus());
        assertEquals("text/html;charset=utf-8", response.getContentType());
    }
}