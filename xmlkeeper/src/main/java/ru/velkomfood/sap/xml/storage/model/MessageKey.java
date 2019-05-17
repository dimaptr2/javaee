package ru.velkomfood.sap.xml.storage.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class MessageKey implements Serializable {

    private java.sql.Date date;
    private java.sql.Time time;
    private long customerId;
    private long providerId;
    private String messageType;

    public MessageKey() {
    }

    public MessageKey(Date date, Time time,
                      long customerId, long providerId, String messageType) {
        this.date = date;
        this.time = time;
        this.customerId = customerId;
        this.providerId = providerId;
        this.messageType = messageType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
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
        MessageKey that = (MessageKey) o;
        return customerId == that.customerId &&
                providerId == that.providerId &&
                date.equals(that.date) &&
                time.equals(that.time) &&
                messageType.equals(that.messageType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, customerId, providerId, messageType);
    }

    @Override
    public String toString() {
        return "MessageKey{" +
                "date=" + date +
                ", time=" + time +
                ", customerId=" + customerId +
                ", providerId=" + providerId +
                ", messageType='" + messageType + '\'' +
                '}';
    }

}
