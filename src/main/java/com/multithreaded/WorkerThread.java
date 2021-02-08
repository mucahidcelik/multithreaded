package com.multithreaded;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.Callable;

public class WorkerThread implements Callable<String> {
    private File inputFile;
    private File outputFile;
    public WorkerThread(File file){
        this.inputFile = file;
        this.outputFile = new File(file.getParent()+"/../output/"+file.getName());
    }

    @Override
    public String call() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        int sum=0;
        String line;
        while ((line = reader.readLine())!=null){
            sum+= Integer.parseInt(line);
        }
        reader.close();
        FileWriter fileWriter = new FileWriter(outputFile);
        fileWriter.write(Integer.toString(sum));
        fileWriter.close();
        return inputFile.getName() + ": " + sum;
    }
}
