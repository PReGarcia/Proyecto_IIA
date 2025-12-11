package com.example.tareas;

import com.example.pipeline.Slot;
import com.example.utils.Message;

public class Merger implements Task {

    private Slot[] entradas;
    private Slot salida;

    public Merger(Slot[] entradas, Slot salida) {
        this.entradas = entradas;
        this.salida = salida;
    }

    @Override
    public void execute() throws Exception {
        for (Slot slot : entradas) {
            if (!slot.esVacia()) {
                merge(slot.recibirMensaje());
            }
        }
    }

    public void merge(Message mensaje) throws Exception {
        salida.enviarMensaje(mensaje);
    }
}