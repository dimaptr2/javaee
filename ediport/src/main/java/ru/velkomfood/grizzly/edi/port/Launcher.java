package ru.velkomfood.grizzly.edi.port;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.velkomfood.grizzly.edi.port.core.SoapServer;
import ru.velkomfood.grizzly.edi.port.db.DbMachine;
import ru.velkomfood.grizzly.edi.port.provider.KonRequestor;

import java.sql.SQLException;

public class Launcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {


        PropertiesReader propertiesReader = PropertiesReader.createInstance();

        DbMachine dbMachine = DbMachine.create();
        dbMachine.setPropertiesReader(propertiesReader);

        try {

            dbMachine.initDatabaseStatus();
            KonRequestor requestor = KonRequestor.create();
            requestor.setPropertiesReader(propertiesReader);
            requestor.setDbMachine(dbMachine);
            requestor.prepare();

            SoapServer server = new SoapServer();
            server.setRequestor(requestor);
            server.startUp();

        } catch (SQLException dbe) {

            LOGGER.error(dbe.getMessage());

        }

    }

}
