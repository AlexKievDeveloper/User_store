package com.glushkov.main;

import com.glushkov.servlets.ShowUsersServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {

        ShowUsersServlet showUsersServlet = new ShowUsersServlet();

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        servletContextHandler.addServlet(new ServletHolder(showUsersServlet), "/users");

        Server server = new Server(8080);
        server.setHandler(servletContextHandler);

        server.start();
    }
}
