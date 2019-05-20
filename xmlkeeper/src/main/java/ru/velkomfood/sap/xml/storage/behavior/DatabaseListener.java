package ru.velkomfood.sap.xml.storage.behavior;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.velkomfood.sap.xml.storage.model.Customer;
import ru.velkomfood.sap.xml.storage.model.SoapMessage;

public interface DatabaseListener {

    Logger LOGGER = LoggerFactory.getLogger(DatabaseListener.class);
    void initialize();
    void createCustomerEntity(Customer customer);
    void createSoapMessageEntity(SoapMessage message);

}
