package ru.velkomfood.sap.data.keeper.config;

import org.slf4j.Logger;

public interface LogKeeper {
    Logger createLogger(Class<?> clazz);
}
