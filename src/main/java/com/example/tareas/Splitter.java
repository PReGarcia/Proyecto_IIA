package com.example.tareas;

import java.util.List;
import java.util.UUID;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.example.pipeline.Slot;
import com.example.utils.Arbol;
import com.example.utils.Message;
import com.example.utils.XmlUtils;

public class Splitter implements Task {

    private String expresionXpath;
    private Slot entrada;
    private Slot salida;
    private static Arbol arbolInstancia;

    public Splitter(String expresionXpath, String idGrupo, Slot entrada, Slot salida) {
        this.expresionXpath = expresionXpath;
        this.entrada = entrada;
        this.salida = salida;
        arbolInstancia = Arbol.getInstancia();
    }

    public Splitter(String expresionXpath, Slot entrada, Slot salida) {
        this.expresionXpath = expresionXpath;
        this.entrada = entrada;
        this.salida = salida;
        arbolInstancia = Arbol.getInstancia();
    }



    @Override
    public void execute() throws Exception {
        while(!entrada.esVacia()){
            split(entrada.recibirMensaje());
        }
    }
    
    public void split(Message mensaje) throws Exception {
        Message recibido = mensaje;
        Document doc = mensaje.getCuerpo();

        List<Node> items = XmlUtils.NodeGroupSearch(doc, expresionXpath);
        int total = items.size();
        recibido.setTamSecuencia(total);
        recibido.setSequenceId(UUID.randomUUID().toString());

        Message mensajeOriginal = recibido.clonar();
        
        arbolInstancia.agregarArbol(recibido.getSequenceId(), doc, items);
        int contador = 1;

        for (Node item : items) {
            Message nuevoMensaje = mensajeOriginal.clonar();
            Document nuevoCuerpo = XmlUtils.createNewDocument();
            Node importado = nuevoCuerpo.importNode(item, true);
            nuevoCuerpo.appendChild(importado);

            nuevoMensaje.setCuerpo(nuevoCuerpo);
            nuevoMensaje.setOrdenSecuencia(contador);
            contador++; 

            nuevoMensaje.setCorrelationId(UUID.randomUUID().toString());
            salida.enviarMensaje(nuevoMensaje);
        }
    }
}