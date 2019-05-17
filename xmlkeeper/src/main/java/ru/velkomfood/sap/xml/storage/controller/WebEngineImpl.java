package ru.velkomfood.sap.xml.storage.controller;

import ru.velkomfood.sap.xml.storage.behavior.DatabaseListener;
import ru.velkomfood.sap.xml.storage.behavior.WebEngine;
import ru.velkomfood.sap.xml.storage.model.SoapMessage;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static spark.Spark.*;

public class WebEngineImpl implements WebEngine {

    private final Properties serverParameters;
    private final DatabaseListener databaseListener;
    private static final String TEMPLATE_PATH = "templates";

    public WebEngineImpl(Properties serverParameters, DatabaseListener databaseListener) {
        this.serverParameters = serverParameters;
        this.databaseListener = databaseListener;
    }

    @Override
    public void startUp() {

        // Set the port of the server
        int portNumber = Integer.valueOf(serverParameters.getProperty("server.port"));
        port(portNumber);
        // Set the general parameters of this server instance
        int maxThreads = Integer.valueOf(serverParameters.getProperty("max.threads"));
        int minThreads = Integer.valueOf(serverParameters.getProperty("min.threads"));
        int timeOutMillis = Integer.valueOf(serverParameters.getProperty("time.out"));
        threadPool(maxThreads, minThreads, timeOutMillis);
        // Set the location of static files
        staticFiles.location("/static");

    }

    @Override
    public void toRide() {
        get("/", this::indexPage);
        post("/keeper/:customer/:messageType/:provider", this::keepMessage);
    }

    // private section

    // Get the index page
    private Object indexPage(Request request, Response response) {

        Map<String, Object> model = new HashMap<>();
        model.put("currentDate", translateLocalDateToRussianFormat(LocalDate.now()));

        return render(model, TEMPLATE_PATH + "/index.vm");
    }

    // Execute POST requests with the customer ID and with the body contained an XML message
    private Object keepMessage(Request request, Response response) {

        Map<String, Object> model = new HashMap<>();
        LocalDate currDate = LocalDate.now();
        // 24-hours format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm:ss");
        String currTime = LocalTime.now().format(formatter);
        model.put("currentDate", translateLocalDateToRussianFormat(currDate));
        model.put("currentTime", currTime);

        // Get parameters and the body
        java.sql.Date sqlDate = java.sql.Date.valueOf(currDate.toString());
        java.sql.Time sqlTime = java.sql.Time.valueOf(currTime);
        long customerId = Long.valueOf(request.params(":customer"));
        String msgType = request.params(":messageType");
        long providerId = Long.valueOf(request.params(":provider"));
        String body = request.body();
        // Create a SOAP message object
        SoapMessage message = new SoapMessage(sqlDate, sqlTime, customerId, providerId, msgType, body);

        return render(model, TEMPLATE_PATH + "/keeper-status.vm");
    }

    private String translateLocalDateToRussianFormat(LocalDate value) {
        String[] txtDate = value.toString().split("-");
        return (txtDate[2] + "." + txtDate[1] + "." + txtDate[0]);
    }

    // Render the page by her path
    private static String render(Map<String, Object> model, String templatePath) {
        return new VelocityTemplateEngine().render(new ModelAndView(model, templatePath));
    }

}
