package ru.velkomfood.services.mrp4.watch.erp;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import ru.velkomfood.services.mrp4.watch.Component;

public interface ErpReader extends Component {

    JCoDestination createDestination() throws JCoException;
    void readPlantInfo(JCoDestination destination) throws JCoException;
    void readPurchaseUnits(JCoDestination destination) throws JCoException;
    void readMaterials(JCoDestination destination) throws JCoException;

}
