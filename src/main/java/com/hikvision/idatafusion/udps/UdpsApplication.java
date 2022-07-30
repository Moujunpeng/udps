package com.hikvision.idatafusion.udps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hikvision.idatafusion")
public class UdpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UdpsApplication.class,args);
    }

}