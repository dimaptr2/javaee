package ru.velkomfood.mrp.book.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MrpLoggerFactory {

    public Logger createLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

}
