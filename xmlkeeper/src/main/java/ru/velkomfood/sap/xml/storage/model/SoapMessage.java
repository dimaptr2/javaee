package ru.velkomfood.sap.xml.storage.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class SoapMessage implements Serializable {

    private java.sql.Timestamp moment;
    private long customerId;
    private long providerId;
    private String messageType;
    private String xmlMessage;

    public SoapMessage() {
    }

    public SoapMessage(Timestamp moment, long customerId,
                       long providerId, String messageType, String xmlMessage) {
        this.moment = moment;
        this.customerId = customerId;
        this.providerId = providerId;
        this.messageType = messageType;
        this.xmlMessage = xmlMessage;
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

    public String getXmlMessage() {
        return xmlMessage;
    }

    public void setXmlMessage(String xmlMessage) {
        this.xmlMessage = xmlMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoapMessage message = (SoapMessage) o;
        return customerId == message.customerId &&
                providerId == message.providerId &&
                moment.equals(message.moment) &&
                messageType.equals(message.messageType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moment, customerId, providerId, messageType);
    }

    @Override
    public String toString() {
        return "SoapMessage{" +
                "moment=" + moment +
                ", customerId=" + customerId +
                ", providerId=" + providerId +
                ", messageType='" + messageType + '\'' +
                ", xmlMessage='" + xmlMessage + '\'' +
                '}';
    }

}
