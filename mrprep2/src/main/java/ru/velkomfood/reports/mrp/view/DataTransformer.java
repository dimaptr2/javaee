package ru.velkomfood.reports.mrp.view;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.velkomfood.reports.mrp.model.DbReader;
import ru.velkomfood.reports.mrp.model.Stock;

import javax.servlet.ServletOutputStream;
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

@WebServlet(urlPatterns = "/excel")
public class DataTransformer extends HttpServlet {

    private DbReader dbReader;

    public void setDbReader(DbReader dbReader) {
        this.dbReader = dbReader;
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        final String DASH = "-";
        Map<String, Object> pageVariables = createPageVariables(request);

        response.reset();
        response.setContentType("application/vnd.ms-excel");

//        response.setContentType("application/json;charset=utf-8");

        LocalDate dt = LocalDate.now();
        String attachment = "mrp2-" + dt.getYear() + DASH + dt.getMonth() +
                DASH + dt.getDayOfMonth() + ".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + attachment);

        int period = (int) pageVariables.get("month");
        int year = (int) pageVariables.get("year");
        String purg = (String) pageVariables.get("purGroup");

        String txtId1 = String.valueOf(pageVariables.get("matnrLow"));
        String txtId2 = String.valueOf(pageVariables.get("matnrHigh"));
        long id1 = Long.parseLong(txtId1);
        long id2 = Long.parseLong(txtId2);
        String warehouse = (String) pageVariables.get("place");

        List<OutputView> viewList = prepareResponse(id1, id2, purg, warehouse, period, year);

        // Create an excel file in the web stream
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        sheet.setDefaultColumnWidth(30);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        // Create a header
        Row header = sheet.createRow(0);
        int indexCell = 0;
        header.createCell(indexCell).setCellValue("№ материала");
        indexCell++;
        header.createCell(indexCell).setCellValue("Описание");
        indexCell++;
        header.createCell(indexCell).setCellValue("Склад");
        indexCell++;
        header.createCell(indexCell).setCellValue("Группа закупок");
        indexCell++;
        header.createCell(indexCell).setCellValue("Наименование");
        indexCell++;
        header.createCell(indexCell).setCellValue("ЕИ");
        indexCell++;
        header.createCell(indexCell).setCellValue("Свободный запас");
        indexCell++;
        header.createCell(indexCell).setCellValue("Запас на КК");
        indexCell++;
        header.createCell(indexCell).setCellValue("Блокированный запас");
        indexCell++;
        // Add requirements
        int mc = period;
        int yc = year;
        for (int j = 0; j < 6; j++) {
            header.createCell(indexCell).setCellValue("Потребность за " + mc + "/" + yc);
            indexCell++;
            mc++;
            if (mc > 12) {
                mc = 1;
                yc++;
            }
        }

        // Create rows in the excel file
        int indexRow = 1;

        Iterator<OutputView> iter1 = viewList.iterator();

        while (iter1.hasNext()) {
            OutputView ov = iter1.next();
            Row row = sheet.createRow(indexRow);
            indexCell = 0;
            row.createCell(indexCell).setCellValue(ov.getMaterialId());
            indexCell++;
            row.createCell(indexCell).setCellValue(ov.getDescription());
            indexCell++;
            row.createCell(indexCell).setCellValue(ov.getWarehouse());
            indexCell++;
            row.createCell(indexCell).setCellValue(ov.getPurchaseGroup());
            indexCell++;
            row.createCell(indexCell).setCellValue(ov.getName());
            indexCell++;
            row.createCell(indexCell).setCellValue(ov.getUom());
            indexCell++;
            row.createCell(indexCell).setCellValue(ov.getFree().doubleValue());
            indexCell++;
            row.createCell(indexCell).setCellValue(ov.getQuality().doubleValue());
            indexCell++;
            row.createCell(indexCell).setCellValue(ov.getBlock().doubleValue());
            indexCell++;
            for (BigDecimal valReq: ov.getRequirements()) {
                row.createCell(indexCell).setCellValue(valReq.doubleValue());
                indexCell++;
            }
            indexRow++;
        }
        // Give the stream to remote addresses
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
    }

    private static Map<String, Object> createPageVariables(HttpServletRequest request) {

        Map<String, Object> vars1 = new HashMap<>();

        // Get the local date in National format
        LocalDate ldt = LocalDate.now();
        int year = ldt.getYear();
        int month = ldt.getMonthValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        vars1.put("month", month);
        vars1.put("year", year);
        vars1.put("matnrLow", request.getParameter("matnrLow"));
        vars1.put("matnrHigh", request.getParameter("matnrHigh"));
        vars1.put("purGroup", request.getParameter("purGroup"));
        vars1.put("place", request.getParameter("place"));

        return vars1;
    }

    // Get needed data from database and create a list
    private List<OutputView> prepareResponse(long id1, long id2, String pg,
                                             String whs, int month, int year) {

        List<OutputView> outputViews = new ArrayList<>();

        try {
            dbReader.openConnection();
            Map<Long, Stock> stocks = dbReader.readCurrentStocks(id1, id2, whs);
            Map<Long, OutputView> viewMap = dbReader.buildResult(stocks, whs, id1, id2,
                    pg, month, year);
            viewMap.forEach((key, value) -> {
                outputViews.add(value);
            });
            viewMap.clear();
            dbReader.closeConnection();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return outputViews;
    }
}
