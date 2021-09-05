package com.sunil.munrotop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.sunil"})
public class MunrotopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MunrotopApplication.class, args);
    }

}
