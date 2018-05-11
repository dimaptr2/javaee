package ru.velkomfood.grizzly.edi.port.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.velkomfood.api.edi.kontur.connector.Runner;
import ru.velkomfood.api.edi.kontur.connector.RunnerImpl;
import ru.velkomfood.grizzly.edi.port.PropertiesReader;
import ru.velkomfood.grizzly.edi.port.db.DbMachine;

import java.io.IOException;

public class KonRequestor {

    private static final Logger LOG = LoggerFactory.getLogger(KonRequestor.class);
    private static final KonRequestor instance = new KonRequestor();

    private PropertiesReader propertiesReader;
    private Runner runner;
    private DbMachine dbMachine;

    public static KonRequestor create() {
        return instance;
    }

    public void setPropertiesReader(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    public void setDbMachine(DbMachine dbMachine) {
        this.dbMachine = dbMachine;
    }

    public void prepare() {

        try {
            runner = RunnerImpl.createInstance()
                    .newEDIPartner("kontur")
                    .createTokenDirectory("tokens")
                    .createTokenFile("voken.txt")
                    .createPropertiesFromResourceFile()
                    .prepare()
                    .newHttpClient();
            runner.validateToken();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }

    }

}
