package com.example.tareas;

import com.example.pipeline.Slot;
import com.example.utils.Message;

public class Replicator extends BaseTask {
    private Slot[] salidas;

    public Replicator(Slot entrada, Slot... salidas) {
        super(entrada);
        this.salidas = salidas;
    }

    @Override
    public void procesarMensaje(Message mensaje) {
        for (Slot salida : salidas) {
            Message nuevoMensaje = mensaje.clonar();
            salida.enviarMensaje(nuevoMensaje);
        }
    }
}