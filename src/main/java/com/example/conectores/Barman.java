package com.example.conectores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.utils.Message;
import com.example.utils.XmlUtils;

public class Barman implements Conector{
    private String url;

    public Barman(String rutaBaseDatos) {
        this.url = "jdbc:sqlite:" + rutaBaseDatos;
    }

    public Message execute(Message query) throws DOMException, ParserConfigurationException {
        Message nuevoMensaje = query.clonar();
        nuevoMensaje.setCuerpo(ejecutarOperacion(query.getCuerpo().getDocumentElement().getTextContent()));
        return nuevoMensaje;
    }

    public Document ejecutarOperacion(String query) throws ParserConfigurationException {

        try (Connection conn = DriverManager.getConnection(this.url)) {

            if (conn != null) {
                System.out.println("Conexión a la base de datos establecida exitosamente.");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query); 

                Document doc = XmlUtils.createNewDocument();
                Element root = doc.createElement("resultado"); 
                doc.appendChild(root);

                Element nodoEstado = doc.createElement("estado");

                if (rs.next()) {
                    int cantidadStock = rs.getInt("stock");

                    Element nodoStock = doc.createElement("stock");
                    nodoStock.setTextContent(String.valueOf(cantidadStock));

                    if (cantidadStock > 0) {
                        nodoEstado.setTextContent("Preparado");
                        root.appendChild(nodoEstado);
                        root.appendChild(nodoStock);

                    } else {
                        nodoEstado.setTextContent("No se puede preparar");
                        root.appendChild(nodoEstado);
                        root.appendChild(nodoStock); 
                    }
                } else {
                    nodoEstado.setTextContent("No se puede preparar");
                    root.appendChild(nodoEstado);
                }

                return doc; 
            }

        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return null;
    }

}
