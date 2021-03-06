/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umar.faisol.pddikti.ws;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.Text;

/**
 *
 * @author java
 */
public class GetRecordset {
    public DefaultTableModel getRecordset(String token, String table, String ws, String filter, String order, String limit, String offset) {
        System.out.println(ws);
        DefaultTableModel defaultTableModel = null;
        try {
            SOAPConnectionFactory sOAPConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = sOAPConnectionFactory.createConnection();

            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage message = factory.createMessage();

            SOAPHeader header = message.getSOAPHeader();
            SOAPBody body = message.getSOAPBody();
            header.detachNode();


            QName bodyName = new QName("GetRecordset");
            SOAPBodyElement bodyElement = body.addBodyElement(bodyName);

            QName tokenQname = new QName("token");
            SOAPElement tokenSOAPElement = bodyElement.addChildElement(tokenQname);
            tokenSOAPElement.addTextNode(token);

            QName tableQname = new QName("table");
            SOAPElement tableSOAPElement = bodyElement.addChildElement(tableQname);
            tableSOAPElement.addTextNode(table);

            QName filterQname = new QName("filter");
            SOAPElement filterSoap = bodyElement.addChildElement(filterQname);
            filterSoap.addTextNode(filter);

            QName orderQname = new QName("order");
            SOAPElement orderSoap = bodyElement.addChildElement(orderQname);
            orderSoap.addTextNode(order);

            QName limitQname = new QName("limit");
            SOAPElement limitSoap = bodyElement.addChildElement(limitQname);
            limitSoap.addTextNode(limit);

            QName offsetQname = new QName("offset");
            SOAPElement offsetSoap = bodyElement.addChildElement(offsetQname);
            offsetSoap.addTextNode(offset);

            URL endpoint = new URL(ws);
            SOAPMessage response = connection.call(message, endpoint);
            connection.close();

            /**
             * get response from SOAP server
             */
            SOAPBody soapBody = response.getSOAPBody();
            defaultTableModel = new DefaultTableModel();
            //ambilDataMahasiswa(soapBody, defaultTableModel);
            /*Iterator iterator = soapBody.getChildElements();*/

            String indent = "";
            Iterator iterator = soapBody.getChildElements();
            while (iterator.hasNext()) {
                Node node = (Node) iterator.next();
                if (node instanceof SOAPElement) {
                    System.out.println("----------------------------------");
                    SOAPElement sOAPElement = (SOAPElement) node;
                    QName qName = sOAPElement.getElementQName();
                    System.out.println(indent + "Name=" + qName.toString());

                    /* iterasi anaknya level2 */
                    Iterator iter2 = sOAPElement.getChildElements();
                    while (iter2.hasNext()) {
                        Node node2 = (Node) iter2.next();
                        if (node2 instanceof SOAPElement) {
                            System.out.println("----------------------------------");
                            SOAPElement sOAPElement2 = (SOAPElement) node2;
                            QName qName2 = sOAPElement2.getElementQName();
                            System.out.println(indent + "Name2=" + qName2.toString());

                            /* anaknya level3 */
                            Iterator iter3 = sOAPElement2.getChildElements();
                            while (iter3.hasNext()) {
                                Node node3 = (Node) iter3.next();
                                if (node3 instanceof SOAPElement) {
                                    System.out.println("----------------------------------");
                                    SOAPElement sOAPElement3 = (SOAPElement) node3;
                                    QName qName3 = sOAPElement3.getElementQName();
                                    System.out.println(indent + "Name3=" + qName3.toString());

                                    /* ambil result */
                                    if (qName3.toString().equals("result")) {
                                        Iterator iter4 = sOAPElement3.getChildElements();
                                        int i = 0;
                                        while (iter4.hasNext()) {
                                            i++;
                                            Vector data = new Vector();
                                            Node node4 = (Node) iter4.next();
                                            if (node4 instanceof SOAPElement) {
                                                System.out.println("----------------------------------");
                                                SOAPElement sOAPElement4 = (SOAPElement) node4;
                                                QName qName4 = sOAPElement4.getElementQName();
                                                System.out.println(indent + "Name4=" + qName4.toString());

                                                /* ini mulai isi tabel */
                                                Iterator iter5 = sOAPElement4.getChildElements();
                                                while (iter5.hasNext()) {
                                                    Node node5 = (Node) iter5.next();
                                                    if (node5 instanceof SOAPElement) {
                                                        System.out.println("----------------------------------");
                                                        String nilai = "";
                                                        SOAPElement sOAPElement5 = (SOAPElement) node5;
                                                        QName qName5 = sOAPElement5.getElementQName();
                                                        System.out.println(indent + "Name5=" + qName5.toString());

                                                        /* ambil data kolom */
                                                        if (i == 1) {
                                                            defaultTableModel.addColumn(qName5.toString());
                                                        }

                                                        /** tambahan ***/
                                                        Iterator attrib = sOAPElement5.getAllAttributesAsQNames();
                                                        while (attrib.hasNext()){
                                                            QName attrName = (QName)attrib.next();
                                                            System.out.println(indent + " Attribute name is " + attrName.toString());
                                                            System.out.println(indent + " Attribute value is " + sOAPElement5.getAttributeValue(attrName));
                                                            nilai = sOAPElement5.getAttributeValue(attrName);
                                                        }
                                                        /********************************/

                                                        /* ambil nilai */
                                                        Iterator iter6 = sOAPElement5.getChildElements();
                                                        while (iter6.hasNext()) {
                                                            Node node6 = (Node) iter6.next();
                                                            if (node6 instanceof SOAPElement) {
                                                                SOAPElement sOAPElement6 = (SOAPElement) node6;
                                                                Iterator attrs = sOAPElement6.getAllAttributesAsQNames();
                                                                while (attrs.hasNext()){
                                                                    QName attrName = (QName)attrs.next();
                                                                    System.out.println(indent + " Attribute name is " + attrName.toString());
                                                                    System.out.println(indent + " Attribute value is " + sOAPElement6.getAttributeValue(attrName));
                                                                    nilai = sOAPElement6.getAttributeValue(attrName);
                                                                }
                                                            }
                                                            else {
                                                                Text text = (Text) node6;
                                                                nilai = text.getValue();
                                                            }
                                                        }
                                                        if (nilai.equals("true")) {
                                                            nilai = "";
                                                        }
                                                        data.addElement(nilai);
                                                    }
                                                }
                                            }
                                            defaultTableModel.addRow(data);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            /* banyaknya kolom */
            int banyakKolom = defaultTableModel.getColumnCount();
            for (int i = 0; i < banyakKolom; i++) {
                System.out.println("| " + defaultTableModel.getColumnName(i));
            }
        }
        catch(Exception e) {
            System.out.println("[ERROR] - " + new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(new Date()) + " - " + e);
        }

        return defaultTableModel;
    }
}
