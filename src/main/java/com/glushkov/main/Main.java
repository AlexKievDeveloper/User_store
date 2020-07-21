package com.glushkov.main;

import com.glushkov.servlets.AddUserServlet;
import com.glushkov.servlets.AllUsersServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {

        AllUsersServlet allUsersServlet = new AllUsersServlet();
        AddUserServlet addUserServlet = new AddUserServlet();

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        servletContextHandler.addServlet(new ServletHolder(allUsersServlet), "/users");
        servletContextHandler.addServlet(new ServletHolder(addUserServlet), "/add");

        Server server = new Server(8080);
        server.setHandler(servletContextHandler);

        server.start();
    }
}
