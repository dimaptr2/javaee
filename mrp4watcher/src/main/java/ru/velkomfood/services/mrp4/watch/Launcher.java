package ru.velkomfood.services.mrp4.watch;

import ru.velkomfood.services.mrp4.watch.config.WatcherFactory;
import ru.velkomfood.services.mrp4.watch.config.implementation.WatcherFactoryBase;
import ru.velkomfood.services.mrp4.watch.core.ServerInstanceBase;
import ru.velkomfood.services.mrp4.watch.repository.DataManager;
import ru.velkomfood.services.mrp4.watch.erp.ErpReader;

public class Launcher {

    public static void main(String[] args) {

        WatcherFactory factory = WatcherFactoryBase.create();
        factory.inject(DataManager.class);
        factory.inject(ErpReader.class);
        ServerInstanceBase server = factory.createServer();
        server.prepare();
        server.startUp();

    }

}
