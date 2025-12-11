package com.example.pipeline;

import java.util.LinkedList;
import java.util.List;

import com.example.utils.Message;

public class Slot {

    private List<Message> mensajes = new LinkedList<>();

    public void enviarMensaje(Message mensaje) {
        if (mensaje != null) {
            mensajes.add(mensaje);
        }
    }

    public Message recibirMensaje() {
        if (mensajes.isEmpty()) {
            return null;
        }
        return mensajes.remove(0);
    }

    public boolean esVacia() {
        return mensajes.isEmpty();
    }
}