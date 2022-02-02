package com.rosenblat.richard.util;

import java.net.http.HttpResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public final class ResponseUtil {

    public static void checkResponseOk(HttpResponse<?> response) {

        checkNull(response);

        checkStatus(response, 200);

    }

    private static void checkStatus(HttpResponse<?> response, int status) {
        if (!ResponseUtil.isResponseStatus(response, status)) {
            log.error("response status invalid, throwing exception");
            throw new ResponseStatusException(HttpStatus.valueOf(response.statusCode()), "External API returned Error");
        }
    }

    private static void checkNull(HttpResponse<?> response) {
        if (response == null) {
            log.error("response equals to null, throwing exception");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "External API returned Null");
        }
    }

    public static <w> boolean isResponseStatus(HttpResponse<w> response, int status) {
        return response.statusCode() == status;
    }
}
