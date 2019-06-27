package ru.velkomfood.services.mrp4.watch;

import ru.velkomfood.services.mrp4.watch.bus.EventBus;

public interface Component {
    Component readSettings();
    void rollInBus(EventBus eventBus);
}
