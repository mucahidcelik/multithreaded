package com.multithreaded;


import com.multithreaded.WorkerThread;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.util.concurrent.*;

@Configuration
public class Master {
    private ThreadPoolExecutor executor;
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
                    Future future = executor.submit(w);
                }
            }
        }
    }
}
