package org.kakaoPay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Date;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        //System.out.println(LocalDateTime.now());
        SpringApplication.run(Application.class,args);
    }
}
