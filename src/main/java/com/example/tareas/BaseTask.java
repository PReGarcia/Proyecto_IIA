package com.example.tareas;

import com.example.pipeline.Slot;
import com.example.utils.Message;

public abstract class BaseTask implements Task{
    protected Slot entrada;

    public BaseTask(Slot entrada){
        this.entrada = entrada;
    }

    @Override
    public void execute() throws Exception{
        while(!entrada.esVacia()) {
            Message mensaje = entrada.recibirMensaje();
            procesarMensaje(mensaje); 
        }
    }

    protected abstract void procesarMensaje(Message mensaje) throws Exception;

}
