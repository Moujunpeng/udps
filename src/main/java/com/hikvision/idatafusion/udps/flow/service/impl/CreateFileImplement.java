package com.hikvision.idatafusion.udps.flow.service.impl;

import com.hikvision.idatafusion.udps.event.CreateFileEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CreateFileImplement {

    private static final Logger log = LoggerFactory.getLogger(CreateFileImplement.class);

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public void createLocalFile(CreateFileEvent fileInfo){

        executorService.submit(()->{

            // java -cp D:\program\processcreate\target\processcreate-1.0-SNAPSHOT.jar com.mjp.FileCreate D:\\mjp1\\programworkspace\\executor\\ 000001111

//            ProcessBuilder pb = new ProcessBuilder();
//            try {
//                ArrayList<String> command = new ArrayList<>();
//                command.add("D:\\program\\java8\\bin\\java.exe");
//                command.add("-cp");
//                command.add("D:\\program\\processcreate\\target\\processcreate-1.0-SNAPSHOT.jar");
//                command.add("com.mjp.FileCreate");
//                command.add(fileInfo.getFilepath());
//                command.add(fileInfo.getFilename());
//                pb.command(command);
//                pb.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            //File file = new File(fileName + File.separator + filePath);
            try {
                FileWriter fileWriter = new FileWriter(fileInfo.getFilepath() + File.separator + fileInfo.getFilename());
                final BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write("hello\n");
                bw.write("world\n");
                bw.close();
            } catch (IOException e) {
                System.out.println("create file error ");
            }

            log.info("create path is " + fileInfo.getFilepath());

        });

    }

}