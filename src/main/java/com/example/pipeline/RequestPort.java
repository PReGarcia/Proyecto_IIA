package com.example.pipeline;

import javax.xml.parsers.ParserConfigurationException;

import com.example.conectores.Conector;

public class RequestPort {
    Slot entrada;
    Slot salida;
    Conector con;

    public RequestPort(Slot entrada, Slot salida, Conector con) {
        this.entrada = entrada;
        this.salida = salida;
        this.con = con;
    }

    public void execute() throws RuntimeException, ParserConfigurationException{
        while(!entrada.esVacia()){
            salida.enviarMensaje(con.execute(entrada.recibirMensaje()));
        }

    }
}
