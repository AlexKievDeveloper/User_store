package com.glushkov.web.servlets;


import com.glushkov.entities.User;
import com.glushkov.services.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class AddUserServlet extends HttpServlet {

    private UserService userService;

    public AddUserServlet(UserService userService) {
        this.userService = userService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String page = new String(Files.readAllBytes(Paths.get("templates/webapp/form.ftl")));
            response.getWriter().println(page);
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            //TODO 1) ioException / e   2) Выбрасывать ли RunTimeException, 3) задавать ли статус в Response
        } catch (IOException ioException) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException("Error while loading form", ioException);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setId(Integer.parseInt(request.getParameter("id")));
        user.setFirstName(request.getParameter("firstName"));
        user.setSecondName(request.getParameter("secondName"));
        user.setSalary(Double.parseDouble(request.getParameter("salary")));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
        user.setDateOfBirth(LocalDate.parse(request.getParameter("dateOfBirth"), formatter));

        userService.save(user);

        try {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("AllUserServlet");
            requestDispatcher.forward(request, response);
        }
        catch (IOException | ServletException e){
            throw new RuntimeException("Error while request dispatcher process", e);
        }
    }
}
