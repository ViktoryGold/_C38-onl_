package com.example;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParserDemo extends DefaultHandler {
    private final StringBuilder currentText = new StringBuilder();

    @Override
    public void startElement(String uri, String localName, String qName, 
                                Attributes attributes) throws SAXException {
        currentText.setLength(0);

        if (qName.equalsIgnoreCase("employee")) {
            String id = attributes.getValue("id") ;
            System.out.println("Employee ID: " + id);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
                                 throws SAXException {
        switch (qName) {
            case "name":
                System.out.println("Name: " + currentText.toString());
                break;
            case "age":
                System.out.println("Age: " + currentText.toString());
                break;
            case "role":
                System.out.println("Role: " + currentText.toString());
                break;
        }
        System.out.println("---");
    }

    @Override
    public void characters(char[] ch, int start, int length) 
                                     throws SAXException {
        currentText.append(ch, start, length);
    }
    
    @SuppressWarnings({"CallToPrintStackTrace", "UseSpecificCatch"})
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            SaxParserDemo handler = new SaxParserDemo();
            saxParser.parse(new File("src/main/resources/employees.xml"), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}