package com.example.factory;

import com.example.pipeline.Slot;
import com.example.tareas.Correlator;

public class CorrelatorFactory extends TaskFactory{

    public Correlator createTask(Slot[] entradas, Slot... salidas) {

        return new Correlator(entradas, salidas);
    }
}