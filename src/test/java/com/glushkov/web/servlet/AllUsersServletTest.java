package com.glushkov.web.servlet;

import com.glushkov.service.UserService;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class AllUsersServletTest {

    @Test
    @DisplayName("Processes the client's request and sends a response with valid status code, content type, encoding and content(Users table)")
    void doGetTest() throws IOException {
        //prepare

        UserService mockUserService = mock(UserService.class);
        AllUsersServlet allUsersServlet = new AllUsersServlet(mockUserService);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpChannel mockHttpChannel = mock(HttpChannel.class);
        HttpOutput mockHttpOutput = mock(HttpOutput.class);

        HttpServletResponse response = new Response(mockHttpChannel, mockHttpOutput);

        //when
        allUsersServlet.doGet(mockRequest, response);

        //then
        assertEquals(200, response.getStatus());
        assertEquals("text/html;charset=utf-8", response.getContentType());
    }
}