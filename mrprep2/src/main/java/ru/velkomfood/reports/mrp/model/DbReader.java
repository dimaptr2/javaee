package ru.velkomfood.reports.mrp.model;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import ru.velkomfood.reports.mrp.view.OutputView;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DbReader {

    private final BigDecimal ZERO = new BigDecimal(0.000);

    private static DbReader instance;
    private DataSource dataSource;
    private Connection connection;

    private DbReader() {
    }

    public static DbReader getInstance() {
        if (instance == null) {
            instance = new DbReader();
        }
        return instance;
    }

    public void configure() throws IOException {

        Properties props = new Properties();
        InputStream is = getClass().getResourceAsStream("/db.properties");
        props.load(is);
        is.close();

        MysqlDataSource mds = new MysqlDataSource();
        mds.setServerName(props.getProperty("host"));
        mds.setPort(Integer.parseInt(props.getProperty("port")));
        mds.setDatabaseName(props.getProperty("database"));
        mds.setUser(props.getProperty("user"));
        mds.setPassword(props.getProperty("password"));
        dataSource = mds;

    }

    public void openConnection() throws SQLException {
        connection = dataSource.getConnection();
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // Get stocks
    public Map<Long, Stock> readCurrentStocks(long id1, long id2, String whs) throws SQLException {

        final String ISQL1 = "SELECT * FROM current_stocks WHERE ( material_id >= ? AND material_id <= ? ) " +
                "AND warehouse = ?";

        Map<Long, Stock> freeStocks = new TreeMap<>();
        Map<Long, Stock> qualityStocks = new TreeMap<>();
        Map<Long, Stock> blockStocks = new TreeMap<>();

        Map<Long, Stock> stockMap = new ConcurrentHashMap<>();

        StringBuilder sb = new StringBuilder(0);
        sb.append("SELECT id FROM materials WHERE id >= ? AND id < ? ORDER BY id");
        PreparedStatement pstmt0 = connection.prepareStatement(sb.toString());
        sb.delete(0, sb.length());
        sb.append(ISQL1).append(" AND free > 0.000 ORDER BY material_id");
        PreparedStatement pstmt1 = connection.prepareStatement(sb.toString());
        sb.delete(0, sb.length());
        sb.append(ISQL1).append(" AND quality > 0.000 ORDER BY material_id");
        PreparedStatement pstmt2 = connection.prepareStatement(sb.toString());
        sb.delete(0, sb.length());
        sb.append(ISQL1).append(" AND block > 0.000 ORDER BY material_id");
        PreparedStatement pstmt3 = connection.prepareStatement(sb.toString());

        try {
            pstmt0.setLong(1, id1);
            pstmt0.setLong(2, id2);
            ResultSet rs0 = pstmt0.executeQuery();
            while (rs0.next()) {
                Stock st0 = new Stock();
                st0.setMaterialId(rs0.getLong("id"));
                st0.setFree(ZERO);
                st0.setQuality(ZERO);
                st0.setBlock(ZERO);
                stockMap.put(st0.getMaterialId(), st0);
            }
            rs0.close();
            pstmt1.setLong(1, id1);
            pstmt1.setLong(2, id2);
            pstmt1.setString(3, whs);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                Stock st1 = new Stock();
                st1.setMaterialId(rs1.getLong("material_id"));
                st1.setWarehouse(rs1.getString("warehouse"));
                st1.setUom(rs1.getString("uom"));
                st1.setFree(rs1.getBigDecimal("free"));
                st1.setQuality(ZERO);
                st1.setBlock(ZERO);
                freeStocks.put(st1.getMaterialId(), st1);
            }
            rs1.close();
            pstmt2.setLong(1, id1);
            pstmt2.setLong(2, id2);
            pstmt2.setString(3, whs);
            ResultSet rs2 = pstmt2.executeQuery();
            while (rs2.next()) {
                Stock st2 = new Stock();
                st2.setMaterialId(rs2.getLong("material_id"));
                st2.setWarehouse(rs2.getString("warehouse"));
                st2.setUom(rs2.getString("uom"));
                st2.setFree(ZERO);
                st2.setQuality(rs2.getBigDecimal("quality"));
                st2.setBlock(ZERO);
                qualityStocks.put(st2.getMaterialId(), st2);
            }
            rs2.close();
            pstmt3.setLong(1, id1);
            pstmt3.setLong(2, id2);
            pstmt3.setString(3, whs);
            ResultSet rs3 = pstmt3.executeQuery();
            while (rs3.next()) {
                Stock st3 = new Stock();
                st3.setMaterialId(rs3.getLong("material_id"));
                st3.setWarehouse(rs3.getString("warehouse"));
                st3.setUom(rs3.getString("uom"));
                st3.setFree(ZERO);
                st3.setQuality(ZERO);
                st3.setBlock(rs3.getBigDecimal("block"));
                blockStocks.put(st3.getMaterialId(), st3);
            }
            rs3.close();
        } finally {
            pstmt0.close();
            pstmt1.close();
            pstmt2.close();
            pstmt3.close();
        }

        // Fill the total map of stocks
        if (!stockMap.isEmpty()) {

            stockMap.forEach((k, v) -> {
                if (freeStocks.containsKey(k)) {
                    v.setWarehouse(freeStocks.get(k).getWarehouse());
                    v.setUom(freeStocks.get(k).getUom());
                    v.setFree(freeStocks.get(k).getFree());
                }
                if (qualityStocks.containsKey(k)) {
                    v.setWarehouse(qualityStocks.get(k).getWarehouse());
                    v.setUom(qualityStocks.get(k).getUom());
                    v.setQuality(qualityStocks.get(k).getQuality());
                }
                if (blockStocks.containsKey(k)) {
                    v.setWarehouse(blockStocks.get(k).getWarehouse());
                    v.setUom(blockStocks.get(k).getUom());
                    v.setBlock(blockStocks.get(k).getBlock());
                }
                if (v.getFree().equals(ZERO) && v.getQuality().equals(ZERO) && v.getBlock().equals(ZERO)) {
                    stockMap.remove(k);
                }
            });

            freeStocks.clear();
            qualityStocks.clear();
            blockStocks.clear();

        }

        return stockMap;
    }

    // Main reading method
    public Map<Long, OutputView> buildResult(Map<Long, Stock> stocks, String warehouse,
                                             long id1, long id2, String purGroup, int pMonth, int pYear) {

        int nextYear = pYear + 1;
        Map<Long, OutputView> resMap = new ConcurrentHashMap<>();

        List<Requirement> firstReq;
        List<Requirement> secondReq;
        List<Integer> periods1 = new ArrayList<>();
        List<Integer> periods2 = new ArrayList<>();
        int monthCounter = pMonth;
        int yearCounter = pYear;

        // Build the periods for the searching
        for (int i = 0; i < 6; i++) {
            if (yearCounter == pYear) {
                periods1.add(monthCounter);
            } else {
                periods2.add(monthCounter);
            }
            monthCounter++;
            if (monthCounter > 12) {
                monthCounter = 1;
                yearCounter++;
            }
        }

        try {
            // Requirements for current year
            firstReq = readRequirements(id1, id2, purGroup, pYear);
            firstReq.forEach( r1 -> {
                OutputView ov1 = new OutputView();
                ov1.setMaterialId(String.valueOf(r1.getMaterialId()));
                ov1.setPurchaseGroup(r1.getPurchaseGroup());
                ov1.setUom(r1.getUom());
                for (int p1: periods1) {
                    ov1.addRequirement(defineQuantityValue(p1, r1));
                }
                if (!resMap.containsKey(ov1.getMaterialId())) {
                    resMap.put(Long.valueOf(ov1.getMaterialId()), ov1);
                }
            });
            // Requirements for next year
            secondReq = readRequirements(id1, id2, purGroup, nextYear);
            if (!secondReq.isEmpty() && !periods2.isEmpty()) {
                secondReq.forEach(r2 -> {
                    OutputView ov2 = new OutputView();
                    String key = String.valueOf(r2.getMaterialId());
                    ov2.setMaterialId(key);
                    ov2.setPurchaseGroup(r2.getPurchaseGroup());
                    ov2.setUom(r2.getUom());
                    if (resMap.containsKey(key)) {
                        for (int p2: periods2) {
                            resMap.get(key).addRequirement(defineQuantityValue(p2, r2));
                        }
                    } else {
                        for (int p2: periods2) {
                            ov2.addRequirement(defineQuantityValue(p2, r2));
                        }
                        resMap.put(Long.valueOf(key), ov2);
                    }
                });
            }

            // Now we fill the stock
            Iterator<Map.Entry<Long, OutputView>> it = resMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Long, OutputView> entry = it.next();
                long key = entry.getKey();
                OutputView view = entry.getValue();
                view.setDescription(readMaterialDescriptionByid(key));
                view.setName(readPurchaseGroupNameById(view.getPurchaseGroup()));
                view.setWarehouse(warehouse);
                BigDecimal zero = new BigDecimal(0.000);
                Stock st = stocks.get(key);
                if (st != null) {
                    view.setFree(st.getFree());
                    view.setQuality(st.getQuality());
                    view.setBlock(st.getBlock());
                } else {
                    view.setFree(zero);
                    view.setQuality(zero);
                    view.setBlock(zero);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resMap;
    }

    public  List<Requirement> readRequirements(
            long id1, long id2, String purGroup, int year) throws SQLException {

        List<Requirement> requirements = new ArrayList<>();
        StringBuilder sb = new StringBuilder(0);
        sb.append("SELECT * FROM mrp2total");
        sb.append(" WHERE ( material_id >= ? AND material_id <= ?)")
                .append(" AND pur_group = ? AND year = ?")
                .append(" ORDER BY material_id");

        PreparedStatement pstmt = connection.prepareStatement(sb.toString());

        try {
            pstmt.setLong(1, id1);
            pstmt.setLong(2, id2);
            pstmt.setString(3, purGroup);
            pstmt.setInt(4, year);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Requirement r = new Requirement(
                        rs.getLong("material_id"),
                        rs.getString("pur_group"),
                        rs.getInt("year"),
                        rs.getString("uom")
                );
                r.setReq01(rs.getBigDecimal("req01"));
                r.setReq02(rs.getBigDecimal("req02"));
                r.setReq03(rs.getBigDecimal("req03"));
                r.setReq04(rs.getBigDecimal("req04"));
                r.setReq05(rs.getBigDecimal("req05"));
                r.setReq06(rs.getBigDecimal("req06"));
                r.setReq07(rs.getBigDecimal("req07"));
                r.setReq08(rs.getBigDecimal("req08"));
                r.setReq09(rs.getBigDecimal("req09"));
                r.setReq10(rs.getBigDecimal("req10"));
                r.setReq11(rs.getBigDecimal("req11"));
                r.setReq12(rs.getBigDecimal("req12"));
                requirements.add(r);
            }
            rs.close();
        } finally {
            pstmt.close();
        }

        return requirements;
    }

    // Read the texts

    public String readMaterialDescriptionByid(long id) throws SQLException {

        StringBuilder description = new StringBuilder(0);

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT description FROM materials WHERE id = " + id);
        while (rs.next()) {
            description.append(rs.getString("description"));
        }
        rs.close();

        return description.toString();
    }

    public String readPurchaseGroupNameById(String id) throws SQLException {

        StringBuilder name = new StringBuilder(0);
        StringBuilder sql = new StringBuilder(0);

        Statement stmt = connection.createStatement();
        sql.append("SELECT * FROM purgroups WHERE id = \'").append(id).append("\'");

        ResultSet rs = stmt.executeQuery(sql.toString());
        while (rs.next()) {
            name.append(rs.getString("description"));
        }
        rs.close();

        return name.toString();
    }

    private BigDecimal defineQuantityValue(int month, Requirement requirement) {

        BigDecimal value = new BigDecimal(0.000);

        switch (month) {
            case 1:
                value = requirement.getReq01();
                break;
            case 2:
                value = requirement.getReq02();
                break;
            case 3:
                value = requirement.getReq03();
                break;
            case 4:
                value = requirement.getReq04();
                break;
            case 5:
                value = requirement.getReq05();
                break;
            case 6:
                value = requirement.getReq06();
                break;
            case 7:
                value = requirement.getReq07();
                break;
            case 8:
                value = requirement.getReq08();
                break;
            case 9:
                value = requirement.getReq09();
                break;
            case 10:
                value = requirement.getReq10();
                break;
            case 11:
                value = requirement.getReq11();
                break;
            case 12:
                value = requirement.getReq12();
                break;
        }

        return value;
    }

}
