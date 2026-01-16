package com.example.factory;

import com.example.pipeline.Slot;
import com.example.tareas.Correlator;
import com.example.tareas.Task;

public class CorrelatorFactory extends TaskFactory{

    public Task createTask(Slot[] entradas, Slot... salidas) {

        return new Correlator(entradas, salidas);
    }
}