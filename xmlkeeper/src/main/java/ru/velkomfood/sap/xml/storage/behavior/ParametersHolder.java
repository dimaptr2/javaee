package ru.velkomfood.sap.xml.storage.behavior;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Properties;

public interface ParametersHolder {
    Logger LOGGER = LoggerFactory.getLogger(ParametersHolder.class);
    Optional<Properties> read(String typeOfParameters);
}
