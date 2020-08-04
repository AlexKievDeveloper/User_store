package com.glushkov;


import com.glushkov.dao.jdbc.JdbcUserDao;
import com.glushkov.service.UserService;
import com.glushkov.web.handler.ErrorHandler;
import com.glushkov.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.h2.jdbcx.JdbcDataSource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;


public class Starter {
    public static void main(String[] args) throws Exception {

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:file:~/src/main/resources/db.mv.db/user_store;MV_STORE=false");
        dataSource.setUser("h2");
        dataSource.setPassword("h2");

        try (Connection connection = dataSource.getConnection();
             Statement createTableStatement = connection.createStatement()) {

            String createTableQuery = new String(Files.readAllBytes(Paths.get("src/main/resources/h2-schema.sql")));
            createTableStatement.execute(createTableQuery);
        }

        JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource);

        UserService userService = new UserService(jdbcUserDao);

        AllUsersServlet allUsersServlet = new AllUsersServlet(userService);
        AddUserServlet addUserServlet = new AddUserServlet(userService);
        EditUserServlet editUserServlet = new EditUserServlet(userService);
        RemoveUserServlet removeUserServlet = new RemoveUserServlet(userService);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setResourceBase("src/main/resources/static");
        servletContextHandler.setErrorHandler(new ErrorHandler());

        servletContextHandler.addServlet(new ServletHolder(allUsersServlet), "/users");
        servletContextHandler.addServlet(new ServletHolder(addUserServlet), "/users/add");
        servletContextHandler.addServlet(new ServletHolder(editUserServlet), "/users/edit");
        servletContextHandler.addServlet(new ServletHolder(removeUserServlet), "/users/remove");
        servletContextHandler.addServlet(DefaultServlet.class, "/");

        Server server = new Server(8080);
        server.setHandler(servletContextHandler);
        server.addBean(new ErrorHandler());
        server.start();
    }
}

//TODO 1) После выключения Компьютера база данных не удаляется
// 2) Не могу изменить цвет шрифта в error.ftl  (h2)
// 3) Не получилось сделать Полноценные тесты на сервлеты потому что тестовая библиотека использует классы которые удалены
// 4) Во время тестирования ErrorHandler выводится стек трейс ошибки которая выбрасывается во время теста
// 5) Обдумать response.sendError(500, ex.getMessage()); (бросает IOException который будет проброшен) вместо throw new ServletException(ex);
// 6) Вывод стек трейса кроме моего Логгера ещё: 20:01:34.339[qtp1373419525-26]WARN  org.eclipse.jetty.server.HttpChannel - /users/add
// javax.servlet.ServletException: java.time.format.DateTimeParseException: Text '2001/06/12' could not be parsed at index 4