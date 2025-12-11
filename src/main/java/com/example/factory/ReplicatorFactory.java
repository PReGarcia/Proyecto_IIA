package com.example.factory;

import com.example.pipeline.Slot;
import com.example.tareas.Replicator;
import com.example.tareas.Task;

public class ReplicatorFactory extends TaskFactory{
    

    public Task createTask(Slot input, Slot... output) {
        return new Replicator(input, output);
    }

}
