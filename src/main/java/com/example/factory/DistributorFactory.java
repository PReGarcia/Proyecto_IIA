package com.example.factory;

import java.util.List;

import com.example.pipeline.Slot;
import com.example.tareas.Distributor;
import com.example.tareas.Task;

public class DistributorFactory extends TaskFactory {

    public Task createTask(List<String> xpath, Slot entrada, List<Slot> salidas) {
        return new Distributor(xpath, entrada, salidas);
    }
}