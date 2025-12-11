package com.example.tareas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.example.pipeline.Slot;
import com.example.utils.Arbol;
import com.example.utils.Message;
import com.example.utils.XmlUtils;

public class Aggregator implements Task {

    private final Map<String, List<Message>> comandasPendientes = new ConcurrentHashMap<>();
    private Slot entrada;
    private Slot salida;
    private String xpath;
    Arbol arbol = Arbol.getInstancia();

    public Aggregator(String xpath,Slot entrada, Slot salida) {
        this.xpath = xpath;
        this.entrada = entrada;
        this.salida = salida;
    }


    @Override
    public void execute() throws Exception {
        while(!entrada.esVacia()) {
            Message mensaje = entrada.recibirMensaje();
            String idComanda = mensaje.getSequenceId();
            System.out.println("Aggregator: Recibido mensaje para comanda " + idComanda + " ("
                    + mensaje.getOrdenSecuencia() + "/" + mensaje.getTamSecuencia() + ")");


            List<Message> lista = comandasPendientes.computeIfAbsent(idComanda, k -> new ArrayList<>());
            lista.add(mensaje);

            if(mensaje.getTamSecuencia() == lista.size()) {
                lista.sort(Comparator.comparingInt(Message::getOrdenSecuencia));
                aggregate(mensaje.getSequenceId(),lista);
                comandasPendientes.remove(idComanda);
            }
        }
    }

    private void aggregate(String idComanda,List<Message> messages) throws Exception {
        Document comanda = arbol.getArbol(idComanda); 

        Document doc = XmlUtils.createNewDocument();
        Node root = doc.importNode(comanda.getDocumentElement(), true);
        doc.appendChild(root);

        Node nodoDestino = XmlUtils.NodeSearch(doc, xpath);

        Message mensajeSalida = null;
        for (Message msg : messages) {
            if(mensajeSalida == null) {
                mensajeSalida = msg.clonar();
            }
            Node bebidaNode = doc.importNode(msg.getCuerpo().getDocumentElement(),true);
            nodoDestino.appendChild(bebidaNode);
        }

        mensajeSalida.setCuerpo(doc);
        salida.enviarMensaje(mensajeSalida);
    }
}