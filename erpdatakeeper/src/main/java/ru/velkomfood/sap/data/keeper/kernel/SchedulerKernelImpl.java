package ru.velkomfood.sap.data.keeper.kernel;

import org.slf4j.Logger;
import ru.velkomfood.sap.data.keeper.config.Holder;

public class SchedulerKernelImpl implements SchedulerKernel {

    private final Logger logger;
    private final Holder parametersHolder;

    public SchedulerKernelImpl(Logger logger, Holder parametersHolder) {
        this.logger = logger;
        this.parametersHolder = parametersHolder;
    }

    @Override
    public void startScheduler() {

    }

}
