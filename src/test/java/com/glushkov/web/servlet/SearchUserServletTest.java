package com.glushkov.web.servlet;

import com.glushkov.entity.User;
import com.glushkov.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class SearchUserServletTest {

    @Test
    @DisplayName("Processes the request and sends a response with content type, encoding and content")
    void doGetTest() throws IOException {
        //prepare
        UserService mockUserService = mock(UserService.class);
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        SearchUserServlet searchUserServlet = new SearchUserServlet(mockUserService);
        PrintWriter mockPrintWriter = mock(PrintWriter.class);
        User user = new User();
        List<User> userList = new ArrayList<>();

        user.setId(1);
        user.setFirstName("Alex");
        user.setSecondName("Developer");
        user.setSalary(3000.0);
        user.setDateOfBirth(LocalDate.of(1993, 6, 22));

        userList.add(user);

        when(mockRequest.getParameter("enteredName")).thenReturn("Alex");
        when(mockUserService.findByName("Alex")).thenReturn(userList);
        when(mockResponse.getWriter()).thenReturn(mockPrintWriter);

        //when
        searchUserServlet.doGet(mockRequest, mockResponse);

        //then
        verify(mockRequest).getParameter("enteredName");
        verify(mockUserService).findByName("Alex");
        verify(mockResponse).setContentType("text/html;charset=utf-8");
        verify(mockResponse).getWriter();
        verify(mockPrintWriter).println(any(String.class));
    }
}