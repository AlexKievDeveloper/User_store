package com.glushkov;

import com.glushkov.dao.jdbc.JdbcUserDao;
import com.glushkov.service.UserService;
import com.glushkov.web.handler.DefaultErrorHandler;
import com.glushkov.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.h2.jdbcx.JdbcDataSource;

import java.io.BufferedInputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws Exception {

        Properties properties = new Properties();

        BufferedInputStream propertiesBufferedInputStream = new BufferedInputStream(Starter.class.getResourceAsStream("/application.properties"));
        properties.load(propertiesBufferedInputStream);

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(properties.getProperty("jdbc.host"));
        dataSource.setUser(properties.getProperty("jdbc.user"));
        dataSource.setPassword(properties.getProperty("jdbc.password"));

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(properties.getProperty("CREATE_TABLE"));
        }

        JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource);

        UserService userService = new UserService(jdbcUserDao);

        AllUsersServlet allUsersServlet = new AllUsersServlet(userService);
        AddUserServlet addUserServlet = new AddUserServlet(userService);
        EditUserServlet editUserServlet = new EditUserServlet(userService);
        RemoveUserServlet removeUserServlet = new RemoveUserServlet(userService);
        SearchUserServlet searchUserServlet = new SearchUserServlet(userService);
        AllOtherRequestServlet allOtherRequestServlet = new AllOtherRequestServlet();

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setErrorHandler(new DefaultErrorHandler());

        servletContextHandler.addServlet(new ServletHolder(allUsersServlet), "/users");
        servletContextHandler.addServlet(new ServletHolder(addUserServlet), "/users/add");
        servletContextHandler.addServlet(new ServletHolder(editUserServlet), "/users/edit");
        servletContextHandler.addServlet(new ServletHolder(removeUserServlet), "/users/remove");
        servletContextHandler.addServlet(new ServletHolder(searchUserServlet), "/users/search");
        servletContextHandler.addServlet(new ServletHolder(allOtherRequestServlet), "/*");

        servletContextHandler.setWelcomeFiles(new String[]{"home.ftl"});

        Server server = new Server(8080);
        server.setHandler(servletContextHandler);
        server.start();
    }
}


//TODO
// 1) Не получается создать inmemory базу данных "jdbc: h2: mem: test; DB_CLOSE_DELAY = -1;INIT=runscript from './src/test/resources/h2-test-schema.sql';"
// с таблицей (create local temporary table  IF NOT EXISTS USERS): no suitable driver found
// 2) Гугл хром делает первую колонку шириной в пол экрана, а остальные прижимает к правому краю, не понимаю почему
// 3) Над методами которые мы переопределили нужно ли везде использовать аннотацию @Override или везде убрать?
// 4) Не удаётся подключиться к БД через интерфейс IntellijIdea
//        a) Тесты на 3 сервлета убрать мокито или интеграционную часть
//        b) Доработать H2 и создание базы данных