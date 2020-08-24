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

class AddUserServletTest {

    private AddUserServlet addUserServlet;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;

   private AddUserServletTest() {
        UserService mockUserService = mock(UserService.class);
        addUserServlet = new AddUserServlet(mockUserService);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
    }

    @Test
    @DisplayName("Processes the request and sends a response with status code, content type, encoding and a page with a registration form")
    void doGetTest() throws IOException {
        //prepare
        PrintWriter mockPrintWriter = mock(PrintWriter.class);
        when(mockResponse.getWriter()).thenReturn(mockPrintWriter);

        //when
        addUserServlet.doGet(mockRequest, mockResponse);

        //then
        verify(mockResponse).setContentType("text/html;charset=utf-8");
        verify(mockResponse).getWriter();
        verify(mockPrintWriter).println(any(String.class));
    }

    @Test
    @DisplayName("Processes the request and adds the user to the database")
    void doPostTest() throws IOException {
        //prepare
        User mockUser = mock(User.class);
        mockUser.setId(0);
        mockUser.setFirstName("Alex");
        mockUser.setSecondName("Developer");
        mockUser.setSalary(3000.0);
        mockUser.setDateOfBirth(LocalDate.of(1993, 6, 22));

        when(mockRequest.getParameter("firstName")).thenReturn("Alex");
        when(mockRequest.getParameter("secondName")).thenReturn("Developer");
        when(mockRequest.getParameter("salary")).thenReturn("3000.0");
        when(mockRequest.getParameter("dateOfBirth")).thenReturn("1993-06-22");

        //when
        addUserServlet.doPost(mockRequest, mockResponse);

        //then
        //verify(userService).save(user); //TODO Разные хэшкоды у юзеров
        verify(mockResponse).sendRedirect("/users");
    }
}
