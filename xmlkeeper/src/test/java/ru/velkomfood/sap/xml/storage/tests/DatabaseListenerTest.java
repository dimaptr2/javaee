package ru.velkomfood.sap.xml.storage.tests;

import org.junit.Before;
import org.junit.Test;
import ru.velkomfood.sap.xml.storage.behavior.DatabaseListener;
import ru.velkomfood.sap.xml.storage.behavior.ErpListener;
import ru.velkomfood.sap.xml.storage.behavior.ParametersHolder;
import ru.velkomfood.sap.xml.storage.config.ParametersHolderImpl;
import ru.velkomfood.sap.xml.storage.controller.DatabaseListenerImpl;
import ru.velkomfood.sap.xml.storage.controller.ErpListenerImpl;
import ru.velkomfood.sap.xml.storage.model.Customer;
import ru.velkomfood.sap.xml.storage.model.SoapMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.stream.Stream;

public class DatabaseListenerTest {

    private DatabaseListener db;
    private ErpListener erp;

    @Before
    public void preStart() {

//        ParametersHolder holder = new ParametersHolderImpl();
//        Properties params = holder.read("DB").orElse(null);
//        if (params != null) {
//            db = new DatabaseListenerImpl(params);
//            db.initialize();
//        }
//        erp = new ErpListenerImpl("R14");

    }

    @Test
    public void runTest() {

//        try (Stream<String> stream = Files.lines(Paths.get("testing/soap.txt"))) {
//            stream.forEach(line -> {
//                String[] data = line.split(";");
//                java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(LocalDateTime.now());
//                long customerId = Long.valueOf(data[0]);
//                Customer customer = new Customer(customerId, String.valueOf(customerId));
//                db.createCustomerEntity(customer);
//                long providerId = Long.valueOf(data[1]);
//                String messageType = data[2];
//                String xmlMessage = data[3];
//                SoapMessage soapMessage = new SoapMessage(timestamp, customerId, providerId, messageType, xmlMessage);
//                db.saveSoapMessageEntity(soapMessage);
//            });
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }

    }

}
