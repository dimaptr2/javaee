package ru.velkomfood.sap.data.keeper.config;

import com.google.gson.JsonObject;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class HolderImpl implements Holder {

    private final Logger logger;
    private final Map<String, String> globalParameters;
    private final Map<String, Queue<JsonObject>> messageQueueMap;

    public HolderImpl(Logger logger) {

        this.logger = logger;
        globalParameters = new ConcurrentHashMap<>();
        messageQueueMap = new ConcurrentHashMap<>();
        messageQueueMap.put("read.status", new ConcurrentLinkedDeque<>());

    }

    @Override
    public void readPropertiesFile(String fileName) {

        Properties parametersFromFile = new Properties();

        try (InputStream is = getClass().getResourceAsStream(fileName)) {
            parametersFromFile.load(is);
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
        }

        for (Map.Entry<Object, Object> entry : parametersFromFile.entrySet()) {
            globalParameters.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }

    }

    @Override
    public String readPropertyByKey(String key) {
        return globalParameters.get(key);
    }

    @Override
    public void addMessage(String address, JsonObject jsonObject) {

        if (messageQueueMap.containsKey(address)) {
            if (messageQueueMap.get(address) == null) {
                Queue<JsonObject> messageQueue = new ConcurrentLinkedDeque<>();
                messageQueue.add(jsonObject);
                messageQueueMap.replace(address, messageQueue);
            } else if (!messageQueueMap.get(address).contains(jsonObject)) {
                messageQueueMap.get(address).add(jsonObject);
            }
        } else {
            Queue<JsonObject> queue = new ConcurrentLinkedDeque<>();
            queue.add(jsonObject);
            messageQueueMap.put(address, queue);
        }

    }

    @Override
    public boolean messageQueueIsEmpty(String address) {
        return messageQueueMap.get(address).isEmpty();
    }

    @Override
    public JsonObject readCurrentMessage(String address) {
        return messageQueueMap.get(address).poll();
    }

}
