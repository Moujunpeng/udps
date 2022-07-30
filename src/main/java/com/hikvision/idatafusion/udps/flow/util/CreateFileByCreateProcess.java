package com.hikvision.idatafusion.udps.flow.util;

import java.io.IOException;
import java.util.ArrayList;

public class CreateFileByCreateProcess extends Thread{



    private String fileName;

    private String filePath;

    public CreateFileByCreateProcess(String filePath, String fileName) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    @Override
    public void run() {

        // java -cp D:\program\processcreate\target\processcreate-1.0-SNAPSHOT.jar com.mjp.FileCreate D:\\mjp1\\programworkspace\\executor\\ 000001111
        ProcessBuilder pb = new ProcessBuilder();
        try {
            ArrayList<String> command = new ArrayList<>();
//                command.add("cmd");
            command.add("D:\\program\\java8\\bin\\java.exe");
            command.add("-cp");
            command.add("D:\\program\\processcreate\\target\\processcreate-1.0-SNAPSHOT.jar");
            command.add("com.mjp.FileCreate");
            command.add(filePath);
            command.add(fileName);
            pb.command(command);
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
