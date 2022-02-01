package com.rosenblat.richard.bo;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.rosenblat.richard.dto.birthdaymatch.BirthdayMatchResponse;
import com.rosenblat.richard.dto.imdb.getBio.GetBioResponse;
import com.rosenblat.richard.dto.imdb.knownFor.KnownForResponse;
import com.rosenblat.richard.services.BornTodayService;
import com.rosenblat.richard.services.GetBioService;
import com.rosenblat.richard.services.KnownForService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BirthdayMatchBO {

    @Autowired
    BornTodayService bornTodayService;

    @Autowired
    GetBioService getBioService;
    
    @Autowired
    KnownForService knownForService;

    public BirthdayMatchResponse matchBirthday(int day, int month){
        log.info("BirthdayMatch Request recieved, matching born actors of day {}/{}", day, month);
        
        LocalDate date = LocalDate.of(2020, month, day);
        
        log.info("Calling born today service");
        List<String> matches = bornTodayService.getBornByDate(date);
        
        BirthdayMatchResponse response = new BirthdayMatchResponse();
        
        log.info("Starting matches loop");
        // int i = 0; 
        for (String code : matches) {
            // if (i>=10) break; 
            
            log.info("{}", code);
            fillMatchesByCode(response, code);
            log.info("");
            // i++; 
        }
        return response;
    }

    @Async
    public void fillMatchesByCode(BirthdayMatchResponse response, String code) {
        log.info("Getting bio of match {}", code);
        GetBioResponse match;
        try {
            match = getBioService.getBio(code).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Thread error while getting Bio", e);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Thread error while getting Bio", e);
        }
        response.addActor(match);
    }

    public List<String> bornToday(int day, int month) {
        log.info("BornToday Request recieved, checking born actors of day {}/{}", day,month);
        LocalDate date = LocalDate.of(2020, month, day);
        return bornTodayService.getBornByDate(date);
    }

    public GetBioResponse getBio(String code) {
        log.info("Request recieved, getting bio of actor belonging to the code: {}", code);
        try {
            return getBioService.getBio(code).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Thread error while getting Bio", e);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Thread error while getting Bio", e);
        }
    }

    public List<KnownForResponse> getKnownFor(String code) {
        log.info("Request recieved, getting bio of actor belonging to the code: {}", code);
        try {
            return knownForService.knownFor(code).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Thread error while getting known for", e);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Thread error while getting known for", e);
        }
    }
}
