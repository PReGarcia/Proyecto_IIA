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

//crear un nodo con los elementos obtenidos de la bd
public class Barman {
    private String url;

    // En el constructor recibimos la ruta al archivo .db
    public Barman(String rutaBaseDatos) {
        // La URL de conexión para SQLite siempre sigue el formato: jdbc:sqlite:ruta
        this.url = "jdbc:sqlite:" + rutaBaseDatos;
    }

    public void execute(Message query) throws DOMException, ParserConfigurationException {
        ejecutarOperacion(query.getCuerpo().getDocumentElement().getTextContent());
    }

    public Document ejecutarOperacion(String query) throws ParserConfigurationException {

        // Estructura try-with-resources: Cierra la conexión automáticamente al terminar
        try (Connection conn = DriverManager.getConnection(this.url)) {

            if (conn != null) {
                System.out.println("Conexión a la base de datos establecida exitosamente.");
                // Creamos el statement para ejecutar la query que nos llega
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query); // 'sql' es el String que recibe el método

                // Usamos tu XmlUtils (que ya tienes en el proyecto) para iniciar el XML de
                // respuesta
                Document doc = com.example.utils.XmlUtils.createNewDocument();
                Element root = doc.createElement("resultado"); // Nodo raíz
                doc.appendChild(root);

                Element nodoEstado = doc.createElement("estado");

                // Comprobamos si la query devolvió algún resultado (si la bebida existe)
                if (rs.next()) {
                    // Asumimos que tu tabla tiene una columna llamada "stock"
                    int cantidadStock = rs.getInt("stock");

                    Element nodoStock = doc.createElement("stock");
                    nodoStock.setTextContent(String.valueOf(cantidadStock));

                    if (cantidadStock > 0) {
                        // CASO: Hay stock
                        nodoEstado.setTextContent("Preparado");
                        root.appendChild(nodoEstado);
                        root.appendChild(nodoStock);

                        // (Opcional) Si quisieras restar el stock en la BD, aquí iría un
                        // stmt.executeUpdate(...)
                    } else {
                        // CASO: No hay stock (cantidad <= 0)
                        nodoEstado.setTextContent("No se puede preparar");
                        root.appendChild(nodoEstado);
                        root.appendChild(nodoStock); // El usuario pidió mostrar el stock aquí también
                    }
                } else {
                    // CASO: No existe la bebida en la tabla
                    nodoEstado.setTextContent("No se puede preparar");
                    root.appendChild(nodoEstado);
                }

                return doc; // Devolvemos el XML generado
            }

        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return null;
    }

}
