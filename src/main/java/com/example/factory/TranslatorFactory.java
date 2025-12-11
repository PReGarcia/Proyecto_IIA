package com.example.factory;

import com.example.pipeline.Slot;
import com.example.tareas.Task;
import com.example.tareas.Translator;

public class TranslatorFactory extends TaskFactory{

    public Task createTask(String xslt, Slot entrada, Slot salida) {
        return new Translator(xslt, entrada, salida);
    }

}
