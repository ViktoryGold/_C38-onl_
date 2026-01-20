package com.example;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlDomParserExample {
    @SuppressWarnings({"UseSpecificCatch", "CallToPrintStackTrace"})
    public static void main(String[] args) {
        try {
           File xmlFile = new File("src/main/resources/users.xml");

           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
           DocumentBuilder builder = factory.newDocumentBuilder();

           Document document = builder.parse(xmlFile);
           document.getDocumentElement().normalize();

           NodeList userList = document.getElementsByTagName("user");
           for (int i = 0; i < userList.getLength(); i++) {
               Node userNode = userList.item(i);
               if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                   Element userElement = (Element) userNode;
                   
                   String name = userElement.getAttribute("name");
                   String age = userElement.getAttribute("age");
                   String companyTitle = userElement
                    .getElementsByTagName("title")
                    .item(0)
                    .getTextContent();
                System.out.println("Name: " + name); 
                System.out.println("Age: " + age);
                System.out.println("Company: " + companyTitle);
                System.out.println("---");
               }
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}