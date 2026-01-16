package com.example.utils;

import java.util.UUID;

import org.w3c.dom.Document;

public class Message {
    private String messageId;
    private String sequenceId;
    private String correlationId;
    private int tamSecuencia;
    private int ordenSecuencia;
    private Document cuerpo;

    public Message(String sequenceId, String corrId, int tamSec, int ordSec, Document c){
        messageId = UUID.randomUUID().toString();
        this.sequenceId = sequenceId;
        correlationId = corrId;
        tamSecuencia = tamSec;
        ordenSecuencia = ordSec;
        cuerpo = c;
    }

    public Message(String cid, Document c){
        messageId = UUID.randomUUID().toString();
        sequenceId = null;
        correlationId = null;
        tamSecuencia = 0;
        ordenSecuencia = 0;
        cuerpo = c;
    }


    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public int getTamSecuencia() {
        return tamSecuencia;
    }

    public void setTamSecuencia(int tamSecuencia) {
        this.tamSecuencia = tamSecuencia;
    }

    public int getOrdenSecuencia() {
        return ordenSecuencia;
    }

    public void setOrdenSecuencia(int ordenSecuencia) {
        this.ordenSecuencia = ordenSecuencia;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Document getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(Document cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Message clonar(){
        return new Message(sequenceId,correlationId, tamSecuencia, ordenSecuencia, cuerpo);
    }

}