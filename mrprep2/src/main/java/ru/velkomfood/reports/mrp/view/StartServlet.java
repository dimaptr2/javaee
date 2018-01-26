package ru.velkomfood.reports.mrp.view;

import ru.velkomfood.reports.mrp.core.PageGenerator;
import ru.velkomfood.reports.mrp.model.DataBuffer;
import ru.velkomfood.reports.mrp.model.DbReader;
import ru.velkomfood.reports.mrp.model.Requirement;
import ru.velkomfood.reports.mrp.model.Stock;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/mrp2")
public class StartServlet extends HttpServlet {

    private DbReader dbReader;
    private DataBuffer dataBuffer;

    public void setDataBuffer(DataBuffer dataBuffer) {
        this.dataBuffer = dataBuffer;
    }

    public void setDbReader(DbReader dbReader) {
        this.dbReader = dbReader;
    }

    // If you need to run a GET request, then delete comments
//    @Override
//    public void doGet(HttpServletRequest request,
//                      HttpServletResponse response) throws ServletException, IOException {
//
//        Map<String, Object> pageVariables = createPageVariablesMap(request);
//
//        response.setContentType("text/html;charset=utf-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.getWriter().println(PageGenerator.instance().getPage("output.html", pageVariables));
//
//    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        Map<String, Object> pageVariables = createPageVariablesMap(request);
        String message = request.getParameter("message");
//
//        if (message == null || message.isEmpty()) {
//            response.setStatus(HttpServletResponse.SC_OK);
//        } else {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        }

//        response.resetBuffer();
        response.reset();
        dataBuffer.refresh();

        int month = (int) pageVariables.get("month");
        int year = (int) pageVariables.get("year");
        String purGroup = (String) pageVariables.get("purGroup");
        String txtId1 = String.valueOf(pageVariables.get("matnrLow"));
        String txtId2 = String.valueOf(pageVariables.get("matnrHigh"));
        long id1 = Long.parseLong(txtId1);
        long id2 = Long.parseLong(txtId2);
        String warehouse = (String) pageVariables.get("place");
        Map<Long, Stock> stockMap = null;

        try {
            dbReader.openConnection();
            stockMap = dbReader.readCurrentStocks(id1, id2, warehouse);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            message = e.getMessage();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        if (warehouse.equals("")) {
            message = "Укажите номер склада";
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        Map<Long, OutputView> resultMap = dbReader
                .buildResult(stockMap, warehouse, id1, id2, purGroup, month, year);
        List<OutputView> results = new ArrayList<>();
        if (!resultMap.isEmpty()) {
            resultMap.forEach((k, v) -> {
                results.add(v);
            });
            resultMap.clear();
            dataBuffer.increaseCounter(results.size());
        }

        try {
            dbReader.closeConnection();
        } catch (SQLException e2) {
            message = e2.getMessage();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        // Add a main data container for the template of page
        pageVariables.put("results", results);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
                .println(PageGenerator.instance()
                        .getPage("output.html", pageVariables));

    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {

        Map<String, Object> variables = new HashMap<>();
        String message = "";
        // Get the local date in National format
        LocalDate ldt = LocalDate.now();
        int year = ldt.getYear();
        int month = ldt.getMonthValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String txtDate = ldt.format(formatter);

        variables.put("currDate", txtDate);
        variables.put("month", month);
        variables.put("year", year);
        variables.put("matnrLow", request.getParameter("matnrLow"));
        variables.put("matnrHigh", request.getParameter("matnrHigh"));
        variables.put("purGroup", request.getParameter("purGroup"));
        variables.put("place", request.getParameter("place"));
        variables.put("message", message);
        // Build the template of header of table
        List<String> headReqs = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            String name = "Потребность за " + month + "/" + year;
            headReqs.add(name);
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
        }
        variables.put("headReqs", headReqs);

        return variables;
    }


}
