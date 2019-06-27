package ru.velkomfood.services.mrp4.watch.erp.base;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.velkomfood.services.mrp4.watch.Component;
import ru.velkomfood.services.mrp4.watch.bus.EventBus;
import ru.velkomfood.services.mrp4.watch.erp.ErpReader;

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
    public void readPlantInfo(JCoDestination destination) throws JCoException {

    }

    @Override
    public void readPurchaseUnits(JCoDestination destination) throws JCoException {

    }

    @Override
    public void readMaterials(JCoDestination destination) throws JCoException {

    }

}
