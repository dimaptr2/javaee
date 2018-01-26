package ru.velkomfood.reports.mrp.view;

import com.google.gson.Gson;
import ru.velkomfood.reports.mrp.model.DbReader;
import ru.velkomfood.reports.mrp.model.Stock;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/mrpapi")
public class RestServlet extends HttpServlet {

    private DbReader dbReader;

    public void setDbReader(DbReader dbReader) {
        this.dbReader = dbReader;
    }

    // Here is the GET request
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        Map<String, Object> pageVariables = createPageVariables(request);
        response.setContentType("application/json;charset=utf-8");

        response.resetBuffer();
        response.getWriter().println(buildJsonAnswer(request, pageVariables));

    }

    // Here is the POST request
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        Map<String, Object> pageVariables = createPageVariables(request);
        response.setContentType("application/json;charset=utf-8");

        response.resetBuffer();
        // Transform to JSON form
        response.getWriter().println(buildJsonAnswer(request, pageVariables));

    }

    // PRIVATE SECTION

    private static Map<String, Object> createPageVariables(HttpServletRequest request) {

        Map<String, Object> vars = new HashMap<>();
        String message = "";
        // Get the local date in National format
        LocalDate ldt = LocalDate.now();
        int year = ldt.getYear();
        int month = ldt.getMonthValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        String txtDate = ldt.format(formatter);

        vars.put("month", month);
        vars.put("year", year);
        vars.put("matnrLow", request.getParameter("matnrLow"));
        vars.put("matnrHigh", request.getParameter("matnrHigh"));
        vars.put("purGroup", request.getParameter("purGroup"));
        vars.put("place", request.getParameter("place"));

        return vars;
    }

    private String buildJsonAnswer(HttpServletRequest request, Map<String, Object> pageVariables) {

        Gson gson = new Gson();

        int period = (int) pageVariables.get("month");
        int year = (int) pageVariables.get("year");
        String purg = (String) pageVariables.get("purGroup");

        String txtId1 = String.valueOf(pageVariables.get("matnrLow"));
        String txtId2 = String.valueOf(pageVariables.get("matnrHigh"));
        long id1 = Long.parseLong(txtId1);
        long id2 = Long.parseLong(txtId2);
        String warehouse = (String) pageVariables.get("place");
        Map<Long, Stock> stockMap;
        List<OutputView> viewList = null;

        try {
            dbReader.openConnection();
            stockMap = dbReader.readCurrentStocks(id1, id2, warehouse);
            viewList = prepareResponse(stockMap, warehouse, id1, id2, purg, period, year);
            dbReader.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gson.toJson(viewList);
    }

    private List<OutputView> prepareResponse(Map<Long, Stock> stocks, String warehouse,
                                             long id1, long id2, String purGroup, int period, int year) {

        List<OutputView> outputViews = new ArrayList<>();

        Map<Long, OutputView> viewMap = dbReader.buildResult(stocks, warehouse, id1, id2,
                purGroup, period, year);
        if (!viewMap.isEmpty()) {
            viewMap.forEach((k, v) -> {
                outputViews.add(v);
            });
            viewMap.clear();
        }

        return outputViews;
    }

}
