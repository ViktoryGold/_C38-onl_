package com.example;

import java.io.FileInputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StaxParserDemo {
    @SuppressWarnings({"UseSpecificCatch", "CallToPrintStackTrace"})
    public static void main(String[] args) {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(
                new FileInputStream("src/main/resources/library.xml"));
            
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String elementName = startElement.getName().getLocalPart();

                    if (elementName.equals("book")) {
                        javax.xml.stream.events.Attribute idAttr = startElement.getAttributeByName(
                            new javax.xml.namespace.QName("id"));
                        if (idAttr != null) {
                            System.out.println("Book ID: " + idAttr.getValue());
                        }
                    }   
                } else if (event.isCharacters() && !event.asCharacters()
                            .isWhiteSpace()) {
                    String text = event.asCharacters().getData();
                    System.out.println(text);
                } else if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    String elementName = endElement.getName().getLocalPart();
                    if (elementName.equals("book")) {
                        System.out.println("---");  
                    }
                }    
            }
            eventReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}