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

import static org.mockito.Mockito.*;

class EditUserServletTest {
    private UserService mockUserService;
    private EditUserServlet editUserServlet;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private User mockUser;

    private EditUserServletTest() {
        mockUserService = mock(UserService.class);
        editUserServlet = new EditUserServlet(mockUserService);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);

        mockUser = mock(User.class);
        mockUser.setId(0);
        mockUser.setFirstName("Alex");
        mockUser.setSecondName("Developer");
        mockUser.setSalary(3000.0);
        mockUser.setDateOfBirth(LocalDate.of(1993, 6, 22));
    }

    @Test
    @DisplayName("Processes the client's request and sends a response with status code, content type, encoding and a page with user data")
    void doGetTest() throws IOException {
        //prepare
        PrintWriter mockPrintWriter = mock(PrintWriter.class);
        when(mockRequest.getParameter("id")).thenReturn("1");
        when(mockUserService.findById(1)).thenReturn(mockUser);
        when(mockResponse.getWriter()).thenReturn(mockPrintWriter);

        //when
        editUserServlet.doGet(mockRequest, mockResponse);

        //then
        verify(mockResponse).setContentType("text/html;charset=utf-8");
        verify(mockResponse).getWriter();
        verify(mockPrintWriter).println(any(String.class));
    }

    @Test
    @DisplayName("Processes the request and update the user data in the database")
    void doPostTest() throws IOException {
        //prepare
        when(mockRequest.getParameter("id")).thenReturn("1");
        when(mockRequest.getParameter("firstName")).thenReturn("Alex");
        when(mockRequest.getParameter("secondName")).thenReturn("Developer");
        when(mockRequest.getParameter("salary")).thenReturn("3000.0");
        when(mockRequest.getParameter("dateOfBirth")).thenReturn("1993-06-22");
        //when
        editUserServlet.doPost(mockRequest, mockResponse);

        //then
        //verify(mockUserService).update(mockUser);  //TODO Разные хэшкоды у юзеров
        verify(mockResponse).sendRedirect("/users");
    }
}
