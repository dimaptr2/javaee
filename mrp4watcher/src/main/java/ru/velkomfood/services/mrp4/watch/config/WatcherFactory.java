package ru.velkomfood.services.mrp4.watch.config;

import ru.velkomfood.services.mrp4.watch.core.ServerInstanceBase;

public interface WatcherFactory {
    void inject(Class<?> clazz);
    ServerInstanceBase createServer();
}
