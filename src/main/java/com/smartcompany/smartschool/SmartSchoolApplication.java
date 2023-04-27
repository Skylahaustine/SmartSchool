package com.smartcompany.smartschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.smartcompany.smartschool", "com.smartcompany.smartschool.config"})

@SpringBootApplication
public class SmartSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartSchoolApplication.class, args);
    }

}
