package ru.velkomfood.grizzly.edi.port.endpoints;

import ru.velkomfood.grizzly.edi.port.provider.KonRequestor;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class SapWsPoint {

    private KonRequestor konRequestor;

    public SapWsPoint() {
    }

    public SapWsPoint(KonRequestor konRequestor) {
        this.konRequestor = konRequestor;
    }

    public void setKonRequestor(KonRequestor konRequestor) {
        this.konRequestor = konRequestor;
    }

    @WebMethod(operationName = "RECEIVE")
    public String receive(String atDate) {

        String xml = "";

        return xml;
    }

    @WebMethod(operationName = "SEND")
    public String send(String xmlDocument) {

        String statusDoc = "";

        return statusDoc;
    }

    @WebMethod(operationName = "GET_RELATIONS")
    public String getRelations(String schema) {

        String xmlMap = "";

        return xmlMap;
    }

}
