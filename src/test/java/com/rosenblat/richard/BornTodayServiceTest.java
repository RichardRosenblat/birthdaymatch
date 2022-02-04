package com.rosenblat.richard;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.rosenblat.richard.services.BornTodayService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@WireMockTest(httpPort = 3030)
@TestPropertySource(properties = {"imdb.url=http://localhost:3030"})
public class BornTodayServiceTest {

    @Autowired
    BornTodayService service;
    
    @Test
    public void testGetBornByDate()
    {
        assertDoesNotThrow(() -> service.getBornByDate(LocalDate.of(2020, 10, 17)));
    }
    

}