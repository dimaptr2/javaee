package ru.velkomfood.edi.info.webreport;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;

import java.io.IOException;

public class GrizzlyMammy {

    private String host;
    private int port;

    private HttpServer server;

    public GrizzlyMammy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void prepare() {
        server = HttpServer.createSimpleServer("/", port);
        // Add static resources
        StaticHttpHandler staticHttpHandler = new StaticHttpHandler("webapp");
        staticHttpHandler.setFileCacheEnabled(false);

        server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/*");
    }

    public void startUp() throws IOException {
        server.start();

    }

    public void shutdown() {
        server.shutdownNow();
    }

}
