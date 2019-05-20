package ru.velkomfood.sap.xml.storage.controller;

import com.sap.conn.jco.*;
import ru.velkomfood.sap.xml.storage.behavior.ErpListener;
import ru.velkomfood.sap.xml.storage.model.Customer;

import java.util.Optional;

public class ErpListenerImpl implements ErpListener {

    private final String sapDestinationName;

    public ErpListenerImpl(String sapDestinationName) {
        this.sapDestinationName = sapDestinationName;
    }

    @Override
    public Optional<Customer> createCustomer(String customerId) throws JCoException {

        String textIdValue = alphaTransformation(customerId);
        JCoDestination destination = createDestination();
        Optional<Customer> optional = Optional.empty();

        if (destination.isValid()) {
            JCoFunction bapiCustomerList = destination.getRepository().getFunction("BAPI_CUSTOMER_GETLIST");
            JCoTable range = bapiCustomerList.getTableParameterList().getTable("IDRANGE");
            range.appendRow();
            range.setValue("SIGN", "I");
            range.setValue("OPTION", "EQ");
            range.setValue("LOW", textIdValue);
            range.setValue("HIGH", textIdValue);
            bapiCustomerList.execute(destination);
            JCoTable addrData = bapiCustomerList.getTableParameterList().getTable("ADDRESSDATA");
            if (addrData.getNumRows() > 0) {
                do {
                    optional = Optional
                            .of(new Customer(addrData.getLong("CUSTOMER"), addrData.getString("NAME")));
                } while (addrData.nextRow());
            }
        }

        return optional;
    }

    // private section

    private String alphaTransformation(String value) {

        StringBuilder sb = new StringBuilder(0);
        int tail = 10 - value.length();

        if (tail > 0) {
            for (int i = 1; i <= tail; i++) {
                sb.append("0");
            }
        }
        sb.append(value);

        return sb.toString();
    }

    private JCoDestination createDestination() throws JCoException {
        return JCoDestinationManager.getDestination(sapDestinationName);
    }

}
