package com.rosenblat.richard;

import com.rosenblat.richard.config.ConfigProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
@ConfigurationPropertiesScan("com.rosenblat.richard.config.configurationproperties")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    
}