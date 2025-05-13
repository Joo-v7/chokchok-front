package com.chokchok.chokchokfront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ChokchokFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChokchokFrontApplication.class, args);
    }

}
