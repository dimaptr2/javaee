package ru.velkomfood.services.mrp4.watch.erp.base;

import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.velkomfood.services.mrp4.watch.Component;
import ru.velkomfood.services.mrp4.watch.bus.EventBus;
import ru.velkomfood.services.mrp4.watch.erp.ErpReader;
import ru.velkomfood.services.mrp4.watch.model.master.Measure;
import ru.velkomfood.services.mrp4.watch.model.master.PurchaseGroup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ErpReaderBase implements ErpReader {

    private final Logger logger;
    private Properties sapProperties;
    private EventBus eventBus;

    public ErpReaderBase() {
        logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public Component readSettings() {

        sapProperties = new Properties();

        try (InputStream is = getClass().getResourceAsStream("/sap.properties")) {
            sapProperties.load(is);
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
        }

        return this;
    }

    @Override
    public void rollInBus(EventBus eventBus) {
       this.eventBus = eventBus;
    }

    @Override
    public JCoDestination createDestination() throws JCoException {
        return JCoDestinationManager.getDestination(sapProperties.getProperty("destination"));
    }

    @Override
    public void readPurchaseUnits(JCoDestination destination) throws JCoException {

        JCoFunction rfcReadTable = destination.getRepository().getFunction("RFC_READ_TABLE");

        rfcReadTable.getImportParameterList().setValue("QUERY_TABLE", "T024");
        rfcReadTable.getImportParameterList().setValue("DELIMITER", ";");
        JCoTable fields = rfcReadTable.getTableParameterList().getTable("FIELDS");
        fields.appendRow();
        fields.setValue("FIELDNAME", "EKGRP");
        fields.appendRow();
        fields.setValue("FIELDNAME", "EKNAM");

        rfcReadTable.execute(destination);

        JCoTable dataTable = rfcReadTable.getTableParameterList().getTable("DATA");
        if (dataTable.getNumRows() > 0) {
            do {
                String[] columnValues = parseStringIntoArray(dataTable.getString("WA"));
                if (columnValues.length > 0) {
                    PurchaseGroup pg = new PurchaseGroup(columnValues[0], columnValues[1]);
                    eventBus.push("purchase.unit", pg);
                }
            } while (dataTable.nextRow());
        }

        fields.clear();
        dataTable.clear();

    }

    @Override
    public void readUnitsOfMeasure(JCoDestination destination) throws JCoException {

        JCoFunction rfcReadTable = destination.getRepository().getFunction("RFC_READ_TABLE");
        rfcReadTable.getImportParameterList().setValue("QUERY_TABLE", "T006A");
        rfcReadTable.getImportParameterList().setValue("DELIMITER", ";");

        JCoTable options = rfcReadTable.getTableParameterList().getTable("OPTIONS");
        options.appendRow();
        options.setValue("TEXT", "SPRAS = \'RU\'");
        JCoTable fields = rfcReadTable.getTableParameterList().getTable("FIELDS");
        fields.appendRow();
        fields.setValue("FIELDNAME", "MSEHI");
        fields.appendRow();
        fields.setValue("FIELDNAME", "MSEHL");

        rfcReadTable.execute(destination);

        JCoTable data = rfcReadTable.getTableParameterList().getTable("DATA");
        if (data.getNumRows() > 0) {
            do {
                String[] columnValues = parseStringIntoArray(data.getString("WA"));
                if (columnValues.length > 0) {
                    Measure measure = new Measure(columnValues[0], columnValues[1]);
                    eventBus.push("uom", measure);
                }
            } while (data.nextRow());
        }

        fields.clear();
        data.clear();

    }

    @Override
    public void readWarehouse(JCoDestination destination) throws JCoException {

    }

    @Override
    public void readMaterials(JCoDestination destination) throws JCoException {

    }

    // private section

    private String[] parseStringIntoArray(String value) {
        return value.split(";");
    }

}
