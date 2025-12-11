package com.example;

import java.nio.file.Path;
import java.util.List;

import com.example.factory.AggregatorFactory;
import com.example.factory.ContentEnricherFactory;
import com.example.factory.CorrelatorFactory;
import com.example.factory.DistributorFactory;
import com.example.factory.MergerFactory;
import com.example.factory.ReplicatorFactory;
import com.example.factory.SplitterFactory;
import com.example.factory.TranslatorFactory;
import com.example.pipeline.InputPort;
import com.example.pipeline.OutputPort;
import com.example.pipeline.RequestPort;
import com.example.pipeline.Slot;
import com.example.tareas.Task;

public class Main {

    public static void main(String[] args) throws Exception {

        String xlst = "src/main/java/com/example/utils/xlst.xml";
        List<String> xpathdist = List.of(
            "/drink[type='hot']",
            "/drink[type='cold']"
        );

        Slot slotDistRepHot = new Slot();
        Slot slotDistRepCold = new Slot();

        List<Slot> slotsDist = List.of(
            slotDistRepHot,
            slotDistRepCold
        );

        Slot slotInSplit = new Slot();

        Slot slotAddOut = new Slot();
        
        Slot slotSplitDist = new Slot();
        
        Slot slotRepCor1 = new Slot();
        Slot slotRepCor2 = new Slot();
        
        Slot slotRepTrad1 = new Slot();
        Slot slotRepTrad2 = new Slot();
        
        Slot slotTradReq1 = new Slot();
        Slot slotRequestCor1 = new Slot();

        Slot slotTradReq2 = new Slot();
        Slot slotRequestCor2 = new Slot();

        Slot slotCorEnricherPend1 = new Slot();
        Slot slotCorEnricherPend2 = new Slot();

        Slot slotCorEnricherRes1 = new Slot();
        Slot slotCorEnricherRes2 = new Slot();
        
        Slot slotEnricherMerger1 = new Slot();
        Slot slotEnricherMerger2 = new Slot();
        
        Slot slotMergerAdd = new Slot();

        Slot[] arrayRepHot = {slotRepCor1, slotRepTrad1};
        Slot[] arrayRepCold = {slotRepCor2, slotRepTrad2};
 
        InputPort inputPort = new InputPort(slotInSplit);
        OutputPort outputPort = new OutputPort(slotAddOut);
        RequestPort requestPort1 = new RequestPort(slotTradReq1, slotRequestCor1);
        RequestPort requestPort2 = new RequestPort(slotTradReq2, slotRequestCor2);

        SplitterFactory splitterFactory = new SplitterFactory();
        DistributorFactory distributorFactory = new DistributorFactory();
        ReplicatorFactory replicatorFactory = new ReplicatorFactory();
        CorrelatorFactory correlatorFactory = new CorrelatorFactory();
        TranslatorFactory traductorFactory = new TranslatorFactory();
        ContentEnricherFactory contentEnricherFactory = new ContentEnricherFactory();
        MergerFactory mergerFactory = new MergerFactory();
        AggregatorFactory aggregatorFactory = new AggregatorFactory();
        
        Task splitTask = splitterFactory.createTask("/cafe_order/drinks/drink", slotInSplit, slotSplitDist);
        Task distTask = distributorFactory.createTask(xpathdist, slotSplitDist, slotsDist);
        Task repHotTask = replicatorFactory.createTask(slotDistRepHot, arrayRepHot);
        Task repColdTask = replicatorFactory.createTask(slotDistRepCold, arrayRepCold);
        Task tradTask1 = traductorFactory.createTask(xlst, slotRepTrad1, slotTradReq1);
        Task corTask1 = correlatorFactory.createTask(new Slot[]{slotRepCor1, slotRequestCor1}, new Slot[]{slotCorEnricherPend1, slotCorEnricherRes1});
        Task tradTask2 = traductorFactory.createTask(xlst, slotRepTrad2, slotTradReq2);
        Task corTask2 = correlatorFactory.createTask(new Slot[]{slotRepCor2, slotRequestCor2}, new Slot[]{slotCorEnricherPend2, slotCorEnricherRes2});
        Task enricherTask1 = contentEnricherFactory.createTask(new String [] {"/drink", ""}, slotEnricherMerger1, new Slot[]{slotCorEnricherPend1, slotCorEnricherRes1});
        Task enricherTask2 = contentEnricherFactory.createTask(new String [] {"/drink", ""}, slotEnricherMerger2, new Slot[]{slotCorEnricherPend2, slotCorEnricherRes2});
        Task mergerTask = mergerFactory.createTask(new Slot[]{slotEnricherMerger1, slotEnricherMerger2}, slotMergerAdd);
        Task aggregatorTask = aggregatorFactory.createTask("/cafe_order/drinks", slotMergerAdd, slotAddOut);

        System.out.println("Iniciando procesamiento de la orden...");
        inputPort.leerArchivo(Path.of("src/main/java/com/comandas/order1.xml").toAbsolutePath().toString());
        splitTask.execute();
        distTask.execute();
        repHotTask.execute();
        repColdTask.execute();
        tradTask1.execute();
        requestPort1.execute();
        corTask1.execute();
        tradTask2.execute();
        requestPort2.execute();
        corTask2.execute();
        enricherTask1.execute();
        enricherTask2.execute();
        mergerTask.execute();
        aggregatorTask.execute();
        outputPort.escribirArchivo("src/main/java/com/output/order.xml");
        System.out.println("Procesamiento completado. Archivo de salida generado en com/output/order.xml");
    } 
}