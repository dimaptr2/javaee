package ru.velkomfood.mrp.book.server.controller;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.velkomfood.mrp.book.server.config.MrpLoggerFactory;
import ru.velkomfood.mrp.book.server.config.ParametersHolder;

import javax.annotation.PostConstruct;

@Service
public class ErpReader {

    private final ParametersHolder parametersHolder;
    private final DatabaseManager databaseManager;
    private final MrpLoggerFactory mrpLoggerFactory;
    private Logger logger;

    @Autowired
    public ErpReader(ParametersHolder parametersHolder, DatabaseManager databaseManager, MrpLoggerFactory mrpLoggerFactory) {
        this.parametersHolder = parametersHolder;
        this.databaseManager = databaseManager;
        this.mrpLoggerFactory = mrpLoggerFactory;
        logger = this.mrpLoggerFactory.createLogger(getClass());
    }

    public void readMasterData() {

        try {
            final JCoDestination destination = createDestination();
        } catch (JCoException jcoEx) {
            logger.error(jcoEx.getMessage());
        }

    }

    public void readTransactionData() {

        try {
            final JCoDestination destination = createDestination();
        } catch (JCoException jcoEx) {
            logger.error(jcoEx.getMessage());
        }

    }
    // private section

    private JCoDestination createDestination() throws JCoException {
        return JCoDestinationManager.getDestination(parametersHolder.getSapQueryParameters().get("destination"));
    }

}
