package com.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        // Configura el servidor Jetty en el puerto 8080
        Server server = new Server(8080);

        // Configura el contexto del servlet
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setResourceBase("src/main/webapp");
        server.setHandler(context);

        // Añade el servlet al contexto
        ServletHolder servletHolder = context.addServlet(HelloServlet.class, "/hello");
        servletHolder.setInitOrder(0);

        // Inicia el servidor
        server.start();
        server.join();
    }
}
