package ru.velkomfood.sap.xml.storage.behavior;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface WebEngine {
    Logger LOGGER = LoggerFactory.getLogger(WebEngine.class);
    void startUp();
    void toRide();
}
