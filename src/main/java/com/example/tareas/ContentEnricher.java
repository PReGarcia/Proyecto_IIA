package com.example.tareas;

import org.w3c.dom.Node;

import com.example.pipeline.Slot;
import com.example.utils.Message;
import com.example.utils.XmlUtils;

public class ContentEnricher implements Task {

    private String [] xpath;
    private Slot[] entradas;
    private Slot salida;

    public ContentEnricher(String[] xpath, Slot salida, Slot... entradas) {
        this.xpath = xpath;
        this.entradas = entradas;
        this.salida = salida;
    }

    @Override
    public void execute() throws Exception {
        while(!entradas[0].esVacia() || !entradas[1].esVacia()){
            enriquecer(entradas[0].recibirMensaje(), entradas[1].recibirMensaje());
        }
    }

    private void enriquecer(Message mensaje, Message respuesta) throws Exception {
        Node nodoDestino = XmlUtils.NodeSearch(mensaje.getCuerpo(), xpath[0]);
        Node nodoImportado = mensaje.getCuerpo().importNode(respuesta.getCuerpo().getDocumentElement(), true);
        nodoDestino.appendChild(nodoImportado);
        salida.enviarMensaje(mensaje);
    }
}