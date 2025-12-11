package com.example.tareas;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

import com.example.pipeline.Slot;
import com.example.utils.Message;

public class Translator implements Task {

    private String rutaXslt;
    private Slot entrada;
    private Slot salida;

    public Translator(String rutaXslt, Slot entrada, Slot salida) {
        this.rutaXslt = rutaXslt;
        this.entrada = entrada;
        this.salida = salida;
    }

    @Override
    public void execute() throws Exception {     
        while(!entrada.esVacia()){
            translate(entrada.recibirMensaje());
        }
    }

    public void translate(Message mensajeEntrada) throws Exception {
        Document xmlOriginal = mensajeEntrada.getCuerpo();

        File archivoXslt = new File(rutaXslt);
        TransformerFactory fabrica = TransformerFactory.newInstance();
        Transformer transformer = fabrica.newTransformer(new StreamSource(archivoXslt));

        DOMSource origen = new DOMSource(xmlOriginal);
        DOMResult resultado = new DOMResult();
        
        transformer.transform(origen, resultado);

        Document xmlTransformado = (Document) resultado.getNode();

        Message mensajeSalida = mensajeEntrada.clonar();
        mensajeSalida.setCuerpo(xmlTransformado);
        salida.enviarMensaje(mensajeSalida);
    }
}