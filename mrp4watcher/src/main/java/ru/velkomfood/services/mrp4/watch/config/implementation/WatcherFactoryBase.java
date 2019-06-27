package ru.velkomfood.services.mrp4.watch.config.implementation;

import ru.velkomfood.services.mrp4.watch.Component;
import ru.velkomfood.services.mrp4.watch.bus.EventBus;
import ru.velkomfood.services.mrp4.watch.config.WatcherFactory;
import ru.velkomfood.services.mrp4.watch.core.ServerInstanceBase;
import ru.velkomfood.services.mrp4.watch.repository.DataManager;
import ru.velkomfood.services.mrp4.watch.repository.base.DataManagerBase;
import ru.velkomfood.services.mrp4.watch.erp.ErpReader;
import ru.velkomfood.services.mrp4.watch.erp.base.ErpReaderBase;

public class WatcherFactoryBase implements WatcherFactory {

    private static final WatcherFactory instance = new WatcherFactoryBase();
    private final EventBus eventBus;
    private final ServerInstanceBase serverInstance;

    private WatcherFactoryBase() {
        eventBus = EventBus.eventBus();
        serverInstance = new ServerInstanceBase();
    }

    public static WatcherFactory create() {
        return instance;
    }

    @Override
    public void inject(Class<?> clazz) {
        serverInstance.createServerComponent(createComponent(clazz));
    }

    @Override
    public ServerInstanceBase createServer() {
        return serverInstance;
    }

// private section

    private Component createComponent(Class<?> clazz) {

        if (clazz.equals(DataManager.class)) {
            var dm = (DataManager) new DataManagerBase().readSettings();
            dm.rollInBus(eventBus);
            dm.configure();
            return dm;
        } else if (clazz.equals(ErpReader.class)) {
            var erp = (ErpReader) new ErpReaderBase().readSettings();
            erp.rollInBus(eventBus);
            return erp;
        } else {
            return null;
        }

    }

}
