package ru.velkomfood.edi.info.webreport;

public class Launcher {

    public static void main(String[] args) throws Exception {

        GrizzlyMammy mam = new GrizzlyMammy("0.0.0.0", 8080);
        mam.prepare();
        mam.startUp();
        Thread.currentThread().join();

    }

}
