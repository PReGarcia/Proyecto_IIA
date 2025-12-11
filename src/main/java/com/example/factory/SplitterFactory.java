package com.example.factory;

import com.example.pipeline.Slot;
import com.example.tareas.Splitter;
import com.example.tareas.Task;

public class SplitterFactory extends TaskFactory{

    public Task createTask( String xpath,Slot entrada, Slot salida) {
        return new Splitter(xpath ,entrada, salida);
    }
    
}
