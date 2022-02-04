package com.rosenblat.richard;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.rosenblat.richard.resources.BirthdayMatchResource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@WireMockTest(httpPort = 3030)
@TestPropertySource(properties = {"imdb.url=http://localhost:3030"})
public class BirthdayMatchControllerTest {

    @Autowired
    BirthdayMatchResource resource;
    
    @Test
    public void testBornToday()
    {
        assertDoesNotThrow(() -> resource.bornToday(17, 10));
    }
    
    @Test
    public void testGetBio()
    {
        assertDoesNotThrow(() -> resource.getBio("nm0001667"));
    }

    @Test
    public void testKnownFor()
    {
        assertDoesNotThrow(() -> resource.knownFor("nm0001667"));
    }

    @Test
    public void testBirthdayMatch()
    {
        assertDoesNotThrow(() -> resource.matchBirthday(17,10));
    }

}