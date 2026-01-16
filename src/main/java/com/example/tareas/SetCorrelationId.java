package com.example.tareas;

import java.util.UUID;

import org.w3c.dom.DOMException;

import com.example.pipeline.Slot;
import com.example.utils.Message;
import com.example.utils.XmlUtils;

public class SetCorrelationId extends BaseTask {
    private Slot salida;
    private String xpath;

    public SetCorrelationId(Slot entrada, Slot salida) {
        this(null, entrada, salida); 
    }

    public SetCorrelationId(String xpath, Slot entrada, Slot salida) {
        super(entrada);
        this.xpath = xpath;
    }

    @Override
    public void procesarMensaje(Message mensaje) throws DOMException, Exception {
        String correlationId;

        if (this.xpath != null && !this.xpath.isEmpty()) {
            correlationId = XmlUtils.NodeSearch(mensaje.getCuerpo(), xpath).getTextContent();
        } else {
            correlationId = UUID.randomUUID().toString();
        }

        mensaje.setCorrelationId(correlationId);
        salida.enviarMensaje(mensaje);
    }
}