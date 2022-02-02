package com.rosenblat.richard.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.Future;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rosenblat.richard.config.ConfigProperties;
import com.rosenblat.richard.dto.imdb.knownFor.KnownForResponse;
import com.rosenblat.richard.dto.imdb.knownFor.KnownForResponseInitiator;
import com.rosenblat.richard.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class KnownForService {

    @Autowired
    ConfigProperties config;

    @Async
    public Future<List<KnownForResponse>> knownFor(String code) {
        log.info("Getting known for for code: {}", code);

        log.info("Initializing request to {}", config.getUrl());
        HttpRequest request = getKnownForRequest(code);

        log.info("Request created, sending request");
        HttpResponse<String> response;

        try {
            response = getCheckedResponse(request);
        } catch (IOException | InterruptedException e) {
            log.error("Error while sending request", e);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while sending request", e);
        }

        log.info("Response valid, mapping response");
        KnownForResponseInitiator mappedResponse;

        try {
            mappedResponse = getMappedResponse(response);
        } catch (JsonProcessingException e) {
            log.error("Error while mapping request", e);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while mapping request", e);
        }

        log.info("Get Known for of code {} successful", code);
        return new AsyncResult<List<KnownForResponse>>(mappedResponse.getKnownForResponse());
    }

    private KnownForResponseInitiator getMappedResponse(HttpResponse<String> response)
            throws JsonMappingException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        KnownForResponseInitiator mappedResponse = new KnownForResponseInitiator();
        mappedResponse
                .setKnownForResponse(mapper.readValue(response.body(), new TypeReference<List<KnownForResponse>>() {
                }));

        return mappedResponse;
    }

    private HttpResponse<String> getCheckedResponse(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = sendRequest(request);

        ResponseUtil.checkResponseOk(response);

        log.info("Response valid, returning response");
        return response;
    }

    private HttpResponse<String> sendRequest(HttpRequest request)
            throws IOException, InterruptedException {

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    private HttpRequest getKnownForRequest(String code) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(config.getUrl() + "/actors/get-known-for?nconst=" + code))
                .header("x-rapidapi-host", config.getHost())
                .header("x-rapidapi-key", config.getKey())
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        return request;
    }

}
