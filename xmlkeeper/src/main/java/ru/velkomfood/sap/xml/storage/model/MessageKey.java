package ru.velkomfood.sap.xml.storage.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class MessageKey implements Serializable {

    private java.sql.Timestamp moment;
    private long customerId;
    private long providerId;
    private String messageType;

    public MessageKey() {
    }

    public MessageKey(Timestamp moment, long customerId, long providerId, String messageType) {
        this.moment = moment;
        this.customerId = customerId;
        this.providerId = providerId;
        this.messageType = messageType;
    }

    public Timestamp getMoment() {
        return moment;
    }

    public void setMoment(Timestamp moment) {
        this.moment = moment;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getProviderId() {
        return providerId;
    }

    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageKey key = (MessageKey) o;
        return customerId == key.customerId &&
                providerId == key.providerId &&
                moment.equals(key.moment) &&
                messageType.equals(key.messageType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moment, customerId, providerId, messageType);
    }

    @Override
    public String toString() {
        return "MessageKey{" +
                "moment=" + moment +
                ", customerId=" + customerId +
                ", providerId=" + providerId +
                ", messageType='" + messageType + '\'' +
                '}';
    }

}
