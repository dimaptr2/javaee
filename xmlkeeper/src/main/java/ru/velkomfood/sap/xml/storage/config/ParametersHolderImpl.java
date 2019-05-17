package ru.velkomfood.sap.xml.storage.config;

import ru.velkomfood.sap.xml.storage.behavior.ParametersHolder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class ParametersHolderImpl implements ParametersHolder {

    @Override
    public Optional<Properties> read(String typeOfParameters) {

        String fileName = "";

        switch (typeOfParameters) {
            case "DB":
                fileName = "/db.properties";
                break;
            case "SERVER":
                fileName = "/server.properties";
                break;
        }

        Properties properties = new Properties();

        try (InputStream is = getClass().getResourceAsStream(fileName)) {
            properties.load(is);
        } catch (IOException ioe) {
            LOGGER.error(ioe.getMessage());
        }

        if (properties.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(properties);
        }

    }

}
