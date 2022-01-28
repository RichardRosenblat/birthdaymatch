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

// import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BornTodayService {
    // @Value("${URL.IMDB}")
    private String imdbURL = "https://imdb8.p.rapidapi.com";
    private String xRapidapiHost = "imdb8.p.rapidapi.com";
    private String xRapidapiKey = "30f6e8c89bmshfd852ed772e1957p1ac444jsn75196f85f3e0";

    public List<String> getBornByDate(LocalDate date) {
        Integer day = date.getDayOfMonth();
        Integer month = date.getMonthValue();
        log.info("Getting actorns born in {} / {}", day, month);

        log.info("Initializing request to {}", imdbURL);
        HttpRequest request = getIMDBRequest(day, month);

        log.info("request created, sending request");
        HttpResponse<String> response = sendRequest(request);

        log.info("request sent, checking response");
        checkResponse(response);

        log.info("response valid, mapping response");
        List<String> matchingBirthdays = mapResponse(response);

        return matchingBirthdays;
    }

    private List<String> mapResponse(HttpResponse<String> response)
            throws IOException, JsonParseException, JsonMappingException {
        List<String> matchingBirthdays;
        ObjectMapper mapper = new ObjectMapper();
        matchingBirthdays = mapper.readValue(response.body(), List.class);
        return matchingBirthdays;
    }

    private void checkResponse(HttpResponse<String> response) throws Exception {
        if (response == null || !ResponseUtil.isResponseStatus(response, 200)) {
            log.info("response status invalid, throwing exception");
            throw new Exception("Invalid Response");
            // TODO throw custom exception
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
