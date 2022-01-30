package com.rosenblat.richard.services;

import java.net.URI;
import java.net.http.HttpRequest;

import com.rosenblat.richard.config.ConfigProperties;
import com.rosenblat.richard.dto.imdb.GetBioResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class GetBioService {

    @Autowired
    ConfigProperties config;

    public GetBioResponse getBio(String code) {
        log.info("Getting bio for code: {}", code);
        
        log.info("Initializing request to {}", config.getUrl());
        HttpRequest request = getGetBioRequest(code);

        return null;
    }

    private HttpRequest getGetBioRequest(String code) {
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(config.getUrl() + "/actors/get-bio?nconst=" + code))
        .header("x-rapidapi-host", config.getHost())
        .header("x-rapidapi-key", config.getKey())
        .method("GET", HttpRequest.BodyPublishers.noBody())
        .build();

        return request;
    }

}
