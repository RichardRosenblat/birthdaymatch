package com.rosenblat.richard;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.ExecutionException;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.rosenblat.richard.dto.imdb.getBio.GetBioResponse;
import com.rosenblat.richard.services.GetBioService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@WireMockTest(httpPort = 3030)
@TestPropertySource(properties = {"imdb.url=http://localhost:3030"})
public class GetBioServiceTest {

    @Autowired
    GetBioService service;
    
    @Test
    public void testKnownFor()
    {
        assertDoesNotThrow(() -> service.getBio(TestsConsts.CODE));
    }

    @Test
    public void testKnownForResult() throws InterruptedException, ExecutionException 
    {
        assertDoesNotThrow(() -> service.getBio(TestsConsts.CODE).get());

        GetBioResponse response = service.getBio(TestsConsts.CODE).get();

        assertNotNull(response);
        assertNotEquals(new GetBioResponse(), response);
    }
    

}