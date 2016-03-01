/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umar.faisol.pddikti.ws;

import java.net.URL;
import java.util.Iterator;
import javax.xml.soap.Text;
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

/**
 *
 * @author java
 */
public class GetToken {
    /* GetToken */
    public String getToken(String usr, String pwd, String ws) {
        String token = "";
        try {
            SOAPConnectionFactory sOAPConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = sOAPConnectionFactory.createConnection();

            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage message = factory.createMessage();

            SOAPHeader header = message.getSOAPHeader();
            SOAPBody body = message.getSOAPBody();
            header.detachNode();


            QName bodyName = new QName("GetToken");
            SOAPBodyElement bodyElement = body.addBodyElement(bodyName);

            QName userQname = new QName("username");
            SOAPElement username = bodyElement.addChildElement(userQname);
            username.addTextNode(usr);

            QName passwordQname = new QName("password");
            SOAPElement password = bodyElement.addChildElement(passwordQname);
            password.addTextNode(pwd);

            URL endpoint = new URL(ws);
            SOAPMessage response = connection.call(message, endpoint);
            connection.close();

            /**
             * get response from SOAP server
             */
            SOAPBody soapBody = response.getSOAPBody();
            Iterator iterator = soapBody.getChildElements();

            while (iterator.hasNext()) {
                Node node = (Node) iterator.next();
                if (node instanceof SOAPElement) {
                    SOAPElement element = (SOAPElement)node;
                    QName name = element.getElementQName();
                    System.out.println("Name is " + name.toString());

                    Iterator iter2 = element.getChildElements();
                    while (iter2.hasNext()) {
                        Node node2 = (Node) iter2.next();
                        if (node instanceof SOAPElement) {
                            SOAPElement element2 = (SOAPElement)node2;
                            QName name2 = element2.getElementQName();
                            System.out.println("Name is " + name2.toString());

                            Iterator iter3 = element2.getChildElements();
                            while (iter3.hasNext()) {
                                Node node3 = (Node) iter3.next();
                                Text text = (Text) node3;
                                token = text.getValue();
                                System.out.println("Token is: " + token);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error GetToken: " + e);
        }
        return token;
    }
}
