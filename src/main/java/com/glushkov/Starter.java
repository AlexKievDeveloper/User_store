package com.glushkov;


import com.glushkov.dao.jdbc.JdbcUserDao;
import com.glushkov.service.UserService;
import com.glushkov.web.handler.DefaultErrorHandler;
import com.glushkov.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.h2.jdbcx.JdbcDataSource;


public class Starter {
    public static void main(String[] args) throws Exception {

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:file:~/src/main/resources/db/user_store.h2.db;INIT=runscript from 'src/main/resources/h2-schema.sql';");
        dataSource.setUser("h2");
        dataSource.setPassword("h2");

        JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource);

        UserService userService = new UserService(jdbcUserDao);

        AllUsersServlet allUsersServlet = new AllUsersServlet(userService);
        AddUserServlet addUserServlet = new AddUserServlet(userService);
        EditUserServlet editUserServlet = new EditUserServlet(userService);
        RemoveUserServlet removeUserServlet = new RemoveUserServlet(userService);
        SearchUserServlet searchUserServlet = new SearchUserServlet(userService);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setResourceBase("src/main/resources/static");
        servletContextHandler.setErrorHandler(new DefaultErrorHandler());

        servletContextHandler.addServlet(new ServletHolder(allUsersServlet), "/users");
        servletContextHandler.addServlet(new ServletHolder(addUserServlet), "/users/add");
        servletContextHandler.addServlet(new ServletHolder(editUserServlet), "/users/edit");
        servletContextHandler.addServlet(new ServletHolder(removeUserServlet), "/users/remove");
        servletContextHandler.addServlet(new ServletHolder(searchUserServlet), "/users/search");
        servletContextHandler.addServlet(DefaultServlet.class, "/");

        Server server = new Server(8080);
        server.setHandler(servletContextHandler);
        server.start();
    }
}

//TODO 1)В папках db постоянное пишет loading. Предполагаю что причина может быть в DBVizualizer но это не точно
// 2) Не могу изменить цвет шрифта в error.ftl (message: h1->h2 - yellow)
// 3) Не получилось сделать Полноценные тесты на сервлеты потому что тестовая библиотека использует классы которые удалены,
// а в своих тестах не могу разобраться с protected final ByteArrayOutputStream output = new ByteArrayOutputStream();, System.setOut(new PrintStream(output));
// System.setOut(null); и получить в работу поток байт в response
// 4) В форме в поле для даты все плейсхолдера выводиться дд.мм.гггг - язык берётся у браузера нужно вывести на английском
// 5) Во время тестирования ErrorHandler выводится стек трейс ошибки которая выбрасывается во время теста
// 6) Вывод стек трейса кроме моего Логгера и ещё: 20:01:34.339[qtp1373419525-26]WARN  org.eclipse.jetty.server.HttpChannel - /users/add
// javax.servlet.ServletException: java.time.format.DateTimeParseException:
// 7) Обдумать response.sendError(500, ex.getMessage()); (бросает IOException который будет проброшен) вместо throw new ServletException(ex);