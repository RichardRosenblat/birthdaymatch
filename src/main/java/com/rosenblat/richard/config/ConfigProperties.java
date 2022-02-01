package com.rosenblat.richard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
@PropertySource("classpath:applicationSecret.properties")
@ConfigurationProperties(prefix = "imdb")
@Primary
@EnableAsync
@ConstructorBinding
public class ConfigProperties {
    
    private String url;
    private String host;
    private String key;
}