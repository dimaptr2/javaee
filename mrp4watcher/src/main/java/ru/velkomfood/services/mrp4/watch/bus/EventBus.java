package ru.velkomfood.services.mrp4.watch.bus;

import ru.velkomfood.services.mrp4.watch.model.DataEntity;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EventBus {

    private static final EventBus instance = new EventBus();
    private final Map<String, Queue<DataEntity>> cache;

    private EventBus() {
        cache = new ConcurrentHashMap<>();
        cache.put("purchase.unit", new ConcurrentLinkedQueue<>());
        cache.put("uom", new ConcurrentLinkedQueue<>());
        cache.put("warehouse", new ConcurrentLinkedQueue<>());
        cache.put("material.master.data", new ConcurrentLinkedQueue<>());
        cache.put("material.master.details", new ConcurrentLinkedQueue<>());
        cache.put("stocks", new ConcurrentLinkedQueue<>());
        cache.put("requirements", new ConcurrentLinkedQueue<>());
    }

    public static EventBus eventBus() {
        return instance;
    }

    public int queueSize(String address) {
        return cache.get(address).size();
    }

    public void push(String address, DataEntity entity) {
        if (cache.containsKey(address) && entity != null) {
            cache.get(address).add(entity);
        }
    }

    public boolean queueIsEmpty(String address) {
        return cache.get(address).isEmpty();
    }

    public Optional<DataEntity> pull(String address) {

        if (!cache.containsKey(address) || cache.get(address).isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(cache.get(address).poll());
        }

    }

}
