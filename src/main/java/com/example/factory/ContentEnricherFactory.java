package com.example.factory;

import com.example.pipeline.Slot;
import com.example.tareas.ContentEnricher;
import com.example.tareas.Task;

public class ContentEnricherFactory extends TaskFactory{
    

    public Task createTask(String[] xpath, Slot salida, Slot... entradas) {
        return new ContentEnricher(xpath, salida, entradas);
    }
}