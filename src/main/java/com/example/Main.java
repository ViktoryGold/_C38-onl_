package com.example;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        String inputPath = "src/main/resources/input.xml";
        String outputDir = "output";

        try {
            parceXmlAndSave(inputPath, outputDir);
        } catch (Exception e) {
            System.err.println("Error during processing XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void parceXmlAndSave(String inputPath, String outputDir) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(inputPath));
        doc.getDocumentElement().normalize();

        String lastName = getTagValue(doc, "lastName");
        String firstName = getTagValue(doc, "firstName");
        String nationality = getTagValue(doc, "nationality");
        String yearOfBirth = getTagValue(doc, "yearOfBirth");
        String yearOfDeath = getTagValue(doc, "yearOfDeath");

        String fileName = String.format("%s_%s_%s.txt", lastName, firstName, nationality, yearOfBirth, yearOfDeath);
        Path outputPath = Paths.get(outputDir, fileName);

        Files.createDirectories(outputPath.getParent());

        NodeList lineNodes = doc.getElementsByTagName("line");
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            for (int i = 0; i < lineNodes.getLength(); i++) {
                Node lineNode = lineNodes.item(i);
                String lineText = lineNode.getTextContent();
                writer.write(lineText);
                if (i < lineNodes.getLength() - 1) {
                    writer.newLine();  
                }
            }
        }
        System.out.println("The file is saved: " + outputPath.toAbsolutePath()); 
    }

    private static String getTagValue(Document doc, String tagName) {
        Node node = doc.getElementsByTagName(tagName).item(0);
        return (node != null) ? node.getTextContent().trim() : "unknown";
    }
}