package ru.velkomfood.sap.data.keeper.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogKeeperImpl implements LogKeeper {

    @Override
    public Logger createLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

}
