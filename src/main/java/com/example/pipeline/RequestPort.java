package com.example.pipeline;

public class RequestPort {
    Slot entrada;
    Slot salida;

    public RequestPort(Slot entrada, Slot salida) {
        this.entrada = entrada;
        this.salida = salida;
    }

    public void execute(){
        while(!entrada.esVacia()){
            salida.enviarMensaje(entrada.recibirMensaje());
        }
    }
}
