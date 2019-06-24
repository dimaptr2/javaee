package ru.velkomfood.sap.data.keeper.creator;

import org.mariadb.jdbc.MariaDbDataSource;
import org.slf4j.Logger;
import ru.velkomfood.sap.data.keeper.config.Holder;
import ru.velkomfood.sap.data.keeper.config.LogKeeper;
import ru.velkomfood.sap.data.keeper.config.LogKeeperImpl;
import ru.velkomfood.sap.data.keeper.config.HolderImpl;
import ru.velkomfood.sap.data.keeper.kernel.SchedulerKernel;
import ru.velkomfood.sap.data.keeper.kernel.SchedulerKernelImpl;
import ru.velkomfood.sap.data.keeper.repository.DatabaseManager;
import ru.velkomfood.sap.data.keeper.repository.DatabaseManagerImpl;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ComponentFactory {

    private static final ComponentFactory instance = new ComponentFactory();

    private final LogKeeper logKeeper;
    private final Holder parametersCache;

    // The basic components
    private DatabaseManager<DataSource> databaseManager;
    // start point of server instance
    private SchedulerKernel kernel;

    private ComponentFactory() {

        logKeeper = new LogKeeperImpl();
        parametersCache = new HolderImpl(logKeeper.createLogger(HolderImpl.class));
        parametersCache.readPropertiesFile("/db.properties");
        parametersCache.readPropertiesFile("/sap.properties");

    }

    public static ComponentFactory create() {
        return instance;
    }

    public ComponentFactory inject() {

        Logger localLogger = logKeeper.createLogger(ComponentFactory.class);
        databaseManager = new DatabaseManagerImpl(logKeeper.createLogger(DatabaseManager.class));

        MariaDbDataSource mds = new MariaDbDataSource();
        try {
            mds.setServerName(parametersCache.readPropertyByKey("db.host"));
            mds.setPort(Integer.valueOf(parametersCache.readPropertyByKey("db.port")));
            mds.setDatabaseName(parametersCache.readPropertyByKey("db.database"));
            mds.setUser(parametersCache.readPropertyByKey("db.user"));
            mds.setPassword(parametersCache.readPropertyByKey("db.password"));
        } catch (SQLException ex) {
            localLogger.error(ex.getMessage());
        }

        databaseManager.configure(mds, parametersCache);
        kernel = new SchedulerKernelImpl(logKeeper.createLogger(SchedulerKernel.class), parametersCache);

        return this;
    }

    public SchedulerKernel getKernel() {
        return kernel;
    }

    // private section

}
