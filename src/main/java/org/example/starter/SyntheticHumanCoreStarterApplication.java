package org.example.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@AutoConfiguration
@ComponentScan
@EnableScheduling
@ConfigurationPropertiesScan
public class SyntheticHumanCoreStarterApplication {

    public static void main(String[] args) {
//        SpringApplication.run(SyntheticHumanCoreStarterApplication.class, args);
    }

}
