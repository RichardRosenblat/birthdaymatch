package com.rosenblat.richard.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.rosenblat.richard.config.ConfigProperties;
import com.rosenblat.richard.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BornTodayService {

    @Autowired
    ConfigProperties config;

    public List<String> getBornByDate(LocalDate date) {

        log.info("getBornByDate called, getting actors born in {} / {}", date.getDayOfMonth(), date.getMonthValue());

        log.info("Initializing request to {}", config.getUrl());
        HttpRequest request = getBornTodayRequest(date);

        log.info("Request created, sending request");
        String response;
        try {
            response = getCheckedResponse(request);
        } catch (IOException | InterruptedException e) {
            log.error("Error while sending request", e);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while sending request", e);
        }

        log.info("Response valid, mapping response");
        List<String> mappedResponse = getMappedResponse(response);
        
        log.info("Mapping successful, returning mapped response");
        return mappedResponse;
    }

    private HttpRequest getBornTodayRequest(LocalDate date) {
        Integer day = date.getDayOfMonth();
        Integer month = date.getMonthValue();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(config.getUrl() + "/actors/list-born-today?month=" + month + "&day=" + day))
                .header("x-rapidapi-host", config.getHost())
                .header("x-rapidapi-key", config.getKey())
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        return request;

    }

    private String getCheckedResponse(HttpRequest request) throws IOException, InterruptedException {

        HttpResponse<String> response = sendRequest(request);
        ResponseUtil.checkResponseOk(response);
        return response.body();
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    private List<String> getMappedResponse(String response) {
        response = formatString(response);
        return Arrays.asList(response.split(","));
    }

    private String formatString(String response) {
        return response.replaceAll("[\\[\\]\"]", "");         
    }
}
