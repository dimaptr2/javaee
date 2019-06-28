package ru.velkomfood.services.mrp4.watch.core;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.velkomfood.services.mrp4.watch.Component;
import ru.velkomfood.services.mrp4.watch.erp.ErpReader;
import ru.velkomfood.services.mrp4.watch.repository.DataManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerInstanceBase {

    private final Logger logger;
    private final ScheduledExecutorService scheduler;
    private DataManager dataManager;
    private ErpReader erpReader;
    private Runnable readingTaskStructure, readingTaskMaterial;
    private Runnable writingTaskPurchaseGroup, writingTaskUom;
    private Runnable writingTaskWarehouse, writingTaskMaterial;

    public ServerInstanceBase() {
        logger = LoggerFactory.getLogger(getClass());
        scheduler = Executors.newScheduledThreadPool(16);
    }

    public void createServerComponent(Component component) {

        if (component instanceof DataManager) {
            dataManager = (DataManager) component;
        } else if (component instanceof ErpReader) {
            erpReader = (ErpReader) component;
        }

    }

    // Initialize the runnable tasks
    public void prepare() {

        readingTaskStructure = () -> {
            try {
                JCoDestination destination = erpReader.createDestination();
                JCoContext.begin(destination);
                erpReader.readPurchaseUnits(destination);
//                erpReader.readUnitsOfMeasure(destination);
                erpReader.readWarehouse(destination);
                JCoContext.end(destination);
                logger.info("Organization structure was read");
            } catch (JCoException ex) {
                logger.error(ex.getMessage());
            }
        };

        readingTaskMaterial = () -> {
            try {
                JCoDestination destination = erpReader.createDestination();
                JCoContext.begin(destination);
                erpReader.readMaterials(destination);
                logger.info("Materials was read");
                JCoContext.end(destination);
            } catch (JCoException ex) {
                logger.error(ex.getMessage());
            }
        };

        writingTaskPurchaseGroup = () -> {
            dataManager.saveData("purchase.unit");
            logger.info("Purchase units created");
        };

        writingTaskUom = () -> {
            dataManager.saveData("uom");
            logger.info("Units of measure created");
        };

        writingTaskWarehouse = () -> {
            dataManager.saveData("warehouse");
            logger.info("Warehouses created");
        };

        writingTaskMaterial = () -> {
            dataManager.saveData("material.master.data");
            dataManager.saveData("material.master.details");
            logger.info("Materials data created");
        };

    }

    // Create the scheduled tasks
    public void startUp() {

        scheduler.scheduleAtFixedRate(readingTaskStructure, 0, 45, TimeUnit.MINUTES);
//        scheduler.scheduleAtFixedRate(writingTaskPurchaseGroup, 1, 2, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(writingTaskUom, 1, 20, TimeUnit.MINUTES);

    }

}
