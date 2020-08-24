package com.glushkov.web.servlet;

import com.glushkov.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

class RemoveUserServletTest {

    @Test
    @DisplayName("Processes the request and removes the user from the database by primary key")
    void doPostTest() throws IOException {
        //prepare
        UserService mockUserService = mock(UserService.class);
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        RemoveUserServlet removeUserServlet = new RemoveUserServlet(mockUserService);

        when(mockRequest.getParameter("id")).thenReturn("1");

        //when
        removeUserServlet.doPost(mockRequest, mockResponse);

        //then
        verify(mockUserService).delete(1);
        verify(mockResponse).sendRedirect("/users");
    }
}