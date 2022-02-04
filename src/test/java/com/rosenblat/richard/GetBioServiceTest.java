package com.rosenblat.richard;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
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
        assertDoesNotThrow(() -> service.getBio("nm0001667"));
    }
    

}