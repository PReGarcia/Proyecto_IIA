package com.example.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Arbol {
    private static Arbol instancia;
    private Map<String, Document> arbolesMap;

    private Arbol() {
        arbolesMap = new HashMap<>();
    }

    public void agregarArbol(String clave, Document arbol, List<Node> nodos) throws Exception{
        quitarNodos(arbol, nodos);
        arbolesMap.put(clave, arbol);
    }

    private void quitarNodos(Document arbol, List<Node> nodos) throws Exception{
        for (Node nodo : nodos){
            nodo.getParentNode().removeChild(nodo);
        }
    }

    public static Arbol getInstancia() {
        if (instancia == null) {
            instancia = new Arbol();
        }
        return instancia;
    }

    public Document getArbol(String clave) {
        return arbolesMap.remove(clave);
    }
}
