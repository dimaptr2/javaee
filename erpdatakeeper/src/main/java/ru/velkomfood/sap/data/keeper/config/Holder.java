package ru.velkomfood.sap.data.keeper.config;

import com.google.gson.JsonObject;

public interface Holder {

    void readPropertiesFile(String fileName);
    String readPropertyByKey(String key);
    void addMessage(String address,JsonObject jsonObject);
    boolean messageQueueIsEmpty(String address);
    JsonObject readCurrentMessage(String address);

}
