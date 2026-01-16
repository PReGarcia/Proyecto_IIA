package com.example.pipeline;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.example.utils.Message;

public class OutputPort {

    private Slot slot;

    public OutputPort(Slot slot) {
        this.slot = slot;
    }

    public void escribirArchivo(String rutaDestino) {
        Message mensaje = slot.recibirMensaje();

        Document documento = mensaje.getCuerpo();

        if (documento != null) {
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                
                DOMSource source = new DOMSource(documento);
                StreamResult result = new StreamResult(new File(rutaDestino));

                transformer.transform(source, result);

                System.out.println("OutputPort: Archivo guardado exitosamente en: " + rutaDestino);

            } catch (Exception e) {
                System.err.println("OutputPort: Error al guardar el archivo XML.");
                e.printStackTrace();
            }
        } else {
            System.err.println("OutputPort: El mensaje recibido no tiene cuerpo.");
        }
    }
}