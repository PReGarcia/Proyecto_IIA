package com.example.tareas;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.example.pipeline.Slot;
import com.example.utils.Message;
import com.example.utils.XmlUtils;

public class Distributor implements Task {

    private Map<String, Slot> salidas;
    private Slot entrada;

    public Distributor(List<String> xpath, Slot entrada,List<Slot> salidas) {
        this.salidas =  new LinkedHashMap<>();
        this.entrada = entrada;
        for (int i = 0; i < xpath.size(); i++) {
            this.salidas.put(xpath.get(i), salidas.get(i));
        }
    }

    @Override
    public void execute() throws Exception {
        while(!entrada.esVacia()) {
            distribute(entrada.recibirMensaje());
        }
    }
    public void distribute(Message mensajeEntrada) throws Exception {
        Document documento = mensajeEntrada.getCuerpo();

        for (Map.Entry<String, Slot> regla : salidas.entrySet()) {
            String xpath = regla.getKey();
            Slot slotDestino = regla.getValue();

            Node resultado = XmlUtils.NodeSearch(documento, xpath);

            if (resultado != null) {
                Message copia = mensajeEntrada.clonar();
                slotDestino.enviarMensaje(copia);
            }
        }
    }
}