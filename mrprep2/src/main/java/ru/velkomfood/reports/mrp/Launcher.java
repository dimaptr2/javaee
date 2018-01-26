package ru.velkomfood.reports.mrp;

import ru.velkomfood.reports.mrp.core.JettyStarter;
import ru.velkomfood.reports.mrp.model.DbReader;

public class Launcher {

    public static void main(String[] args) throws Exception {

        JettyStarter starter = new JettyStarter();
        DbReader db = DbReader.getInstance();
        db.configure();
        starter.setDbReader(db);
        starter.run();

    }

}
