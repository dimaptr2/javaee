package ru.velkomfood.sap.xml.storage;

import ru.velkomfood.sap.xml.storage.config.ServerCore;

public class Launcher {

    public static void main(String[] args) {

        ServerCore xmlKeeper = ServerCore.create();
        xmlKeeper.run();

    }

}
