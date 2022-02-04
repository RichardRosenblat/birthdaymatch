package com.rosenblat.richard;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

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
        assertDoesNotThrow(() -> service.getBornByDate(TestsConsts.DATE));
    }

    @Test
    public void testGetBornByDateResponse()
    {
        List<String> response = service.getBornByDate(TestsConsts.DATE);
        
        assertNotNull(response);
        assertNotEquals(new ArrayList<String>(), response);
    }
    

}