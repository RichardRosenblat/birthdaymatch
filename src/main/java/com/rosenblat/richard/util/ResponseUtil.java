package com.rosenblat.richard.util;

import java.net.http.HttpResponse;

public class ResponseUtil {
    public static <w> boolean isResponseStatus(HttpResponse<w> response, int status) {
        return response.statusCode() == status;
    }
}
