package ru.velkomfood.grizzly.edi.port.core;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.jaxws.JaxwsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.velkomfood.grizzly.edi.port.endpoints.SapWsPoint;
import ru.velkomfood.grizzly.edi.port.provider.KonRequestor;

import java.io.IOException;

public class SoapServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoapServer.class);
    private HttpServer httpServer;
    private KonRequestor requestor;

    public void setRequestor(KonRequestor requestor) {
        this.requestor = requestor;
    }

    public SoapServer() {
        httpServer = new HttpServer();
    }

    public void startUp() {

        NetworkListener listener = new NetworkListener("jaxws-listener", "0.0.0.0", 49102);
        HttpHandler httpHandlerKo = new JaxwsHandler(new SapWsPoint(requestor));
        httpServer.getServerConfiguration().addHttpHandler(httpHandlerKo, "/ediko");
        httpServer.addListener(listener);

        try {
            httpServer.start();
            Thread.currentThread().join();
        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage());
        }

    }

    public void shutdown() {
        httpServer.shutdownNow();
    }

}
