package com.example.factory;

import com.example.pipeline.Slot;
import com.example.tareas.Aggregator;
import com.example.tareas.Task;

public class AggregatorFactory extends TaskFactory {

    public Task createTask(String xpath, Slot entrada, Slot salida) {
        return new Aggregator(xpath, entrada, salida);
    }
}