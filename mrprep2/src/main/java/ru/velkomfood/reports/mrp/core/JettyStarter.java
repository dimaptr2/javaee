package ru.velkomfood.reports.mrp.core;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.velkomfood.reports.mrp.model.DataBuffer;
import ru.velkomfood.reports.mrp.model.DbReader;
import ru.velkomfood.reports.mrp.view.DataTransformer;
import ru.velkomfood.reports.mrp.view.RestServlet;
import ru.velkomfood.reports.mrp.view.StartServlet;

public class JettyStarter {

    private final int PORT = 8087;
    private DbReader dbReader;

    public void setDbReader(DbReader dbReader) {
        this.dbReader = dbReader;
    }

    public void run() throws Exception {

        StartServlet servlet1 = new StartServlet();
        DataBuffer buffer = new DataBuffer();
        servlet1.setDataBuffer(buffer);
        servlet1.setDbReader(dbReader);

        RestServlet servlet2 = new RestServlet();
        servlet2.setDbReader(dbReader);

        DataTransformer dataTransformer = new DataTransformer();
        dataTransformer.setDbReader(dbReader);

        // Create a context of servlets
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(servlet1), "/mrp2");
        context.addServlet(new ServletHolder(servlet2), "/mrpapi");
        context.addServlet(new ServletHolder(dataTransformer), "/excel");

        // Add static resources to the server
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("public_html");
        resourceHandler.setRedirectWelcome(true);
        resourceHandler.setDirAllowed(false);
        resourceHandler.setWelcomeFiles(new String[] {"index.html"});
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {resourceHandler, context});

        Server server = new Server(PORT);
        server.setHandler(handlers);

        System.out.printf("Start Jetty Server on the port %d\n", PORT);
        server.start();
        server.join();
    }

}
