package com.glushkov.web.servlet;

import com.glushkov.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

class AllUsersServletTest {

    @Test
    @DisplayName("Processes the request and sends a response with content type, encoding and content(Users table)")
    void doGetTest() throws IOException {
        //prepare
        UserService mockUserService = mock(UserService.class);
        AllUsersServlet allUsersServlet = new AllUsersServlet(mockUserService);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        PrintWriter mockPrintWriter = mock(PrintWriter.class);

        when(mockResponse.getWriter()).thenReturn(mockPrintWriter);

        //when
        allUsersServlet.doGet(mockRequest, mockResponse);

        //then
        verify(mockResponse).setContentType("text/html;charset=utf-8");
        verify(mockResponse, times(1)).getWriter();
    }
}