package com.example.conectores;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;

import com.example.utils.Message;

public interface Conector {
    public Message execute(Message t) throws DOMException, ParserConfigurationException;
}
