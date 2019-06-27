package ru.velkomfood.services.mrp4.watch.core;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.velkomfood.services.mrp4.watch.Component;
import ru.velkomfood.services.mrp4.watch.erp.ErpReader;
import ru.velkomfood.services.mrp4.watch.repository.DataManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ServerInstanceBase {

    private final Logger logger;
    private final ScheduledExecutorService scheduler;
    private DataManager dataManager;
    private ErpReader erpReader;
    private Runnable readingTaskStructure, readingTaskMaterial;
    private Runnable writingTaskPlant, writingTaskPurchaseGroup, writingTaskMaterial;

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
                erpReader.readPlantInfo(destination);
                erpReader.readPurchaseUnits(destination);
                logger.info("Organization structure was read");
            } catch (JCoException ex) {
                logger.error(ex.getMessage());
            }
        };

        readingTaskMaterial = () -> {
            try {
                JCoDestination destination = erpReader.createDestination();
                erpReader.readMaterials(destination);
                logger.info("Materials was read");
            } catch (JCoException ex) {
                logger.error(ex.getMessage());
            }
        };

        writingTaskPlant = () -> {
            dataManager.saveData("plant");
        };

        writingTaskPurchaseGroup = () -> {
            dataManager.saveData("purchase.unit");
        };

        writingTaskMaterial = () -> {
            dataManager.saveData("material.master.data");
            dataManager.saveData("material.master.details");
        };

    }

    public void startUp() {

    }

}
