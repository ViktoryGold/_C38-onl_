package com.example;

import java.io.File;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParserSwitch {
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 1 (SAX) or 2 (DOM): ");
        int choice = scanner.nextInt();

        String filePath = "src/main/resources/example.xml";

        switch (choice) {
            case 1:
                parseWithSAX(filePath);
                break;
            case 2:
                parseWithDOM(filePath);
                break;
            default:
                System.out.println("Incorrect input. Enter 1 or 2.");
                break;
        }
        scanner.close();
    }

    @SuppressWarnings({"CallToPrintStackTrace", "UseSpecificCatch"})
    private static void parseWithSAX(String filePath) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                boolean isName = false;

                @Override
                public void startElement(String uri, String localName,
                            String qName, Attributes attributes) 
                            throws SAXException {
                    if (qName.equalsIgnoreCase("name")) {
                        isName = true;  
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) 
                            throws SAXException {
                    if (isName) {
                        System.out.println("Element found <name>: " +
                            new String(ch, start, length));
                        isName = false;  
                    }
                }    
            };
            saxParser.parse(new File(filePath), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({"UseSpecificCatch", "CallToPrintStackTrace"})
    private static void parseWithDOM(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));

            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("name");
            System.out.println("Found elements <name>:");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.println(element.getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}