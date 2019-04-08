package ru.velkomfood.sap.tv.viewer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class InstanceBuilder {

    private static final InstanceBuilder instance = new InstanceBuilder();
    private final Properties applicationParameters;

    private InstanceBuilder() {
        applicationParameters = new Properties();
        readApplicationParameters(createLogger(getClass()));
    }

    public static InstanceBuilder create() {
        return instance;
    }

    // read properties

    private void readApplicationParameters(Logger logger) {

        try (InputStream is = getClass().getResourceAsStream("/app.properties")) {
            applicationParameters.load(is);
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
        }

    }

    // private section

    private Logger createLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

}
