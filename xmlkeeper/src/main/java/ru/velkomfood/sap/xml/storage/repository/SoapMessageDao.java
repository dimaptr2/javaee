package ru.velkomfood.sap.xml.storage.repository;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ru.velkomfood.sap.xml.storage.behavior.DAO;
import ru.velkomfood.sap.xml.storage.model.MessageKey;
import ru.velkomfood.sap.xml.storage.model.SoapMessage;

import java.util.List;
import java.util.Optional;

public class SoapMessageDao implements DAO<SoapMessage, MessageKey> {

    private final Sql2o sqlEngine;

    public SoapMessageDao(Sql2o sqlEngine) {
        this.sqlEngine = sqlEngine;
    }

    @Override
    public boolean exists(SoapMessage message) {

        java.sql.Timestamp moment = message.getMoment();
        long customerId = message.getCustomerId();
        long providerId = message.getProviderId();
        String messageType = message.getMessageType();
        long counter;

        try (Connection connection = sqlEngine.open()) {
            counter = connection.createQuery(QUERY.COUNT_MESSAGES.label)
                    .addParameter("moment", moment)
                    .addParameter("customerId", customerId)
                    .addParameter("providerId", providerId)
                    .addParameter("messageType", messageType)
                    .executeScalar(Long.class);
        }

        if (counter > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void create(SoapMessage message) {

        java.sql.Timestamp moment = message.getMoment();
        long customerId = message.getCustomerId();
        long providerId = message.getProviderId();
        String messageType = message.getMessageType();
        String xmlMessage = message.getXmlMessage();

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.CREATE_MESSAGE.label)
                    .addParameter("moment", moment)
                    .addParameter("customerId", customerId)
                    .addParameter("providerId", providerId)
                    .addParameter("messageType", messageType)
                    .addParameter("xmlMessage", xmlMessage)
                    .executeUpdate();
        }

    }

    @Override
    public Optional<SoapMessage> findOne(MessageKey messageKey) {

        Optional<SoapMessage> optionalSoapMessage;

        try (Connection connection = sqlEngine.open()) {
            java.sql.Timestamp moment = messageKey.getMoment();
            long customerId = messageKey.getCustomerId();
            long providerId = messageKey.getProviderId();
            String messageType = messageKey.getMessageType();
            SoapMessage message = connection.createQuery(QUERY.READ_MESSAGE.label)
                    .addParameter("moment", moment)
                    .addParameter("customerId", customerId)
                    .addParameter("providerId", providerId)
                    .addParameter("messageType", messageType)
                    .executeScalar(SoapMessage.class);
            optionalSoapMessage = Optional.of(message);
        }

        return optionalSoapMessage;
    }

    @Override
    public List<SoapMessage> findByKeyBetween(MessageKey fromKey, MessageKey toKey) {

        List<SoapMessage> messages;

        try (Connection connection = sqlEngine.open()) {
            // low values
            java.sql.Timestamp momentLow = fromKey.getMoment();
            long customerIdLow = fromKey.getCustomerId();
            long providerIdLow = fromKey.getProviderId();
            String messageTypeLow = fromKey.getMessageType();
            // high values
            java.sql.Timestamp momentHigh = toKey.getMoment();
            long customerIdHigh = toKey.getCustomerId();
            long providerIdHigh = toKey.getProviderId();
            String messageTypeHigh = toKey.getMessageType();
            messages = connection.createQuery(QUERY.READ_MESSAGES_BETWEEN.label)
                    .addParameter("momentLow", momentLow)
                    .addParameter("momentHigh", momentHigh)
                    .addParameter("customerIdLow", customerIdLow)
                    .addParameter("customerIdHigh", customerIdHigh)
                    .addParameter("providerIdLow", providerIdLow)
                    .addParameter("providerIdHigh", providerIdHigh)
                    .addParameter("messageTypeLow", messageTypeLow)
                    .addParameter("messageTypeHigh", messageTypeHigh)
                    .executeAndFetch(SoapMessage.class);
        }

        return messages;
    }

    @Override
    public List<SoapMessage> findAll() {

        try (Connection connection = sqlEngine.open()) {
            return connection.createQuery(QUERY.READ_ALL.label)
                    .executeAndFetch(SoapMessage.class);
        }

    }

    @Override
    public void update(SoapMessage message) {

        try (Connection connection = sqlEngine.open()) {
            String xmlMessage = message.getXmlMessage();
            java.sql.Timestamp moment = message.getMoment();
            long customerId = message.getCustomerId();
            long providerId = message.getProviderId();
            String messageType = message.getMessageType();
            connection.createQuery(QUERY.UPDATE_MESSAGE.label)
                    .addParameter("xmlMessage", xmlMessage)
                    .addParameter("moment", moment)
                    .addParameter("customerId", customerId)
                    .addParameter("providerId", providerId)
                    .addParameter("messageType", messageType)
                    .executeUpdate();
        }

    }

    @Override
    public void delete(SoapMessage message) {

        try (Connection connection = sqlEngine.open()) {
            java.sql.Timestamp moment = message.getMoment();
            long customerId = message.getCustomerId();
            long providerId = message.getProviderId();
            String messageType = message.getMessageType();
            connection.createQuery(QUERY.DELETE_MESSAGE.label)
                    .addParameter("moment", moment)
                    .addParameter("customerId", customerId)
                    .addParameter("providerId", providerId)
                    .addParameter("messageType", messageType)
                    .executeUpdate();
        }

    }

    // private section

    private enum QUERY {

        COUNT_MESSAGES("SELECT COUNT( * ) soap_message WHERE moment = :moment" +
                " AND customer_id = :customerId" +
                " AND provider_id = :providerId" +
                " AND msg_type = :messageType"),
        CREATE_MESSAGE("INSERT INTO soap_message" +
                " VALUES (:moment, :customerId, :providerId, :messageType, :xmlMessage)"),
        READ_MESSAGE("SELECT * FROM soap_message WHERE moment = :moment" +
                " AND customer_id = :customerId" +
                " AND provider_id = :providerId" +
                " AND msg_type = :messageType"),
        READ_MESSAGES_BETWEEN("SELECT * FROM soap_message" +
                " WHERE moment BETWEEN :momentLow AND :momentHigh" +
                " AND customer_id BETWEEN :customerIdLow AND :customerIdHigh" +
                " AND provider_id BETWEEN :providerIdLow AND :providerIdHigh" +
                " AND msg_type BETWEEN :messageTypeLow AND messageTypeHigh" +
                " ORDER BY moment, customer_id, provider_id, msg_type"),
        READ_ALL("SELECT * FROM soap_message ORDER BY moment, customer_id, provider_id"),
        UPDATE_MESSAGE("UPDATE soap_message SET xml_message = :xmlMessage" +
                " WHERE moment = :moment" +
                " AND customer_id = :customerId" +
                " AND provider_id = :providerId" +
                " AND msg_type = :messageType"),
        DELETE_MESSAGE("DELETE FROM soap_message WHERE moment = :moment" +
                " AND customer_id = :customerId" +
                " AND provider_id = :providerId" +
                " AND msg_type = :messageType");

        private String label;

        QUERY(String label) {
            this.label = label;
        }

    }

}
