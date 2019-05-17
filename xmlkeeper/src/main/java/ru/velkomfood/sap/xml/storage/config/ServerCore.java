package ru.velkomfood.sap.xml.storage.config;

import ru.velkomfood.sap.xml.storage.behavior.DatabaseListener;
import ru.velkomfood.sap.xml.storage.behavior.ParametersHolder;
import ru.velkomfood.sap.xml.storage.behavior.WebEngine;
import ru.velkomfood.sap.xml.storage.controller.DatabaseListenerImpl;
import ru.velkomfood.sap.xml.storage.controller.WebEngineImpl;

import java.util.Properties;

public class ServerCore {

    private static final ServerCore instance = new ServerCore();
    private final ParametersHolder parametersHolder;
    private final DatabaseListener databaseListener;
    private final WebEngine webEngine;

    private ServerCore() {

        parametersHolder = new ParametersHolderImpl();
        Properties dbParameters = parametersHolder.read("DB").orElse(null);
        if (dbParameters == null) {
            dbParameters = new Properties();
        }
        databaseListener = new DatabaseListenerImpl(dbParameters);
        databaseListener.initialize();
        webEngine = new WebEngineImpl(
                parametersHolder.read("SERVER").orElse(null),
                databaseListener
        );

    }
    // Create a single instance
    public static ServerCore create() {
        return instance;
    }

    // Run this server instance
    public void run() {
        webEngine.startUp();
        webEngine.toRide();
    }

}
