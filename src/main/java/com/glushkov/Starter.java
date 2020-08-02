package com.glushkov;


import com.glushkov.dao.jdbc.JdbcUserDao;
import com.glushkov.service.UserService;
import com.glushkov.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.h2.jdbcx.JdbcDataSource;



public class Starter {
    public static void main(String[] args) throws Exception {

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:file:~/src/test/resources/db.mv.db/user_store;MV_STORE=false");
        dataSource.setUser("h2");
        dataSource.setPassword("h2");

        JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource);

        UserService userService = new UserService(jdbcUserDao);

        AllUsersServlet allUsersServlet = new AllUsersServlet(userService);
        AddUserServlet addUserServlet = new AddUserServlet(userService);
        EditUserServlet editUserServlet = new EditUserServlet(userService);
        RemoveUserServlet removeUserServlet = new RemoveUserServlet(userService);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setResourceBase("src/main/resources/static");

        servletContextHandler.addServlet(new ServletHolder(allUsersServlet), "/users");
        servletContextHandler.addServlet(new ServletHolder(addUserServlet), "/users/add");
        servletContextHandler.addServlet(new ServletHolder(editUserServlet), "/users/edit");
        servletContextHandler.addServlet(new ServletHolder(removeUserServlet), "/users/remove");
        servletContextHandler.addServlet(DefaultServlet.class, "/");

        Server server = new Server(8080);
        server.setHandler(servletContextHandler);
        server.start();
    }
}
