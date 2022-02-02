package com.rosenblat.richard.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import com.rosenblat.richard.config.ConfigProperties;
import com.rosenblat.richard.dto.imdb.getBio.GetBioResponse;
import com.rosenblat.richard.util.JsonBodyHandler;
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
public class GetBioService {

    @Autowired
    ConfigProperties config;

    @Async
    public Future<GetBioResponse> getBio(String code) {
        log.info("Getting bio for code: {}", code);

        HttpRequest request = getGetBioRequest(code);

        GetBioResponse response;

        try {
            response = getCheckedResponse(request);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while sending request", e);
        }

        log.info("Get bio successful, the code {} belongs to {}", code, response.getName());
        return new AsyncResult<GetBioResponse>(response);
    }

    private GetBioResponse getCheckedResponse(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<Supplier<GetBioResponse>> response = sendRequest(request);

        ResponseUtil.checkResponseOk(response);

        return response.body().get();
    }

    public HttpResponse<Supplier<GetBioResponse>> sendRequest(HttpRequest request)
            throws IOException, InterruptedException {

        HttpResponse<Supplier<GetBioResponse>> model = HttpClient.newHttpClient()
                .send(request, new JsonBodyHandler<>(GetBioResponse.class));

        return model;
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
