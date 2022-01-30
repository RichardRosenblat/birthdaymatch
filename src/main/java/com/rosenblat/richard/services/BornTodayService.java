package com.rosenblat.richard.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.function.Supplier;

import com.rosenblat.richard.config.ConfigProperties;
import com.rosenblat.richard.dto.birthdayMatch.imdb.ImdbBirthdayMatchResponse;
import com.rosenblat.richard.util.JsonBodyHandler;
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

    public ImdbBirthdayMatchResponse getBornByDate(LocalDate date) {

        Integer day = date.getDayOfMonth();
        Integer month = date.getMonthValue();
        
        log.info("BornTodayService.getBornByDate(date) called, getting actors born in {} / {}", day, month);

        log.info("Initializing request to {}", config.getUrl());
        HttpRequest request = getIMDBRequest(day, month);

        log.info("request created, sending request");
        ImdbBirthdayMatchResponse response;
        try {
            response = getCheckedResponse(request);
        } catch (IOException | InterruptedException e) {
            log.error("Error while sending request", e);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while sending request", e);
        }

        return response;

    }

    private HttpRequest getIMDBRequest(Integer day, Integer month) {
        String uri = config.getUrl() + "/actors/list-born-today?month=" + month + "&day=" + day;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("x-rapidapi-host", config.getHost())
                .header("x-rapidapi-key", config.getUrl())
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        return request;

    }

    private ImdbBirthdayMatchResponse getCheckedResponse(HttpRequest request) throws IOException, InterruptedException {

        HttpResponse<Supplier<ImdbBirthdayMatchResponse>> response = sendRequest(request);

        checkResponse(response);

        return response.body().get();

    }

    private HttpResponse<Supplier<ImdbBirthdayMatchResponse>> sendRequest(HttpRequest request)
            throws IOException, InterruptedException {

        HttpResponse<Supplier<ImdbBirthdayMatchResponse>> response = HttpClient.newHttpClient()
                .send(request, new JsonBodyHandler<>(ImdbBirthdayMatchResponse.class));

        return response;

    }

    private void checkResponse(HttpResponse<?> response) {

        if (response == null) {
            log.info("response equals to null, throwing exception");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "External API returned Null");
        }

        if (!ResponseUtil.isResponseStatus(response, 200)) {
            log.info("response status invalid, throwing exception");
            throw new ResponseStatusException(HttpStatus.valueOf(response.statusCode()), "External API returned Error");
        }

    }
}
