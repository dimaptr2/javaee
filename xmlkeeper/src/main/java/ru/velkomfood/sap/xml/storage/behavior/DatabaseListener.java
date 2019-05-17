package ru.velkomfood.sap.xml.storage.behavior;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface DatabaseListener {

    Logger LOGGER = LoggerFactory.getLogger(DatabaseListener.class);

    void initialize();
}
