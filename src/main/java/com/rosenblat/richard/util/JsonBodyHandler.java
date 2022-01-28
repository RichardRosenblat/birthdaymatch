package com.rosenblat.richard.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonBodyHandler<W> implements HttpResponse.BodyHandler<Supplier<W>> {

    /*
     * how to call this:
     * HttpRequest request = HttpRequest.newBuilder(new
     * URI("https://jsonplaceholder.typicode.com/todos/1"))
     * .header("Accept", "application/json")
     * .build();
     *
     * Model model = HttpClient.newHttpClient()
     * .send(request, new JsonBodyHandler<>(Model.class))
     * .body()
     * .get();
     *
     * System.out.println(model);
     *
     */

    private final Class<W> wClass;

    public JsonBodyHandler(Class<W> wClass) {
        this.wClass = wClass;
    }

    @Override
    public HttpResponse.BodySubscriber<Supplier<W>> apply(HttpResponse.ResponseInfo responseInfo) {
        return asJSON(wClass);
    }

    public static <W> HttpResponse.BodySubscriber<Supplier<W>> asJSON(Class<W> targetType) {
        HttpResponse.BodySubscriber<InputStream> upstream = HttpResponse.BodySubscribers.ofInputStream();

        return HttpResponse.BodySubscribers.mapping(
                upstream,
                inputStream -> toSupplierOfType(inputStream, targetType));
    }

    private static <W> Supplier<W> toSupplierOfType(InputStream inputStream, Class<W> targetType) {
        return () -> {
            try (InputStream stream = inputStream) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(stream, targetType);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }
}
