package ru.velkomfood.sap.data.keeper;

import ru.velkomfood.sap.data.keeper.creator.ComponentFactory;
import ru.velkomfood.sap.data.keeper.kernel.SchedulerKernel;

public class Launcher {

    public static void main(String[] args) {

        ComponentFactory factory = ComponentFactory.create().inject();
        SchedulerKernel kernel = factory.getKernel();
        kernel.startScheduler();

    }

}
