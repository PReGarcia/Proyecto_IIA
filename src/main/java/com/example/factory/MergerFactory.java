package com.example.factory;

import com.example.pipeline.Slot;
import com.example.tareas.Merger;
import com.example.tareas.Task;

public class MergerFactory extends TaskFactory{
    

    public Task createTask(Slot[] entradas, Slot salida) {
        return new Merger(entradas, salida);
    }

}
