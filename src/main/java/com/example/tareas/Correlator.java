package com.example.tareas;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.pipeline.Slot;
import com.example.utils.Message;

//Hay que hacerlo mas generico esta hecho muy pensado para cafe??
public class Correlator implements Task {

    private final Map<String, Message> pendientes = new ConcurrentHashMap<>();
    private final Map<String, Message> respuestas = new ConcurrentHashMap<>();
    private final Slot[] entradas;
    private final Map<String, Slot> solicitudes = new HashMap<>();
    private final Slot[] salidas;

    public Correlator(Slot[] entradas, Slot... salidas){
        this.entradas = entradas;
        solicitudes.put("Pendientes", entradas[0]);
        solicitudes.put("Respuestas", entradas[1]);
        this.salidas = salidas;
    }

    @Override
    public void execute() throws Exception {
        while(!entradas[0].esVacia() || !entradas[1].esVacia()) {
            for(Map.Entry<String, Slot> entrada : solicitudes.entrySet()) {
                String tipo = entrada.getKey();
                Slot slot = entrada.getValue();
                if(!slot.esVacia()) {
                    Message mensaje = slot.recibirMensaje();
                    if(tipo.equals("Pendientes")) {
                        correlate(mensaje, false);
                    } else {
                        correlate(mensaje, true);
                    }
                }
            }
        }
    }

    public void correlate(Message inputMessage, boolean respuesta) throws Exception {
        String corrId = inputMessage.getCorrelationId();
        if(respuesta){
            if(pendientes.containsKey(corrId)){
                Message mensaje = pendientes.remove(corrId);
                salidas[0].enviarMensaje(mensaje);
                salidas[1].enviarMensaje(inputMessage);
            }else{
                respuestas.put(inputMessage.getCorrelationId(), inputMessage);
            }
        }
        else{
            if(respuestas.containsKey(corrId)){
                Message mensaje = respuestas.remove(corrId);
                salidas[0].enviarMensaje(inputMessage);
                salidas[1].enviarMensaje(mensaje);
            }else{
                pendientes.put(inputMessage.getCorrelationId(), inputMessage);
            }
        }
    }
}