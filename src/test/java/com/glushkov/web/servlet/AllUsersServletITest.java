package com.glushkov.web.servlet;


import com.glushkov.service.UserService;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.Response;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class AllUsersServletITest {

    @Test
    void doGetTest() throws ServletException {
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