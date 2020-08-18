package com.glushkov;

import com.glushkov.dao.jdbc.JdbcUserDao;
import com.glushkov.service.UserService;
import com.glushkov.web.handler.DefaultErrorHandler;
import com.glushkov.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

public class Starter {

    public static void main(String[] args) throws Exception {
        PropertyReader propertyReader = new PropertyReader();
        Properties properties = propertyReader.getProperties();

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(properties.getProperty("db.url"));
        dataSource.setUser(properties.getProperty("db.user"));
        dataSource.setPassword(properties.getProperty("db.password"));

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()){
            statement.execute(properties.getProperty("CREATE_TABLE"));
        }

        JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource);

        UserService userService = new UserService(jdbcUserDao);

        AllUsersServlet allUsersServlet = new AllUsersServlet(userService);
        AddUserServlet addUserServlet = new AddUserServlet(userService);
        EditUserServlet editUserServlet = new EditUserServlet(userService);
        RemoveUserServlet removeUserServlet = new RemoveUserServlet(userService);
        SearchUserServlet searchUserServlet = new SearchUserServlet(userService);
        StaticResourcesRequestServlet staticResourcesRequestServlet = new StaticResourcesRequestServlet();

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setErrorHandler(new DefaultErrorHandler());

        servletContextHandler.addServlet(new ServletHolder(allUsersServlet), "/users");
        servletContextHandler.addServlet(new ServletHolder(addUserServlet), "/users/add");
        servletContextHandler.addServlet(new ServletHolder(editUserServlet), "/users/edit");
        servletContextHandler.addServlet(new ServletHolder(removeUserServlet), "/users/remove");
        servletContextHandler.addServlet(new ServletHolder(searchUserServlet), "/users/search");
        servletContextHandler.addServlet(new ServletHolder(staticResourcesRequestServlet), "/*");

        servletContextHandler.setWelcomeFiles(new String[]{"home.html"});

        Server server = new Server(Integer.parseInt(properties.getProperty("port")));
        server.setHandler(servletContextHandler);
        server.start();
    }
}

