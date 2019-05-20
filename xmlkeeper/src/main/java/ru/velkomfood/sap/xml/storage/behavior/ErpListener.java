package ru.velkomfood.sap.xml.storage.behavior;

import com.sap.conn.jco.JCoException;
import ru.velkomfood.sap.xml.storage.model.Customer;

import java.util.Optional;

public interface ErpListener {

    Optional<Customer> createCustomer(String customerId) throws JCoException;

}
