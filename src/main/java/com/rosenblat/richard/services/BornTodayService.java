package com.rosenblat.richard.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rosenblat.richard.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("applicationSecret.properties")
public class BornTodayService {
    @Value("${IMDB.url}")
    private String imdbURL;

    @Value("${IMDB.host}")
    private String xRapidapiHost;

    @Value("${IMDB.api}")
    private String xRapidapiKey;

    public List<String> getBornByDate(LocalDate date) {
        Integer day = date.getDayOfMonth();
        Integer month = date.getMonthValue();
        log.info("Getting actorns born in {} / {}", day, month);

        log.info("Initializing request to {}", imdbURL);
        HttpRequest request = getIMDBRequest(day, month);

        log.info("request created, sending request");
        HttpResponse<String> response;
        try {
            response = sendRequest(request);
        } catch (IOException | InterruptedException e) {
            log.error("Error while sending request", e);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while sending request",e);
        }

        log.info("request sent, checking response");
        checkResponse(response);

        log.info("response valid, mapping response");
        List<String> matchingBirthdays;
        try {
            matchingBirthdays = mapResponse(response);
        } catch (IOException e) {
            log.error("Error while mapping request", e);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while mapping request",e);
        }

        return matchingBirthdays;
    }

    private List<String> mapResponse(HttpResponse<String> response)
            throws IOException, JsonParseException, JsonMappingException {
        List<String> matchingBirthdays;
        ObjectMapper mapper = new ObjectMapper();
        matchingBirthdays = mapper.readValue(response.body(), List.class);
        return matchingBirthdays;
    }

    private void checkResponse(HttpResponse<String> response) {
        if (response == null) {
            log.info("response equals to null, throwing exception");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "External API returned Null");
        }
        if (!ResponseUtil.isResponseStatus(response, 200)) {
            log.info("response status invalid, throwing exception");
            throw new ResponseStatusException(HttpStatus.valueOf(response.statusCode()), response.body());
        }
        
    }

    private HttpRequest getIMDBRequest(Integer day, Integer month) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(imdbURL + "/actors/list-born-today?month=" + month + "&day=" + day))
                .header("x-rapidapi-host", xRapidapiHost)
                .header("x-rapidapi-key", xRapidapiKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return request;
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = null;

        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }
}
