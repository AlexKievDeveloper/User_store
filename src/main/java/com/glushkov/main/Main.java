package com.glushkov.main;

import com.glushkov.servlets.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {

        AllUsersServlet allUsersServlet = new AllUsersServlet();
        AddUserServlet addUserServlet = new AddUserServlet();
        EditUserServlet editUserServlet = new EditUserServlet();
        RemoveUserServlet removeUserServlet = new RemoveUserServlet();
        AllOtherRequestServlet allOtherRequestServlet = new AllOtherRequestServlet();

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        servletContextHandler.addServlet(new ServletHolder(allUsersServlet), "/users");
        servletContextHandler.addServlet(new ServletHolder(addUserServlet), "/users/add");
        servletContextHandler.addServlet(new ServletHolder(editUserServlet), "/users/edit");
        servletContextHandler.addServlet(new ServletHolder(removeUserServlet), "/users/remove");
        servletContextHandler.addServlet(new ServletHolder(allOtherRequestServlet), "/*");

        Server server = new Server(8080);
        server.setHandler(servletContextHandler);

        server.start();
    }
}
