package com.example.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlUtils {
    public static Document parseXml(String ruta){
        Document document = null;
        File xmlFile = new File(ruta);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(xmlFile);
            return document;
        }catch(ParserConfigurationException e){
            e.getMessage();
        } catch (SAXException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        return document;
    }

    public static Node NodeSearch(Document doc, String expresion) throws Exception {
        XPath consulta = XPathFactory.newInstance().newXPath();
        Node resultado = (Node) consulta.evaluate(expresion, doc, XPathConstants.NODE);
        return resultado;
    }

    public static List<Node> NodeGroupSearch(Document doc, String expresion) throws Exception {
        XPath consulta = XPathFactory.newInstance().newXPath();
        List<Node> resultado = toNodeList((NodeList)consulta.evaluate(expresion, doc, XPathConstants.NODESET));
        return resultado;
    }

    public static List<Node> toNodeList(NodeList nodes) {
        List<Node> resultList = new ArrayList<>();

        if (nodes != null) {
            for (int i = 0; i < nodes.getLength(); i++) {
                
                Node node = nodes.item(i);
                
                if (node != null) {
                    resultList.add(node);
                }
            }
        }
        return resultList;
    }

    public static Document createNewDocument() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }

}