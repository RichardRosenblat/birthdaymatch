package com.rosenblat.richard;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.rosenblat.richard.dto.birthdaymatch.BirthdayMatchResponse;
import com.rosenblat.richard.dto.imdb.getBio.GetBioResponse;
import com.rosenblat.richard.dto.imdb.knownFor.KnownForResponse;
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
        assertDoesNotThrow(() -> resource.bornToday(TestsConsts.DAY, TestsConsts.MONTH));
        
        List<String> response = resource.bornToday(TestsConsts.DAY, TestsConsts.MONTH);
        
        assertNotNull(response);
        assertNotEquals(new ArrayList<String>(), response);
    }
    
    @Test
    public void testGetBio()
    {
        assertDoesNotThrow(() -> resource.getBio(TestsConsts.CODE));

        GetBioResponse response = resource.getBio(TestsConsts.CODE);
        
        assertNotNull(response);
        assertNotEquals(new GetBioResponse(), response);
    }

    @Test
    public void testKnownFor()
    {
        assertDoesNotThrow(() -> resource.knownFor(TestsConsts.CODE));

        List<KnownForResponse> response = resource.knownFor(TestsConsts.CODE);

        assertNotNull(response);
        assertNotEquals(new ArrayList<KnownForResponse>(), response);
    }

    @Test
    public void testBirthdayMatch()
    {
        assertDoesNotThrow(() -> resource.matchBirthday(TestsConsts.DAY, TestsConsts.MONTH));

        BirthdayMatchResponse response = resource.matchBirthday(TestsConsts.DAY, TestsConsts.MONTH);
        
        assertNotNull(response);
        assertNotEquals(new BirthdayMatchResponse(), response);
    }

}