package ru.velkomfood.sap.xml.storage.config;

public class ServerCore {

    private static final ServerCore instance = new ServerCore();

    private ServerCore() {

    }

    public static ServerCore create() {
        return instance;
    }

}
