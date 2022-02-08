package com.rosenblat.richard;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.rosenblat.richard.bo.BirthdayMatchBO;
import com.rosenblat.richard.dto.birthdaymatch.BirthdayMatchResponse;
import com.rosenblat.richard.dto.imdb.getBio.GetBioResponse;
import com.rosenblat.richard.dto.imdb.knownFor.KnownForResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@WireMockTest(httpPort = 3030)
@TestPropertySource(properties = {"imdb.url=http://localhost:3030"})
public class BirthdayMatchBOTest {

    @Autowired
    BirthdayMatchBO bo;
    
    @Test
    public void testBornToday()
    {
        assertDoesNotThrow(() -> bo.bornToday(TestsConsts.DAY, TestsConsts.MONTH));
        
        List<String> response = bo.bornToday(TestsConsts.DAY, TestsConsts.MONTH);
        
        assertNotNull(response);
        assertNotEquals(new ArrayList<String>(), response);
    }
    
    @Test
    public void testGetBio()
    {
        assertDoesNotThrow(() -> bo.getBio(TestsConsts.CODE));

        GetBioResponse response = bo.getBio(TestsConsts.CODE);
        
        assertNotNull(response);
        assertNotEquals(new GetBioResponse(), response);
    }

    @Test
    public void testKnownFor()
    {
        assertDoesNotThrow(() -> bo.getKnownFor(TestsConsts.CODE));

        List<KnownForResponse> response = bo.getKnownFor(TestsConsts.CODE);

        assertNotNull(response);
        assertNotEquals(new ArrayList<KnownForResponse>(), response);
    }

    @Test
    public void testBirthdayMatch()
    {
        assertDoesNotThrow(() -> bo.matchBirthday(TestsConsts.DAY, TestsConsts.MONTH));

        BirthdayMatchResponse response = bo.matchBirthday(TestsConsts.DAY, TestsConsts.MONTH);
        
        assertNotNull(response);
        assertNotEquals(new BirthdayMatchResponse(), response);
    }

}