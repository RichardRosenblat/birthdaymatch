package com.rosenblat.richard;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.rosenblat.richard.dto.imdb.knownFor.KnownForResponse;
import com.rosenblat.richard.services.KnownForService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@WireMockTest(httpPort = 3030)
@TestPropertySource(properties = {"imdb.url=http://localhost:3030"})
public class KnownForServiceTest {

    @Autowired
    KnownForService service;
    
    @Test
    public void testKnownFor()
    {
        assertDoesNotThrow(() -> service.knownFor(TestsConsts.CODE));
    }
    
    @Test
    public void testKnownForResult() throws InterruptedException, ExecutionException
    {
        assertDoesNotThrow(() -> service.knownFor(TestsConsts.CODE).get());
        
        List<KnownForResponse> response = service.knownFor(TestsConsts.CODE).get();

        assertNotNull(response);
        assertNotEquals(new ArrayList<KnownForResponse>(), response);
    }

}