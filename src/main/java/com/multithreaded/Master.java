package com.multithreaded;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Configuration
public class Master {
    private ThreadPoolExecutor executor;
    List<Future<WorkerThread>> futures = new ArrayList<>();
    public Master(){
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }
    @Scheduled(fixedRate = 3000)
    public void scanDirectory() throws Exception {
        File inputDirectory = new File("src/main/resources/input");
        if (inputDirectory.isDirectory()) {
            for (File f : inputDirectory.listFiles()) {
                if(f.isFile()){
                    Callable w = new WorkerThread(f);
                    futures.add(executor.submit(w));
                }
            }
            try{
                for (Future f : futures){
                    f.get();
                    if(!f.isDone()){
                        System.out.println("Could not complete task");
                    }else{
                        System.out.println(f.get());
                    }
                }
            }catch (ExecutionException e){
                e.printStackTrace();
            }

        }
    }
}
